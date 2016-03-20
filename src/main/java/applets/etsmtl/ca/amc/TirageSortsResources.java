package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.EvenementDAO;
import applets.etsmtl.ca.amc.db.ParticipantDAO;
import applets.etsmtl.ca.amc.db.TirageInscritDAO;
import applets.etsmtl.ca.amc.db.TirageSortDAO;
import applets.etsmtl.ca.amc.model.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@Path("amc-tiragesorts")
public class TirageSortsResources {

    @GET
    @Path("id/{key}")
    //Exemple : http://localhost:8080/rest/tirageSorts/id/1
    @Produces(MediaType.APPLICATION_JSON)
    public TirageSort getTirageSort(@PathParam("key") String idTirageSort) {
        TirageSortDAO tirageSortDAO = new TirageSortDAO();
        TirageSort tirageSort = tirageSortDAO.find(idTirageSort);

        return tirageSort;
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<TirageSort> getAllTirageSort() {
        TirageSortDAO tirageSortDAO = new TirageSortDAO();
        ArrayList<TirageSort> allTirageSort = (ArrayList<TirageSort>)tirageSortDAO.findAll();

        return allTirageSort;
    }

    @GET
    @Path("all-event/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    //return all the partners for an event
    public ArrayList<TirageSort> getAllTirageSort(@PathParam("key") String idEvent) {
        TirageSortDAO tirageSortDAO = new TirageSortDAO();
        ArrayList<TirageSort> allTirageSort = (ArrayList<TirageSort>)tirageSortDAO.findAll(idEvent);

        return allTirageSort;
    }

    @GET
    @Path("db")
    public TirageSort testDB() {
        TirageSortDAO tirageSortDAO = new TirageSortDAO();
        TirageSort tirageSort = tirageSortDAO.find("1");

        return tirageSort;
    }

    @POST
    @Path("inscription/bc/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    //looking for a participant with this barcode to add him for the draws
    public BooleanVar inscriptionBC(@PathParam("key") String valueBarCode) {
        BooleanVar value = new BooleanVar(false);

        EventBriteAuth eventBrite = new EventBriteAuth();
        try {
            ArrayList<Attendee> alAttendees = eventBrite.getAttendees(1, new ArrayList<Attendee>());

            Attendee attendeeFound = null;
            for(Attendee att : alAttendees) {
                for(Barcode bc : att.getBarcodes()) {
                    if(bc.getBarcode().equalsIgnoreCase(valueBarCode)) {
                        attendeeFound = att;
                    }
                    if(attendeeFound != null)
                        break;
                }
                if(attendeeFound != null)
                    break;
            }

            if(attendeeFound != null) {
                ParticipantDAO participantDAO = new ParticipantDAO();
                Participant p = participantDAO.findWithEmail(attendeeFound.getProfile().getEmail());

                if(p != null && p.getId() != 0) {
                    TirageInscritDAO tirageInscritDAO = new TirageInscritDAO();
                    EvenementDAO evenementDAO = new EvenementDAO();
                    int idEvent = evenementDAO.findAll().get(0).getId(); //Get the last one (sort by date, DESC)
                    boolean inscriptionWorks = tirageInscritDAO.addTirageInscrit(idEvent, p.getId());
                    if(inscriptionWorks) {
                        value.setValueBool(true);
                    } else
                        value.setExplication("Deja inscrit");
                }
            } else
                value.setExplication("Barcode invalide");
        } catch (IOException e) {
            e.printStackTrace();
        }


        return value;
    }

    @POST
    @Path("inscription/token/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    //looking for a participant with this barcode to add him for the draws
    public BooleanVar inscriptionToken(@PathParam("key") String valueToken) {
        BooleanVar value = new BooleanVar(false);

        EventBriteAuth eventBrite = new EventBriteAuth();
        try {
            ArrayList<String> emailsFromToken = eventBrite.getEmailsFromToken(valueToken);

            ParticipantDAO participantDAO = new ParticipantDAO();
            boolean foundParticipant = false;
            for(String email : emailsFromToken) {
                Participant p = participantDAO.findWithEmail(email);

                if (p != null && p.getId() != 0) {
                    foundParticipant = true;
                    TirageInscritDAO tirageInscritDAO = new TirageInscritDAO();
                    EvenementDAO evenementDAO = new EvenementDAO();
                    int idEvent = evenementDAO.findAll().get(0).getId(); //Get the last one (sort by date, DESC)
                    boolean inscriptionWorks = tirageInscritDAO.addTirageInscrit(idEvent, p.getId());
                    if (inscriptionWorks) {
                        value.setValueBool(true);
                    } else
                        value.setExplication("Deja inscrit");
                    break;
                }
            }
            if(foundParticipant == false)
                value.setExplication("Participant introuvable.");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return value;
    }
}
