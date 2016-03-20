package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.Participant;

import java.sql.PreparedStatement;
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
            String selectStatement = "SELECT * FROM participant WHERE id_participant = ?";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setInt(1,Integer.parseInt(idParticipant));
            ResultSet result = prepStmt.executeQuery();

            if(result.first()) {
                participant.setId(result.getInt("id_participant"));
                participant.setNom(result.getString("nom"));
                participant.setImage(result.getString("image"));
                participant.setCourriel(result.getString("courriel"));
                participant.setDescription(result.getString("description"));
            }
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
                participant.setCourriel(result.getString("courriel"));
                participant.setDescription(result.getString("description"));

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
            String selectStatement = "SELECT * FROM participant p " +
                                        "JOIN participant_equipe_event pee " +
                                        "ON p.id_participant=pee.id_participant " +
                                        "JOIN equipe_evenement ee " +
                                        "ON ee.id_eq_ev = pee.id_equipe_event " +
                                        "WHERE ee.id_event = ? " +
                                        "ORDER BY p.id_participant";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setInt(1,Integer.parseInt(idEvent));
            ResultSet result = prepStmt.executeQuery();

            while (result.next()) {
                Participant participant = new Participant();
                participant.setId(result.getInt("id_participant"));
                participant.setNom(result.getString("nom"));
                participant.setImage(result.getString("image"));
                participant.setCourriel(result.getString("courriel"));
                participant.setDescription(result.getString("description"));

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
            String selectStatement = "SELECT * FROM participant p " +
                                        "JOIN participant_equipe_event pee " +
                                        "ON p.id_participant=pee.id_participant " +
                                        "JOIN equipe_evenement ee " +
                                        "ON ee.id_eq_ev = pee.id_equipe_event " +
                                        "WHERE ee.id_event = ? " +
                                        "AND ee.id_equipe = ? " +
                                        "ORDER BY p.id_participant";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setInt(1,Integer.parseInt(idEvent));
            prepStmt.setInt(2,Integer.parseInt(idEquipe));
            ResultSet result = prepStmt.executeQuery();

            while (result.next()) {
                Participant participant = new Participant();
                participant.setId(result.getInt("id_participant"));
                participant.setNom(result.getString("nom"));
                participant.setImage(result.getString("image"));
                participant.setCourriel(result.getString("courriel"));
                participant.setDescription(result.getString("description"));

                alParticipant.add(participant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alParticipant;
    }

    public Participant findWithEmail(String courriel) {
        Participant participant = new Participant();

        try {
            String selectStatement = "SELECT * FROM participant WHERE courriel = ? ";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setString(1,courriel);

            ResultSet result = prepStmt.executeQuery();

            if(result.first()) {
                participant.setId(result.getInt("id_participant"));
                participant.setNom(result.getString("nom"));
                participant.setImage(result.getString("image"));
                participant.setCourriel(result.getString("courriel"));
                participant.setDescription(result.getString("description"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return participant;
    }

}
