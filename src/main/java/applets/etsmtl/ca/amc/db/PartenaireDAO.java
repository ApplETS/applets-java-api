package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.Evenement;
import applets.etsmtl.ca.amc.model.Partenaire;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by valentin-debris on 2016-02-10.
 */
public class PartenaireDAO extends DAO<Partenaire> {
    @Override
    public Partenaire find(String key) {
        Partenaire partenaire = new Partenaire();
        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM partenaire WHERE id_partenaire = '" +key+"'"
                    );
            if(result.first()) {
                partenaire.setId(result.getInt("id_partenaire"));
                partenaire.setNom(result.getString("nom"));
                partenaire.setImage(result.getString("image"));
            }
//            else
//                return null;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partenaire;
    }

    @Override
    public List<Partenaire> findAll() {
        ArrayList<Partenaire> alPartenaire = new ArrayList<Partenaire>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM partenaire ORDER BY id_partenaire"
                    );
            while (result.next()) {
                Partenaire partenaire = new Partenaire();
                partenaire.setId(result.getInt("id_partenaire"));
                partenaire.setNom(result.getString("nom"));
                partenaire.setImage(result.getString("image"));

                alPartenaire.add(partenaire);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alPartenaire;
    }

    public List<Partenaire> findAll(String idEvent) {
        ArrayList<Partenaire> alPartenaire = new ArrayList<Partenaire>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM partenaire p " +
                                    "JOIN partenaire_evenement pe " +
                                        "ON p.id_partenaire=pe.id_partenaire " +
                                    "WHERE pe.id_event = '" +idEvent+"' " +
                                    "ORDER BY p.id_partenaire"
                    );
            while (result.next()) {
                Partenaire partenaire = new Partenaire();
                partenaire.setId(result.getInt("id_partenaire"));
                partenaire.setNom(result.getString("nom"));
                partenaire.setImage(result.getString("image"));

                alPartenaire.add(partenaire);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alPartenaire;
    }
}
