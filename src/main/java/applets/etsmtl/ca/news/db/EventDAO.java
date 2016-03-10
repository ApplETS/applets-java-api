package applets.etsmtl.ca.news.db;

import applets.etsmtl.ca.news.model.Event;
import applets.etsmtl.ca.news.model.Nouvelle;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by marcantvez on 24/01/16.
 */
public class EventDAO extends DAO<Event> {

    @Override
    public Event find(String id) {
        try {
                ResultSet result = this.connection
                                .createStatement(
                                        ResultSet.TYPE_SCROLL_INSENSITIVE,
                                        ResultSet.CONCUR_READ_ONLY
                                        ).executeQuery(
                                            "SELECT * FROM evenements WHERE id = '" +id+"'"
                                        );
                                        // TODO add DATE param to query
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
                            "SELECT id FROM evenements WHERE id = '" +id+"'"
                    );
            if(result.first())
                return true;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    @Override
    public List<Event> findAll() {
        // TODO review limit
        // TODO check if date must be checked on find() to filter passed events

        List<Event> events = new ArrayList<Event>();
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM evenements ORDER BY debut DESC LIMIT 10"
                    );
            while(result.next()) {
                events.add(getDataFromResult(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return events;
    }

    @Override
    protected Event getDataFromResult(ResultSet result) throws SQLException {
        Event event = new Event();
        event.setId(result.getString("id"));
        event.setNom(result.getString("nom"));
        event.setDebut(result.getDate("debut"));
        event.setDebut(result.getDate("fin"));
        event.setNom_lieu(result.getString("nom_lieu"));
        event.setVille(result.getString("ville"));
        event.setEtat(result.getString("etat"));
        event.setPays(result.getString("pays"));
        event.setAdresse(result.getString("adresse"));
        event.setCode_postal(result.getString("code_postal"));
        event.setLongitude(result.getFloat("longitude"));
        event.setLatitude(result.getFloat("latitude"));
        event.setDescription(result.getString("description"));
        event.setImage(result.getString("image"));
        event.setId_source(result.getString("id_source"));
        return event;
    }

}
