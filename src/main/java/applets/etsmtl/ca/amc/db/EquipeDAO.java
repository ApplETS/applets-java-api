package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.Equipe;
import applets.etsmtl.ca.amc.model.Participant;

import java.io.UnsupportedEncodingException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valentin-debris on 2016-02-10.
 */
public class EquipeDAO extends DAO<Equipe> {
    @Override
    public Equipe find(String key) {
        Equipe equipe = new Equipe();
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM equipe WHERE id_equipe = '" +key+"'"
                    );
            if(result.first()) {
                equipe.setId(result.getInt("id_equipe"));
                equipe.setNom(result.getString("nom"));
            }
//            else
//                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return equipe;
    }

    @Override
    public List<Equipe> findAll() {
        ArrayList<Equipe> alEquipe = new ArrayList<Equipe>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM equipe ORDER BY id_equipe"
                    );
            while (result.next()) {
                Equipe equipe = new Equipe();
                equipe.setId(result.getInt("id_equipe"));
                equipe.setNom(result.getString("nom"));

                alEquipe.add(equipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alEquipe;
    }

    public List<Equipe> findAll(String idEvent) {
        ArrayList<Equipe> alEquipe = new ArrayList<Equipe>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM equipe eq " +
                                    "JOIN equipe_evenement eqe " +
                                        "ON eq.id_equipe=eqe.id_equipe " +
                                    "WHERE eqe.id_event = '" +idEvent+"' " +
                                    "ORDER BY eq.id_equipe"
                    );
            while (result.next()) {
                Equipe equipe = new Equipe();
                equipe.setId(result.getInt("id_equipe"));
                equipe.setNom(result.getString("nom"));
                equipe.setLienVideo(result.getString("video"));
                equipe.setPrix(result.getString("gagnant"));

                ParticipantDAO participantDAO = new ParticipantDAO();
                ArrayList<Participant> allParticipants = (ArrayList<Participant>)participantDAO.findAll(idEvent, result.getString("id_equipe"));
                equipe.setParticipants(allParticipants);

                alEquipe.add(equipe);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alEquipe;
    }
}
