package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.ParticipantDAO;
import applets.etsmtl.ca.amc.model.Participant;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@Path("amc-participants")
@Api(value = "/Participants")
public class ParticipantsResources {

    @GET
    @Path("id/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne un participant",
            notes = "Retourne un participant"
    )
    public Response getParticipant(@PathParam("key") int idParticipant) {
        ParticipantDAO participantDAO = new ParticipantDAO();
        Participant participant = participantDAO.find(idParticipant);

        if(participant == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Participant non trouvé pour l'id "+idParticipant).build();
        else
            return Response.status(200).entity(participant).build();
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne tous les participants",
            notes = "Retourne tous les participants"
    )
    public Response getAllParticipants() {
        ParticipantDAO participantDAO = new ParticipantDAO();
        ArrayList<Participant> allParticipant = (ArrayList<Participant>)participantDAO.findAll();

        return Response.status(200).entity(allParticipant).build();
    }

    @GET
    @Path("all-event/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne tous les participants pour un évènement",
            notes = "Retourne tous les participants pour un évènement"
    )
    //return all the participants for an event
    public Response getAllParticipantsForEvent(@PathParam("key") int idEvent) {
        ParticipantDAO participantDAO = new ParticipantDAO();
        ArrayList<Participant> allParticipant = (ArrayList<Participant>)participantDAO.findAll(idEvent);

        return Response.status(200).entity(allParticipant).build();
    }

    @GET
    @Path("all-equipe-event/{key}") //key="12-20", 12=idEvent, 20=idEquipe
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne tous les participants pour une équipe à un évènement",
            notes = "Retourne tous les participants pour une équipe à un évènement"
    )
    //return all the participants for an event
    public Response getAllParticipantsForEquipeEvent(@PathParam("key") String data) {
        String[] ids = data.split("-");
        ParticipantDAO participantDAO = new ParticipantDAO();
        ArrayList<Participant> allParticipant = (ArrayList<Participant>)participantDAO.findAll(Integer.parseInt(ids[0]),Integer.parseInt(ids[1]));

        return Response.status(200).entity(allParticipant).build();
    }
}
