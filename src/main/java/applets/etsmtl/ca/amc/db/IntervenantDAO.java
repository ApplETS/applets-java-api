package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.Intervenant;

import java.sql.PreparedStatement;
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
    public Intervenant find(int key) {
        Intervenant intervenant = new Intervenant();
        try {
            String selectStatement = "SELECT * FROM intervenant WHERE id_intervenant = ? ";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setInt(1,key);
            ResultSet result = prepStmt.executeQuery();

            if(result.first()) {
                intervenant.setId(result.getInt("id_intervenant"));
                intervenant.setNom(result.getString("nom"));
                intervenant.setImage(result.getString("image"));
                intervenant.setBiographie(result.getString("biographie"));
            }
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

    public List<Intervenant> findAll(int idEvent) {
        ArrayList<Intervenant> alIntervenant = new ArrayList<Intervenant>();

        try {
            String selectStatement = "SELECT * FROM intervenant i " +
                                        "JOIN intervenant_evenement pe " +
                                        "ON i.id_intervenant=pe.id_intervenant " +
                                        "WHERE pe.id_event = ? " +
                                        "ORDER BY date_debut ASC";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setInt(1,idEvent);
            ResultSet result = prepStmt.executeQuery();

            while (result.next()) {
                Intervenant intervenant = new Intervenant();
                intervenant.setId(result.getInt("id_intervenant"));
                intervenant.setNom(result.getString("nom"));
                intervenant.setImage(result.getString("image"));
                intervenant.setBiographie(result.getString("biographie"));

                intervenant.setDescription(result.getString("description"));

//                Timestamp ts = result.getTimestamp("date_debut");
//                Date dateDeb = new Date(ts.getTime());
                intervenant.setDateDebut(result.getLong("date_debut"));

//                ts = result.getLong("date_fin");
//                Date dateFin = new Date(ts.getTime());
                intervenant.setDateFin(result.getLong("date_fin"));

                intervenant.setLieu(result.getString("lieu"));

                alIntervenant.add(intervenant);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alIntervenant;
    }
}
