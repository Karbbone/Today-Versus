package karbbone.todayversus.model;

public class Poll {
    private int id;
    private String nom1, emoji1, nom2, emoji2, nom3, emoji3, nom4, emoji4;

    public Poll(int id, String nom1, String emoji1, String nom2, String emoji2, String nom3, String emoji3, String nom4, String emoji4) {
        this.id = id;
        this.nom1 = nom1;
        this.emoji1 = emoji1;
        this.nom2 = nom2;
        this.emoji2 = emoji2;
        this.nom3 = nom3;
        this.emoji3 = emoji3;
        this.nom4 = nom4;
        this.emoji4 = emoji4;
    }

    public int getId() { return id; }
    public String getNom1() { return nom1; }
    public String getEmoji1() { return emoji1; }
    public String getNom2() { return nom2; }
    public String getEmoji2() { return emoji2; }
    public String getNom3() { return nom3; }
    public String getEmoji3() { return emoji3; }
    public String getNom4() { return nom4; }
    public String getEmoji4() { return emoji4; }
}
