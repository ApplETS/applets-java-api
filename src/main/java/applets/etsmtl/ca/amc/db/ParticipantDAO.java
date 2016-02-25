package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.Participant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valentin-debris on 2016-02-10.
 */
public class ParticipantDAO extends DAO<Participant> {
    @Override
    public Participant find(String idParticipant) {
        Participant participant = new Participant();
        if(idParticipant == null)
            return null;

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM participant WHERE id_participant = '" +idParticipant+"'"
                    );
            if(result.first()) {
                participant.setId(result.getInt("id_participant"));
                participant.setNom(result.getString("nom"));
                participant.setImage(result.getString("image"));
            }
//            else
//                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participant;
    }

    @Override
    public List<Participant> findAll() {
        ArrayList<Participant> alParticipant = new ArrayList<Participant>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM participant ORDER BY id_participant"
                    );
            while (result.next()) {
                Participant participant = new Participant();
                participant.setId(result.getInt("id_participant"));
                participant.setNom(result.getString("nom"));
                participant.setImage(result.getString("image"));

                alParticipant.add(participant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alParticipant;
    }

    //return all the participant for an event
    public List<Participant> findAll(String idEvent) {
        ArrayList<Participant> alParticipant = new ArrayList<Participant>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM participant p " +
                                    "JOIN participant_equipe_event pee " +
                                    "ON p.id_participant=pee.id_participant " +
                                    "JOIN equipe_evenement ee " +
                                    "ON ee.id_eq_ev = pee.id_equipe_event " +
                                    "WHERE ee.id_event = '" +idEvent+"' " +
                                    "ORDER BY p.id_participant"
                    );
            while (result.next()) {
                Participant participant = new Participant();
                participant.setId(result.getInt("id_participant"));
                participant.setNom(result.getString("nom"));
                participant.setImage(result.getString("image"));

                alParticipant.add(participant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alParticipant;
    }

    //return all the participant for an equipe in a specific event
    public List<Participant> findAll(String idEvent, String idEquipe) {
        ArrayList<Participant> alParticipant = new ArrayList<Participant>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM participant p " +
                                    "JOIN participant_equipe_event pee " +
                                        "ON p.id_participant=pee.id_participant " +
                                    "JOIN equipe_evenement ee " +
                                        "ON ee.id_eq_ev = pee.id_equipe_event " +
                                    "WHERE ee.id_event = '" +idEvent+"' " +
                                    "AND ee.id_equipe = '" +idEquipe+"' " +
                                    "ORDER BY p.id_participant"
                    );
            while (result.next()) {
                Participant participant = new Participant();
                participant.setId(result.getInt("id_participant"));
                participant.setNom(result.getString("nom"));
                participant.setImage(result.getString("image"));

                alParticipant.add(participant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alParticipant;
    }
}
