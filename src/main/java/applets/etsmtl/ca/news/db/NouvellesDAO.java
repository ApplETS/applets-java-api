package applets.etsmtl.ca.news.db;

import applets.etsmtl.ca.news.model.Nouvelle;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by gnut3ll4 on 24/01/16.
 */
public class NouvellesDAO extends DAO<Nouvelle> {

    @Override
    public Nouvelle find(String id) {
        try {
            String findById = "SELECT * FROM nouvelles WHERE id = ?";
            PreparedStatement st = this.connection.prepareStatement(findById,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            st.setString(1, id);
            ResultSet result = st.executeQuery();
            if(result.first()) {
                Nouvelle nouvelle = getDataFromResult(result);
                result.close();
                st.close();
                return nouvelle;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean isExisting(String id) {
        try {
            String findById = "SELECT id FROM nouvelles WHERE id = ?";
            PreparedStatement st = this.connection.prepareStatement(findById,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            st.setString(1, id);
            ResultSet result = st.executeQuery();
            if(result.first()) {
                result.close();
                st.close();
                return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Nouvelle> findAll() {
        List<Nouvelle> nouvelles = new ArrayList<Nouvelle>();
        try {
            String findAll = "SELECT * FROM nouvelles ORDER BY date DESC LIMIT " + FIND_ALL_NOUVELLES_MAX_SIZE + ";";
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            findAll
                    );
            while(result.next()) {
                nouvelles.add(getDataFromResult(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.sort(nouvelles);
        return nouvelles;
    }

    /**
     * Cherche les nouvelles pour une source donnée.
     * @param sourceID le ID de la source.
     * @return La liste des 10 dernières nouvelles pour cette source
     */
    public List<Nouvelle> findAllForSource(String sourceID) {
        List<Nouvelle> nouvelles = new ArrayList<Nouvelle>();
        try {
            String findAllforSource="SELECT * FROM nouvelles WHERE id_source = ? ORDER BY date DESC LIMIT " + FIND_ALL_NOUVELLES_MAX_SIZE;
            PreparedStatement st = this.connection.prepareStatement(findAllforSource,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            st.setString(1, sourceID);
            ResultSet result = st.executeQuery();
            while(result.next()) {
                nouvelles.add(getDataFromResult(result));
            }
            result.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.sort(nouvelles);
        return nouvelles;
    }

    @Override
    protected Nouvelle getDataFromResult(ResultSet result) throws SQLException {
        Nouvelle nouvelle = new Nouvelle();
        nouvelle.setDate(result.getDate("date"));
        nouvelle.setMessage(result.getString("message"));
        nouvelle.setLink(result.getString("link"));
        nouvelle.setUrlPicture(result.getString("url_picture"));
        nouvelle.setTitre(result.getString("titre"));
        nouvelle.setId(result.getString("id"));
        nouvelle.setId_source(result.getString("id_source"));
        return nouvelle;
    }


}
