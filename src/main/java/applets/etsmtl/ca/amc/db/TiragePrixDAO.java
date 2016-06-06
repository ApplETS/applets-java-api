package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.Participant;
import applets.etsmtl.ca.amc.model.TiragePrix;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valentin-debris on 2016-02-10.
 */
public class TiragePrixDAO extends DAO<TiragePrix> {
    @Override
    public TiragePrix find(int idTiragePrix) {
        TiragePrix tiragePrix = new TiragePrix();
        try {
            String selectStatement = "SELECT * FROM tirage_prix tp " +
                                    "LEFT JOIN tirage_inscrit ts " +
                                    "ON tp.id_tirage_inscrit = ts.id_tirage_inscrit " +
                                    "WHERE id_tirage_prix = ?";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setInt(1,idTiragePrix);
            ResultSet result = prepStmt.executeQuery();

            if(result.first()) {
                tiragePrix.setId(result.getInt("id_tirage_prix"));
                tiragePrix.setPrix(result.getString("prix"));
                tiragePrix.setImage(result.getString("image"));

                ParticipantDAO participantDAO = new ParticipantDAO();
                Participant participantGagnant = participantDAO.find(result.getInt("id_participant"));
                tiragePrix.setGagnant(participantGagnant);
            }
            else
                return null;
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
                Participant participantGagnant = participantDAO.find(result.getInt("id_participant"));
                tiragePrix.setGagnant(participantGagnant);

                alTirage.add(tiragePrix);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alTirage;
    }

    public List<TiragePrix> findAll(int idTirageSort) {
        ArrayList<TiragePrix> alTiragePrix = new ArrayList<TiragePrix>();

        try {
            String selectStatement = "SELECT * FROM tirage_prix tp " +
                    "LEFT JOIN tirage_inscrit ts " +
                    "ON tp.id_tirage_inscrit = ts.id_tirage_inscrit " +
                    "WHERE tp.id_tirage = ?";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setInt(1,idTirageSort);
            ResultSet result = prepStmt.executeQuery();

            while (result.next()) {
                TiragePrix tiragePrix = new TiragePrix();
                tiragePrix.setId(result.getInt("id_tirage_prix"));
                tiragePrix.setPrix(result.getString("prix"));
                tiragePrix.setImage(result.getString("image"));

                ParticipantDAO participantDAO = new ParticipantDAO();
                Participant participantGagnant = participantDAO.find(result.getInt("id_participant"));
                tiragePrix.setGagnant(participantGagnant);

                alTiragePrix.add(tiragePrix);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alTiragePrix;
    }
}
