package karbbone.todayversus.bdd;

import java.sql.*;

/**
 * classe DatabaseManager singleton qui permet de gêré la base de données et initialisation
 */
public class DatabaseManager {
    private static final String URL = "jdbc:sqlite:database.db";
    private static DatabaseManager instance;
    private Connection conn;

    /**
     * constructeur privé
     * @throws SQLException
     */
    private DatabaseManager() throws SQLException {
        conn = DriverManager.getConnection(URL);
        initTable();
        seedDatabase();
    }

    /**
     * get instance permet de récupéré l'instance unique de DatabaseManager
     * @return DatabaseManager instnace
     * @throws SQLException
     */
    public static DatabaseManager getInstance() throws SQLException {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    /**
     * initialise les tables
     */
    private void initTable(){
        String sql = """
        CREATE TABLE IF NOT EXISTS poll (
        id INTEGER PRIMARY KEY AUTOINCREMENT,
        nom1 TEXT NOT NULL,
        emoji1 TEXT NOT NULL,
        nom2 TEXT NOT NULL,
        emoji2 TEXT NOT NULL,
        nom3 TEXT NOT NULL,
        emoji3 TEXT NOT NULL,
        nom4 TEXT NOT NULL,
        emoji4 TEXT NOT NULL
       );
       """;

        try (Statement stmt = conn.createStatement()) {
            stmt.execute(sql);
        } catch (SQLException e) {
            System.out.println("Erreur lors de la création de la table : " + e.getMessage());
        }
    }

    /**
     * Remplit la base de données avec des exemples de sondages (évite les doublons).
     */
    private void seedDatabase() {
        String checkSql = "SELECT COUNT(*) FROM poll";
        try (Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(checkSql)) {
            if (rs.next() && rs.getInt(1) > 0) {
                System.out.println("Les données existent déjà");
                return;
            }
        } catch (SQLException e) {
            System.out.println("Erreur lors de la vérification des données : " + e.getMessage());
            return;
        }

        String insertSql = """
            INSERT INTO poll (nom1, emoji1, nom2, emoji2, nom3, emoji3, nom4, emoji4) VALUES
            ('Chat', '🐱', 'Chien', '🐶', 'Lapin', '🐰', 'Oiseau', '🐦'),
            ('Pizza', '🍕', 'Burger', '🍔', 'Sushi', '🍣', 'Tacos', '🌮'),
            ('Été', '☀️', 'Hiver', '❄️', 'Printemps', '🌸', 'Automne', '🍂'),
            ('PS5', '🎮', 'Xbox', '🕹️', 'PC', '💻', 'Switch', '🎭'),
        """;

        try (Statement stmt = conn.createStatement()) {
            stmt.executeUpdate(insertSql);
            System.out.println("Base de données remplie avec des données");
        } catch (SQLException e) {
            System.out.println("Erreur lors de l'insertion des données : " + e.getMessage());
        }
    }

    public Connection getConnection() {
        return conn;
    }
}