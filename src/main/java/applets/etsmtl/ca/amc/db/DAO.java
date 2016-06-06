package applets.etsmtl.ca.amc.db;

import java.sql.Connection;
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
    public abstract T find(int id);

    /**
     * Récupère tous les objets
     * @return
     */
    public abstract List<T> findAll();

}