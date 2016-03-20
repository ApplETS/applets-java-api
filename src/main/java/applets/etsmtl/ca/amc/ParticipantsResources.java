package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.ParticipantDAO;
import applets.etsmtl.ca.amc.model.Participant;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@Path("amc-participants")
public class ParticipantsResources {

    @GET
    @Path("id/{key}")
    //Exemple : http://localhost:8080/rest/participants/id/1
    @Produces(MediaType.APPLICATION_JSON)
    public Participant getParticipant(@PathParam("key") String idParticipant) {
        ParticipantDAO participantDAO = new ParticipantDAO();
        Participant participant = participantDAO.find(idParticipant);

        return participant;
    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    public ArrayList<Participant> getAllParticipants() {
        ParticipantDAO participantDAO = new ParticipantDAO();
        ArrayList<Participant> allParticipant = (ArrayList<Participant>)participantDAO.findAll();

        return allParticipant;
    }

    @GET
    @Path("all-event/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    //return all the participants for an event
    public ArrayList<Participant> getAllParticipantsForEvent(@PathParam("key") String idEvent) {
        ParticipantDAO participantDAO = new ParticipantDAO();
        ArrayList<Participant> allParticipant = (ArrayList<Participant>)participantDAO.findAll(idEvent);

        return allParticipant;
    }

    @GET
    @Path("all-equipe-event/{key}") //key="12-20", 12=idEvent, 20=idEquipe
    @Produces(MediaType.APPLICATION_JSON)
    //return all the participants for an event
    public ArrayList<Participant> getAllParticipantsForEquipeEvent(@PathParam("key") String data) {
        String[] ids = data.split("-");
        ParticipantDAO participantDAO = new ParticipantDAO();
        ArrayList<Participant> allParticipant = (ArrayList<Participant>)participantDAO.findAll(ids[0],ids[1]);

        return allParticipant;
    }

    @GET
    @Path("db")
    public Participant testDB() {
        ParticipantDAO participantDAO = new ParticipantDAO();
        Participant participant = participantDAO.find("1");

        return participant;
    }
}
