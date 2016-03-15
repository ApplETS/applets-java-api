package applets.etsmtl.ca.news.db;

import applets.etsmtl.ca.news.model.Event;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
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
        Collections.sort(events);
        return events;
    }

    /**
     * Cherche les événements pour une source donnée.
     * @param sourceID le ID de la source.
     * @return La liste des événements pour cette source
     */
    public List<Event> findAllForSource(String sourceID) {
        // TODO review limit
        // TODO check if date must be checked on find() to filter passed events
        List<Event> events = new ArrayList<Event>();
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM evenements WHERE id_source = '"+sourceID+"' ORDER BY debut DESC LIMIT 10"
                    );
            while(result.next()) {
                events.add(getDataFromResult(result));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.sort(events);
        return events;
    }

    public void add(Event event) {
        try {

            String req_insert_event = "INSERT INTO evenements (id, nom, debut, fin, nom_lieu, ville, etat, pays, adresse, code_postal, longitude, latitude, description, image, id_source) " +
                    "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement preparedStatement = ConnectionSingleton.getInstance().prepareStatement(req_insert_event);

            preparedStatement.setString(1, event.getId());
            preparedStatement.setString(2, event.getNom());

            if(event.getDebut() != null)
                preparedStatement.setDate(3, new java.sql.Date(event.getDebut().getTime()));
            else
                preparedStatement.setDate(3, null);

            if(event.getFin() != null)
                preparedStatement.setDate(4, new java.sql.Date(event.getFin().getTime()));
            else
                preparedStatement.setDate(4, null);

            preparedStatement.setString(5, event.getNom_lieu());
            preparedStatement.setString(6, event.getVille());
            preparedStatement.setString(7, event.getEtat());
            preparedStatement.setString(8, event.getPays());
            preparedStatement.setString(9, event.getAdresse());
            preparedStatement.setString(10, event.getCode_postal());
            preparedStatement.setFloat(11, event.getLongitude());
            preparedStatement.setFloat(12, event.getLatitude());
            preparedStatement.setString(13, event.getDescription());
            preparedStatement.setString(14, event.getImage());
            preparedStatement.setString(15, event.getId_source());

            preparedStatement.executeUpdate();
        } catch (SQLException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected Event getDataFromResult(ResultSet result) throws SQLException {
        Event event = new Event();
        event.setId(result.getString("id"));
        event.setNom(result.getString("nom"));
        event.setDebut(result.getDate("debut"));
        event.setFin(result.getDate("fin"));
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
