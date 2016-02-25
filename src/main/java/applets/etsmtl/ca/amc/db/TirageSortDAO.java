package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.Participant;
import applets.etsmtl.ca.amc.model.TirageInscrit;
import applets.etsmtl.ca.amc.model.TiragePrix;
import applets.etsmtl.ca.amc.model.TirageSort;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by valentin-debris on 2016-02-10.
 */
public class TirageSortDAO extends DAO<TirageSort> {
    @Override
    public TirageSort find(String idTirage) {
        TirageSort tirage = new TirageSort();
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM tirage_sort " +
                                "WHERE id_tirage = '" +idTirage+"'"
                    );
            if(result.first()) {
                tirage.setId(result.getInt("id_tirage"));
                tirage.setTitre(result.getString("titre"));
                tirage.setDescription(result.getString("description"));

                Timestamp ts = result.getTimestamp("date_debut");
                Date dateDeb = new Date(ts.getTime());
                tirage.setDateDebut(dateDeb);

                ts = result.getTimestamp("date_fin");
                Date dateFin = new Date(ts.getTime());
                tirage.setDateFin(dateFin);

                tirage.setImage(result.getString("image"));

                TiragePrixDAO tiragePrixDAO = new TiragePrixDAO();
                ArrayList<TiragePrix> alTiragePrix = (ArrayList<TiragePrix>)tiragePrixDAO.findAll(idTirage);
                tirage.setPrix(alTiragePrix);
            }
//            else
//                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return tirage;
    }

    @Override
    public List<TirageSort> findAll() {
        ArrayList<TirageSort> alTirage = new ArrayList<TirageSort>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM tirage_sort ORDER BY date_debut"
                    );
            while (result.next()) {
                TirageSort tirage = new TirageSort();
                tirage.setId(result.getInt("id_tirage"));
                tirage.setTitre(result.getString("titre"));
                tirage.setDescription(result.getString("description"));

                Timestamp ts = result.getTimestamp("date_debut");
                Date dateDeb = new Date(ts.getTime());
                tirage.setDateDebut(dateDeb);

                ts = result.getTimestamp("date_fin");
                Date dateFin = new Date(ts.getTime());
                tirage.setDateFin(dateFin);

                tirage.setImage(result.getString("image"));

                TiragePrixDAO tiragePrixDAO = new TiragePrixDAO();
                ArrayList<TiragePrix> alTiragePrix = (ArrayList<TiragePrix>)tiragePrixDAO.findAll(result.getString("id_tirage"));
                tirage.setPrix(alTiragePrix);

                alTirage.add(tirage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alTirage;
    }

    public List<TirageSort> findAll(String idEvent) {
        ArrayList<TirageSort> alTirage = new ArrayList<TirageSort>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM tirage_sort ts " +
                                    "WHERE ts.id_event = '" +idEvent+"' " +
                                    "ORDER BY ts.date_debut"
                    );
            while (result.next()) {
                TirageSort tirage = new TirageSort();
                tirage.setId(result.getInt("id_tirage"));
                tirage.setTitre(result.getString("titre"));
                tirage.setDescription(result.getString("description"));

                Timestamp ts = result.getTimestamp("date_debut");
                Date dateDeb = new Date(ts.getTime());
                tirage.setDateDebut(dateDeb);

                ts = result.getTimestamp("date_fin");
                Date dateFin = new Date(ts.getTime());
                tirage.setDateFin(dateFin);

                tirage.setImage(result.getString("image"));

                TiragePrixDAO tiragePrixDAO = new TiragePrixDAO();
                ArrayList<TiragePrix> alTiragePrix = (ArrayList<TiragePrix>)tiragePrixDAO.findAll(result.getString("id_tirage"));
                tirage.setPrix(alTiragePrix);

                alTirage.add(tirage);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alTirage;
    }
}
