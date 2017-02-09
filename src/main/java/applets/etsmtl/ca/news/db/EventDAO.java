package applets.etsmtl.ca.news.db;

import applets.etsmtl.ca.news.model.Event;

import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class EventDAO extends DAO<Event> {

    @Override
    public Event find(String id) {
        try {
            String findById="SELECT * FROM evenements WHERE id = ?";
            PreparedStatement st = this.connection.prepareStatement(findById,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            st.setString(1, id);
            ResultSet result = st.executeQuery();
            // TODO add DATE param to query
            if(result.first()) {
                Event event = getDataFromResult(result);
                result.close();
                st.close();
                return event;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    @Override
    public boolean isExisting(String id) {
        try {
            String findById="SELECT id FROM evenements WHERE id = ?";
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
    public List<Event> findAll() {
        // TODO review limit
        // TODO check if date must be checked on find() to filter passed events
        List<Event> events = new ArrayList<Event>();
        try {
            String findAll = "SELECT * FROM evenements ORDER BY debut DESC LIMIT "+ FIND_ALL_EVENEMENTS_MAX_SIZE;
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            findAll
                    );
            while(result.next()) {
                events.add(getDataFromResult(result));
            }
            result.close();
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
            String findAllforSource = "SELECT * FROM evenements WHERE id_source = ? ORDER BY debut DESC LIMIT " + FIND_ALL_EVENEMENTS_MAX_SIZE;
            PreparedStatement st = this.connection.prepareStatement(findAllforSource,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            st.setString(1, sourceID);
            ResultSet result = st.executeQuery();
            while(result.next()) {
                events.add(getDataFromResult(result));
            }
            result.close();
            st.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Collections.sort(events);
        return events;
    }

    /**
     * Cherche les prochains événements pour une source donnée.
     * @param sourceID le ID de la source.
     * @return La liste des événements pour cette source
     */
    public List<Event> findFollowingEvents(String sourceID) {
        // TODO review limit
        // TODO check if date must be checked on find() to filter passed events
        List<Event> events = new ArrayList<Event>();
        try {
            String findAllforSource = "SELECT * FROM evenements WHERE id_source = ? AND fin >= ? ORDER BY debut";
            PreparedStatement st = this.connection.prepareStatement(findAllforSource,ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
            st.setString(1, sourceID);
            st.setDate(2, new Date(new java.util.Date().getTime())); // Now
            ResultSet result = st.executeQuery();
            while(result.next()) {
                events.add(getDataFromResult(result));
            }
            result.close();
            st.close();
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
                preparedStatement.setTimestamp(3, new java.sql.Timestamp(event.getDebut().getTime()));
            else
                preparedStatement.setTimestamp(3, null);

            if(event.getFin() != null)
                preparedStatement.setTimestamp(4, new java.sql.Timestamp(event.getFin().getTime()));
            else
                preparedStatement.setTimestamp(4, null);

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

    public void update(Event event) {
        try {

            String req_insert_event = "" +
                    "UPDATE evenements " +
                    "SET nom = ?, debut = ?, fin = ?, nom_lieu = ?, ville = ?, etat = ?, pays = ?, " +
                    "adresse = ?, code_postal = ?, longitude = ?, latitude = ?, description = ?, " +
                    "image = ?, id_source = ? " +
                    "WHERE id = ?";
            PreparedStatement preparedStatement = ConnectionSingleton.getInstance().prepareStatement(req_insert_event);

            preparedStatement.setString(15, event.getId());
            preparedStatement.setString(1, event.getNom());

            if(event.getDebut() != null)
                preparedStatement.setTimestamp(2, new java.sql.Timestamp(event.getDebut().getTime()));
            else
                preparedStatement.setTimestamp(2, null);

            if(event.getFin() != null)
                preparedStatement.setTimestamp(3, new java.sql.Timestamp(event.getFin().getTime()));
            else
                preparedStatement.setTimestamp(3, null);

            preparedStatement.setString(4, event.getNom_lieu());
            preparedStatement.setString(5, event.getVille());
            preparedStatement.setString(6, event.getEtat());
            preparedStatement.setString(7, event.getPays());
            preparedStatement.setString(8, event.getAdresse());
            preparedStatement.setString(9, event.getCode_postal());
            preparedStatement.setFloat(10, event.getLongitude());
            preparedStatement.setFloat(11, event.getLatitude());
            preparedStatement.setString(12, event.getDescription());
            preparedStatement.setString(13, event.getImage());
            preparedStatement.setString(14, event.getId_source());

            preparedStatement.executeUpdate();
        } catch (SQLException | NullPointerException e) {
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
