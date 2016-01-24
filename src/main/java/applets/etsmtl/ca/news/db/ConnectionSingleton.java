package applets.etsmtl.ca.news.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by gnut3ll4 on 24/01/16.
 */
public class ConnectionSingleton {

    /**
     * URL de connection
     */
    private static String url = "jdbc:postgresql://[host]:[port]/[db_name]";
    /**
     * Nom du user
     */
    private static String user = "[username]";
    /**
     * Mot de passe du user
     */
    private static String passwd = "[password]";
    /**
     * Objet Connection
     */
    private static Connection connect;

    /**
     * Méthode qui va nous retourner notre instance
     * et la créer si elle n'existe pas...
     *
     * @return
     */
    public static Connection getInstance() {
        if (connect == null) {
            try {
                Class.forName("org.postgresql.Driver");
                connect = DriverManager.getConnection(url, user, passwd);
            } catch (SQLException | ClassNotFoundException e) {
                e.printStackTrace();
            }
        }
        return connect;
    }
}
