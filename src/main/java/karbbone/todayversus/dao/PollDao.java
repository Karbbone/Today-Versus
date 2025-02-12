package karbbone.todayversus.dao;

import karbbone.todayversus.bdd.DatabaseManager;
import karbbone.todayversus.model.Poll;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PollDao implements DaoEntity<Poll> {

    private Connection conn;

    public PollDao() throws SQLException {
        this.conn = DatabaseManager.getInstance().getConnection();
    }

    @Override
    public boolean insert(Poll poll) {
        String sql = "INSERT INTO poll (nom1, emoji1, nom2, emoji2, nom3, emoji3, nom4, emoji4) VALUES (?, ?, ?, ?, ?, ?, ?, ?)";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, poll.getNom1());
            stmt.setString(2, poll.getEmoji1());
            stmt.setString(3, poll.getNom2());
            stmt.setString(4, poll.getEmoji2());
            stmt.setString(5, poll.getNom3());
            stmt.setString(6, poll.getEmoji3());
            stmt.setString(7, poll.getNom4());
            stmt.setString(8, poll.getEmoji4());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean update(Poll poll) {
        String sql = "UPDATE poll SET nom1=?, emoji1=?, nom2=?, emoji2=?, nom3=?, emoji3=?, nom4=?, emoji4=? WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setString(1, poll.getNom1());
            stmt.setString(2, poll.getEmoji1());
            stmt.setString(3, poll.getNom2());
            stmt.setString(4, poll.getEmoji2());
            stmt.setString(5, poll.getNom3());
            stmt.setString(6, poll.getEmoji3());
            stmt.setString(7, poll.getNom4());
            stmt.setString(8, poll.getEmoji4());
            stmt.setInt(9, poll.getId());

            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la mise à jour : " + e.getMessage());
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        String sql = "DELETE FROM poll WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            return stmt.executeUpdate() > 0;
        } catch (SQLException e) {
            System.out.println("Erreur lors de la suppression : " + e.getMessage());
            return false;
        }
    }

    @Override
    public Poll findById(int id) {
        String sql = "SELECT * FROM poll WHERE id=?";

        try (PreparedStatement stmt = conn.prepareStatement(sql)) {
            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                return new Poll(
                        rs.getInt("id"),
                        rs.getString("nom1"), rs.getString("emoji1"),
                        rs.getString("nom2"), rs.getString("emoji2"),
                        rs.getString("nom3"), rs.getString("emoji3"),
                        rs.getString("nom4"), rs.getString("emoji4")
                );
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération : " + e.getMessage());
        }
        return null;
    }

    @Override
    public List<Poll> findAll() {
        List<Poll> polls = new ArrayList<>();
        String sql = "SELECT * FROM poll";

        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                polls.add(new Poll(
                        rs.getInt("id"),
                        rs.getString("nom1"), rs.getString("emoji1"),
                        rs.getString("nom2"), rs.getString("emoji2"),
                        rs.getString("nom3"), rs.getString("emoji3"),
                        rs.getString("nom4"), rs.getString("emoji4")
                ));
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la récupération des sondages : " + e.getMessage());
        }
        return polls;
    }
}
