package applets.etsmtl.ca.news.db;

import applets.etsmtl.ca.news.model.Nouvelle;

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
                ResultSet result = this.connection
                                .createStatement(
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY
                                        ).executeQuery(
                                            "SELECT * FROM nouvelles WHERE id = '" +id+"'"
                                        );
                if(result.first()) {
                        return getDataFromResult(result);
                }
            } catch (SQLException e) {
                e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean isExisting(String id) {
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT id FROM nouvelles WHERE id = '" +id+"'"
                    );
            if(result.first())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Nouvelle> findAll() {
        List<Nouvelle> nouvelles = new ArrayList<Nouvelle>();
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM nouvelles ORDER BY date DESC LIMIT 10;"
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
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM nouvelles WHERE id_source = '"+sourceID+"'ORDER BY date DESC LIMIT 10;"
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
