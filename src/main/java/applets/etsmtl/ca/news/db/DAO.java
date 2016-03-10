package applets.etsmtl.ca.news.db;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

/**
 * Created by gnut3ll4 on 24/01/16.
 */
public abstract class DAO<T> {

    public Connection connection = ConnectionSingleton.getInstance();

    /**
     * Permet de récupérer un objet via son ID
     * @param id
     * @return
     */
    public abstract T find(String id);

    /**
     * Permet de savoir un objet est présent en base de données via son ID
     * @param key
     * @return boolean
     */
    public abstract boolean isExisting(String key);

    /**
     * Récupère tous les objets
     * @return
     */
    public abstract List<T> findAll();

    /**
     * Crée un objet a partir du Result
     * @return
     */
    protected abstract T getDataFromResult(ResultSet result) throws SQLException;

}