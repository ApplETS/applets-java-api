package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.Participant;
import applets.etsmtl.ca.amc.model.TiragePrix;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valentin-debris on 2016-02-10.
 */
public class TiragePrixDAO extends DAO<TiragePrix> {
    @Override
    public TiragePrix find(String idTiragePrix) {
        TiragePrix tiragePrix = new TiragePrix();
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM tirage_prix tp " +
                                "LEFT JOIN tirage_inscrit ts " +
                                    "ON tp.id_tirage_inscrit = ts.id_tirage_inscrit " +
                                "WHERE id_tirage_prix = '" +idTiragePrix+"'"
                    );
            if(result.first()) {
                tiragePrix.setId(result.getInt("id_tirage_prix"));
                tiragePrix.setPrix(result.getString("prix"));
                tiragePrix.setImage(result.getString("image"));

                ParticipantDAO participantDAO = new ParticipantDAO();
                Participant participantGagnant = participantDAO.find(result.getString("id_participant"));
                tiragePrix.setGagnant(participantGagnant);
            }
//            else
//                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tiragePrix;
    }

    @Override
    public List<TiragePrix> findAll() {
        ArrayList<TiragePrix> alTirage = new ArrayList<TiragePrix>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM tirage_prix tp " +
                                "LEFT JOIN tirage_inscrit ts " +
                                    "ON tp.id_tirage_inscrit = ts.id_tirage_inscrit " +
                            "ORDER BY id_tirage_prix"
                    );
            while (result.next()) {
                TiragePrix tiragePrix = new TiragePrix();
                tiragePrix.setId(result.getInt("id_tirage_prix"));
                tiragePrix.setPrix(result.getString("prix"));
                tiragePrix.setImage(result.getString("image"));

                ParticipantDAO participantDAO = new ParticipantDAO();
                Participant participantGagnant = participantDAO.find(result.getString("id_participant"));
                tiragePrix.setGagnant(participantGagnant);

                alTirage.add(tiragePrix);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alTirage;
    }

    public List<TiragePrix> findAll(String idTirageSort) {
        ArrayList<TiragePrix> alTiragePrix = new ArrayList<TiragePrix>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM tirage_prix tp " +
                                    "LEFT JOIN tirage_inscrit ts " +
                                    "ON tp.id_tirage_inscrit = ts.id_tirage_inscrit " +
                                    "WHERE tp.id_tirage = '" +idTirageSort+"'"
                    );
            while (result.next()) {
                TiragePrix tiragePrix = new TiragePrix();
                tiragePrix.setId(result.getInt("id_tirage_prix"));
                tiragePrix.setPrix(result.getString("prix"));
                tiragePrix.setImage(result.getString("image"));

                ParticipantDAO participantDAO = new ParticipantDAO();
                Participant participantGagnant = participantDAO.find(result.getString("id_participant"));
                tiragePrix.setGagnant(participantGagnant);

                alTiragePrix.add(tiragePrix);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alTiragePrix;
    }
}
