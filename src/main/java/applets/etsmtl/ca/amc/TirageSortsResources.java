package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.EvenementDAO;
import applets.etsmtl.ca.amc.db.ParticipantDAO;
import applets.etsmtl.ca.amc.db.TirageInscritDAO;
import applets.etsmtl.ca.amc.db.TirageSortDAO;
import applets.etsmtl.ca.amc.model.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@Path("amc-tiragesorts")
@Api(value = "/Tirages au sort")
public class TirageSortsResources {

    @GET
    @Path("id/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne un tirage au sort",
            notes = "Retourne un tirage au sort"
    )
    public Response getTirageSort(@PathParam("key") int idTirageSort) {
        TirageSortDAO tirageSortDAO = new TirageSortDAO();
        TirageSort tirageSort = tirageSortDAO.find(idTirageSort);

        if(tirageSort == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Tirage non trouvé pour l'id "+idTirageSort).build();
        else
            return Response.status(200).entity(tirageSort).build();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne tous les tirages au sort",
            notes = "Retourne tous les tirages au sort"
    )
    public Response getAllTirageSort() {
        TirageSortDAO tirageSortDAO = new TirageSortDAO();
        ArrayList<TirageSort> allTirageSort = (ArrayList<TirageSort>)tirageSortDAO.findAll();

        return Response.status(200).entity(allTirageSort).build();
    }

    @GET
    @Path("all-event/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne tous les tirages pour un évènement",
            notes = "Retourne tous les tirages pour un évènement"
    )
    public Response getAllTirageSort(@PathParam("key") int idEvent) {
        TirageSortDAO tirageSortDAO = new TirageSortDAO();
        ArrayList<TirageSort> allTirageSort = (ArrayList<TirageSort>)tirageSortDAO.findAll(idEvent);

        return Response.status(200).entity(allTirageSort).build();
    }

    @POST
    @Path("inscription/bc/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Inscription via un code bar")
    //looking for a participant with this barcode to add him for the draws
    public Response inscriptionBC(@PathParam("key") String valueBarCode) {
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

        return Response.status(200).entity(value).build();
    }

    @POST
    @Path("inscription/token/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Inscription via un token (de Eventbrite)")
    //looking for a participant with this barcode to add him for the draws
    public Response inscriptionToken(@PathParam("key") String valueToken) {
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

        return Response.status(200).entity(value).build();
    }
}
