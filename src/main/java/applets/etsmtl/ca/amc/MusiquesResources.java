package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.MusiqueDAO;
import applets.etsmtl.ca.amc.model.BooleanVar;
import applets.etsmtl.ca.amc.model.Musique;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@Path("amc-musiques")
@Api(value="/Musiques", description = "")
public class MusiquesResources {

    @GET
    @Path("id/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne une musique",
            notes = "Retourne une musique"
    )
    public Response getMusique(@PathParam("key") int idMusique) {
        MusiqueDAO musiqueDAO = new MusiqueDAO();
        Musique musique = musiqueDAO.find(idMusique);

        if(musique == null)
            return Response.status(Response.Status.NOT_FOUND).entity("Musique non trouvée pour l'id "+idMusique).build();
        else
            return Response.status(200).entity(musique).build();

    }

    @GET
    @Path("all")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne toutes les musiques",
            notes = "Retourne tuotes les musiques"
    )
    public Response getAllMusiques() { //ArrayList<Musique>
        MusiqueDAO musiqueDAO = new MusiqueDAO();
        ArrayList<Musique> allMusique = (ArrayList<Musique>)musiqueDAO.findAll();

        return Response.status(200).entity(allMusique).build();
    }

    @GET
    @Path("all-vote/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne toutes les musiques votées mais pas jouées pour l'IP donnée",
            notes = "Retourne toutes les musiques votées mais pas jouées pour l'IP donnée"
    )
    public Response getAllMusiquesForVote(@PathParam("key") String adresseIP) {
        MusiqueDAO musiqueDAO = new MusiqueDAO();
        ArrayList<Musique> allMusique = new ArrayList<Musique>();
        if(!adresseIP.equals(""))
            allMusique = (ArrayList<Musique>)musiqueDAO.findAllFromType(0,adresseIP);
        else
            allMusique = (ArrayList<Musique>)musiqueDAO.findAllFromType(0);

        return Response.status(200).entity(allMusique).build();
    }

    @GET
    @Path("all-elected/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne toutes les musiques votées qui vont être jouées pour l'IP donnée",
            notes = "Retourne toutes les musiques votées qui vont être jouées pour l'IP donnée"
    )
    public Response getAllMusiquesEletedToPlay(@PathParam("key") String adresseIP) {
        MusiqueDAO musiqueDAO = new MusiqueDAO();
        ArrayList<Musique> allMusique = new ArrayList<Musique>();
        if(!adresseIP.equals(""))
            allMusique = (ArrayList<Musique>)musiqueDAO.findAllFromType(1,adresseIP);
        else
            allMusique = (ArrayList<Musique>)musiqueDAO.findAllFromType(1);

        return Response.status(200).entity(allMusique).build();
    }

    @GET
    @Path("all-played/{key}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(
            value = "Retourne toutes les musiques votées et jouées pour l'IP donnée",
            notes = "Retourne toutes les musiques votées et jouées pour l'IP donnée"
    )
    public Response getAllMusiquesPlayed(@PathParam("key") String adresseIP) {
        MusiqueDAO musiqueDAO = new MusiqueDAO();
        ArrayList<Musique> allMusique = new ArrayList<Musique>();
        if(!adresseIP.equals(""))
            allMusique = (ArrayList<Musique>)musiqueDAO.findAllFromType(2,adresseIP);
        else
            allMusique = (ArrayList<Musique>)musiqueDAO.findAllFromType(2);

        return Response.status(200).entity(allMusique).build();
    }

    @POST
    @Path("vote/musique/{key1}/adresseIP/{key2}")
    @Produces(MediaType.APPLICATION_JSON)
    @ApiOperation(value = "Vote pour une musique")
    public Response voteForASong(@PathParam("key1") int idMusique, @PathParam("key2") String adresseIP) {
        MusiqueDAO musiqueDAO = new MusiqueDAO();
        Boolean hasVoted = false;
        if(idMusique > 0 && !adresseIP.equals(""))
            hasVoted = musiqueDAO.addVoteforSong(idMusique, adresseIP);

//        BooleanVar bool = new BooleanVar(hasVoted);
        return Response.status(200).entity(hasVoted).build();
    }
}
