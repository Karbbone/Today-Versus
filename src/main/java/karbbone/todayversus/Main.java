package karbbone.todayversus;

import karbbone.todayversus.bdd.DatabaseManager;
import karbbone.todayversus.dao.PollDao;
import karbbone.todayversus.model.Poll;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.entities.channel.unions.MessageChannelUnion;
import net.dv8tion.jda.api.entities.emoji.Emoji;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.messages.MessagePollData;

import java.sql.SQLException;

public class Main extends ListenerAdapter {

    public static DatabaseManager inst;

    public static void main(String[] args) {
        try {
            String token = getEnvToken();
            JDA jda = JDABuilder.createDefault(token)
                    .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                    .addEventListeners(new Main())
                    .build();
            inst = DatabaseManager.getInstance();
            jda.awaitReady();
            System.out.println("Bot is ready!");
        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

    public static String getEnvToken(){
        String token = System.getenv("DISCORD_TOKEN");

        if (token == null || token.isEmpty()) {
            System.err.println("Token Discord non défini ! Vérifie tes variables d'environnement.");
            return null;
        }
        return token;
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent event) {
        Message msg = event.getMessage();
        if (msg.getContentRaw().equals("!hello")) {
            MessageChannelUnion channel = event.getChannel();
            try {
                Poll firstPoll = new PollDao().findById(1);
                channel.sendMessage("Versus du jour !")
                        .setPoll(
                                MessagePollData.builder("Une préférence ?")
                                        .addAnswer(firstPoll.getNom1(), Emoji.fromUnicode(firstPoll.getEmoji1()))
                                        .addAnswer(firstPoll.getNom2(), Emoji.fromUnicode(firstPoll.getEmoji2()))
                                        .addAnswer(firstPoll.getNom3(), Emoji.fromUnicode(firstPoll.getEmoji3()))
                                        .addAnswer(firstPoll.getNom4(), Emoji.fromUnicode(firstPoll.getEmoji4()))
                                        .build())
                        .queue();
            } catch (SQLException e) {
                throw new RuntimeException(e);
            }
        }
    }
}