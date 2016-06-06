package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.Participant;
import applets.etsmtl.ca.amc.model.TirageInscrit;
import applets.etsmtl.ca.amc.model.TirageInscrit;
import applets.etsmtl.ca.amc.model.TiragePrix;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valentin-debris on 2016-02-10.
 */
public class TirageInscritDAO extends DAO<TirageInscrit> {
    @Override
    public TirageInscrit find(int idTirageInscrit) {
        TirageInscrit tirageInscrit = new TirageInscrit();
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM tirage_inscrit " +
                                "WHERE id_tirage_inscrit = '" +idTirageInscrit+"'"
                    );
            if(result.first()) {
                tirageInscrit.setId(result.getInt("id_tirage_inscrit"));

                ParticipantDAO participantDAO = new ParticipantDAO();
                Participant participantGagnant = participantDAO.find(result.getInt("id_participant"));
                tirageInscrit.setParticipant(participantGagnant);
            }
            else
                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tirageInscrit;
    }

    @Override
    public List<TirageInscrit> findAll() {
        ArrayList<TirageInscrit> alTirageInscrit = new ArrayList<TirageInscrit>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM tirage_inscrit ti " +
                                "ORDER BY id_tirage_inscrit"
                    );
            while (result.next()) {
                TirageInscrit tirageInscrit = new TirageInscrit();

                tirageInscrit.setId(result.getInt("id_tirage_inscrit"));

                ParticipantDAO participantDAO = new ParticipantDAO();
                Participant participantGagnant = participantDAO.find(result.getInt("id_participant"));
                tirageInscrit.setParticipant(participantGagnant);

                alTirageInscrit.add(tirageInscrit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alTirageInscrit;
    }

    public List<TirageInscrit> findAll(int idEvent) {
        ArrayList<TirageInscrit> alTirageInscrits = new ArrayList<TirageInscrit>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM tirage_inscrit ti " +
                                    "WHERE ti.id_event = '" +idEvent+"' " +
                                    "ORDER BY ti.id_tirage_inscrit"
                    );
            while (result.next()) {
                TirageInscrit tirageInscrit = new TirageInscrit();
                tirageInscrit.setId(result.getInt("id_tirage_inscrit"));

                ParticipantDAO participantDAO = new ParticipantDAO();
                Participant participantGagnant = participantDAO.find(result.getInt("id_participant"));
                tirageInscrit.setParticipant(participantGagnant);

                alTirageInscrits.add(tirageInscrit);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alTirageInscrits;
    }


    public boolean addTirageInscrit(int idEvent, int idParticipant) {
        boolean inscritWorks = false;
        try {
            String selectStatement = "INSERT INTO tirage_inscrit (id_event, id_participant) VALUES ( ?, ?)";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setInt(1,idEvent);
            prepStmt.setInt(2,idParticipant);

            int nbInsert = prepStmt.executeUpdate();
            if(nbInsert == 1)
                inscritWorks = true;
            else
                System.out.println("2:ERROR:TirageInscrit not inserted");

        } catch (SQLException e) {
            System.out.println("1:ERROR:TirageInscrit not inserted-duplicate");
            //e.printStackTrace();
        }

        return inscritWorks;
    }
}
