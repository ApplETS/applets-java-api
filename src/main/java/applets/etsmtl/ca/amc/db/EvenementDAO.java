package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.Evenement;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valentin-debris on 2016-02-10.
 */
public class EvenementDAO extends DAO<Evenement> {
    @Override
    public Evenement find(String key) {
        Evenement event = new Evenement();
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM evenement WHERE id_event = '" +key+"'"
                    );
            if(result.first()) {
                event.setId(result.getInt("id_event"));
                event.setNom(result.getString("nom"));
                event.setPresentation(result.getString("presentation"));
                event.setHashtag(result.getString("hashtag"));
                event.setLienEventbrite(result.getString("lien_eventbrite"));
                event.setDateDebut(result.getDate("date_debut"));
                event.setDateFin(result.getDate("date_fin"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }

    @Override
    public List<Evenement>  findAll() {
        ArrayList<Evenement> alEvent = new ArrayList<Evenement>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM evenement ORDER BY id_event"
                    );
            while (result.next()) {
                Evenement event = new Evenement();
                event.setId(result.getInt("id_event"));
                event.setNom(result.getString("nom"));
                event.setPresentation(result.getString("presentation"));
                event.setHashtag(result.getString("hashtag"));
                event.setLienEventbrite(result.getString("lien_eventbrite"));
                event.setDateDebut(result.getDate("date_debut"));
                event.setDateFin(result.getDate("date_fin"));
                alEvent.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alEvent;
    }
}
