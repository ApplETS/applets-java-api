package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.Intervenant;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by valentin-debris on 2016-02-10.
 */
public class IntervenantDAO extends DAO<Intervenant> {
    @Override
    public Intervenant find(String key) {
        Intervenant intervenant = new Intervenant();
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM intervenant WHERE id_intervenant = '" +key+"'"
                    );
            if(result.first()) {
                intervenant.setId(result.getInt("id_intervenant"));
                intervenant.setNom(result.getString("nom"));
                intervenant.setImage(result.getString("image"));
                intervenant.setBiographie(result.getString("biographie"));
            }
//            else
//                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return intervenant;
    }

    @Override
    public List<Intervenant> findAll() {
        ArrayList<Intervenant> alIntervenant = new ArrayList<Intervenant>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM intervenant ORDER BY id_intervenant"
                    );
            while (result.next()) {
                Intervenant intervenant = new Intervenant();
                intervenant.setId(result.getInt("id_intervenant"));
                intervenant.setNom(result.getString("nom"));
                intervenant.setImage(result.getString("image"));
                intervenant.setBiographie(result.getString("biographie"));

                alIntervenant.add(intervenant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alIntervenant;
    }

    public List<Intervenant> findAll(String idEvent) {
        ArrayList<Intervenant> alIntervenant = new ArrayList<Intervenant>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM intervenant i " +
                                    "JOIN intervenant_evenement pe " +
                                        "ON i.id_intervenant=pe.id_intervenant " +
                                    "WHERE pe.id_event = '" +idEvent+"' " +
                                    "ORDER BY date_debut"
                    );
            while (result.next()) {
                Intervenant intervenant = new Intervenant();
                intervenant.setId(result.getInt("id_intervenant"));
                intervenant.setNom(result.getString("nom"));
                intervenant.setImage(result.getString("image"));
                intervenant.setBiographie(result.getString("biographie"));

                intervenant.setDescription(result.getString("description"));

                Timestamp ts = result.getTimestamp("date_debut");
                Date dateDeb = new Date(ts.getTime());
                intervenant.setDateDebut(dateDeb);

                ts = result.getTimestamp("date_fin");
                Date dateFin = new Date(ts.getTime());
                intervenant.setDateFin(dateFin);

                intervenant.setLieu(result.getString("lieu"));

                alIntervenant.add(intervenant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alIntervenant;
    }
}
