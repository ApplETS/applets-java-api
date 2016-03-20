package applets.etsmtl.ca.amc.db;

import applets.etsmtl.ca.amc.model.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.*;

/**
 * Created by valentin-debris on 2016-02-10.
 */
public class EvenementDAO extends DAO<Evenement> {
    @Override
    public Evenement find(String key) {
        Evenement event = new Evenement();
        try {
            String selectStatement = "SELECT * FROM evenement WHERE id_event = ?";
            PreparedStatement prepStmt = this.connection.prepareStatement(selectStatement,ResultSet.TYPE_SCROLL_INSENSITIVE,
                    ResultSet.CONCUR_READ_ONLY);
            prepStmt.setInt(1,Integer.parseInt(key));
            ResultSet result = prepStmt.executeQuery();

            if(result.first()) {
                event.setId(result.getInt("id_event"));
                event.setNom(result.getString("nom"));
                event.setPresentation(result.getString("presentation"));
                event.setHashtag(result.getString("hashtag"));
                event.setLienEventbrite(result.getString("lien_eventbrite"));

                Timestamp ts = result.getTimestamp("date_debut");
                Date dateDeb = new Date(ts.getTime());
                event.setDateDebut(dateDeb);

                ts = result.getTimestamp("date_fin");
                Date dateFin = new Date(ts.getTime());
                event.setDateFin(dateFin);

                String idEvent = result.getString("id_event");
                EquipeDAO equipeDAO = new EquipeDAO();
                event.setEquipes((ArrayList<Equipe>) equipeDAO.findAll(idEvent));

                PartenaireDAO partenaireDAO = new PartenaireDAO();
                event.setPartenaires((ArrayList<Partenaire>) partenaireDAO.findAll(idEvent));

                IntervenantDAO intervenantDAO = new IntervenantDAO();
                event.setIntervenants((ArrayList<Intervenant>) intervenantDAO.findAll(idEvent));

                TirageSortDAO tirageSortDAO = new TirageSortDAO();
                event.setTirageSorts((ArrayList<TirageSort>) tirageSortDAO.findAll(idEvent));

                TirageInscritDAO tirageInscritDAO = new TirageInscritDAO();
                event.setTirageInscrits((ArrayList<TirageInscrit>) tirageInscritDAO.findAll(idEvent));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return event;
    }

    @Override
    public List<Evenement> findAll() {
        ArrayList<Evenement> alEvent = new ArrayList<Evenement>();

        try {
            ResultSet result = this.connection
                    .createStatement(
                            ResultSet.TYPE_SCROLL_INSENSITIVE,
                            ResultSet.CONCUR_READ_ONLY
                    ).executeQuery(
                            "SELECT * FROM evenement ORDER BY date_debut DESC"
                    );
            while (result.next()) {
                Evenement event = new Evenement();
                event.setId(result.getInt("id_event"));
                event.setNom(result.getString("nom"));
                event.setPresentation(result.getString("presentation"));
                event.setHashtag(result.getString("hashtag"));
                event.setLienEventbrite(result.getString("lien_eventbrite"));

                Timestamp ts = result.getTimestamp("date_debut");
                Date dateDeb = new Date(ts.getTime());
                event.setDateDebut(dateDeb);

                ts = result.getTimestamp("date_fin");
                Date dateFin = new Date(ts.getTime());
                event.setDateFin(dateFin);

                String idEvent = result.getString("id_event");
                EquipeDAO equipeDAO = new EquipeDAO();
                event.setEquipes((ArrayList<Equipe>)equipeDAO.findAll(idEvent));

                PartenaireDAO partenaireDAO = new PartenaireDAO();
                event.setPartenaires((ArrayList<Partenaire>)partenaireDAO.findAll(idEvent));

                IntervenantDAO intervenantDAO = new IntervenantDAO();
                event.setIntervenants((ArrayList<Intervenant>) intervenantDAO.findAll(idEvent));

                TirageSortDAO tirageSortDAO = new TirageSortDAO();
                event.setTirageSorts((ArrayList<TirageSort>) tirageSortDAO.findAll(idEvent));

                TirageInscritDAO tirageInscritDAO = new TirageInscritDAO();
                event.setTirageInscrits((ArrayList<TirageInscrit>) tirageInscritDAO.findAll(idEvent));

                alEvent.add(event);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return alEvent;
    }
}
