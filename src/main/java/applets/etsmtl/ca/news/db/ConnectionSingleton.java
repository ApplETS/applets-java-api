package applets.etsmtl.ca.news.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class ConnectionSingleton {

    //Environment variables
    private static String HOST = "POSTGRESQL_HOST";
    private static String PORT = "POSTGRESQL_PORT";
    private static String DB_NAME = "POSTGRESQL_DB_NAME";
    private static String USER = "POSTGRESQL_USER";
    private static String PASS = "POSTGRESQL_PASS";

    /**
     * URL de connection
     */
    private static String url = "jdbc:postgresql://"+System.getenv().get(HOST)+":"+System.getenv().get(PORT)+"/"+System.getenv(DB_NAME);
    /**
     * Nom du user
     */
    private static String user = System.getenv(USER);
    /**
     * Mot de passe du user
     */
    private static String passwd = System.getenv(PASS);
    /**
     * Objet Connection
     */
    private static Connection connect;

    /**
     * Méthode qui va nous retourner notre instance
     * et la créer si elle n'existe pas ou bien réouvrir la connexion si elle existe et a été fermée
     *
     * @return
     */
    public static Connection getInstance() {

        try {
            if (connect == null || (connect != null && connect.isClosed())) {
                Class.forName("org.postgresql.Driver");
                connect = DriverManager.getConnection(url, user, passwd);
            }
        }
        catch (SQLException | ClassNotFoundException e) {
            e.printStackTrace();
        }

        return connect;
    }
}
