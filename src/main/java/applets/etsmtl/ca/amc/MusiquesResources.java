package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.MusiqueDAO;
import applets.etsmtl.ca.amc.model.BooleanVar;
import applets.etsmtl.ca.amc.model.Musique;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@Path("amc-musiques")
public class MusiquesResources {

    @GET
    @Path("id/{key}")
    //Exemple : http://localhost:8080/rest/musiques/id/1
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Musique getMusique(@PathParam("key") String idMusique) {
        MusiqueDAO musiqueDAO = new MusiqueDAO();
        Musique musique = musiqueDAO.find(idMusique);

        return musique;
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Musique> getAllMusiques() {
        MusiqueDAO musiqueDAO = new MusiqueDAO();
        ArrayList<Musique> allMusique = (ArrayList<Musique>)musiqueDAO.findAll();

        return allMusique;
    }

    @GET
    @Path("all-vote/{key}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Musique> getAllMusiquesForVote(@PathParam("key") String adresseIP) {
        MusiqueDAO musiqueDAO = new MusiqueDAO();
        ArrayList<Musique> allMusique = new ArrayList<Musique>();
        if(!adresseIP.equals(""))
            allMusique = (ArrayList<Musique>)musiqueDAO.findAllFromType(0,adresseIP);
        else
            allMusique = (ArrayList<Musique>)musiqueDAO.findAllFromType(0);

        return allMusique;
    }

    @GET
    @Path("all-elected/{key}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Musique> getAllMusiquesEletedToPlay(@PathParam("key") String adresseIP) {
        MusiqueDAO musiqueDAO = new MusiqueDAO();
        ArrayList<Musique> allMusique = new ArrayList<Musique>();
        if(!adresseIP.equals(""))
            allMusique = (ArrayList<Musique>)musiqueDAO.findAllFromType(1,adresseIP);
        else
            allMusique = (ArrayList<Musique>)musiqueDAO.findAllFromType(1);

        return allMusique;
    }

    @GET
    @Path("all-played/{key}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Musique> getAllMusiquesPlayed(@PathParam("key") String adresseIP) {
        MusiqueDAO musiqueDAO = new MusiqueDAO();
        ArrayList<Musique> allMusique = new ArrayList<Musique>();
        if(!adresseIP.equals(""))
            allMusique = (ArrayList<Musique>)musiqueDAO.findAllFromType(2,adresseIP);
        else
            allMusique = (ArrayList<Musique>)musiqueDAO.findAllFromType(2);

        return allMusique;
    }

    @GET
    @Path("db")
    public Musique testDB() {
        MusiqueDAO musiqueDAO = new MusiqueDAO();
        Musique musique = musiqueDAO.find("1");

        return musique;
    }

    @POST
    @Path("vote/musique/{key1}/adresseIP/{key2}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public BooleanVar voteForASong(@PathParam("key1") int idMusique, @PathParam("key2") String adresseIP) {
        MusiqueDAO musiqueDAO = new MusiqueDAO();
        Boolean hasVoted = false;
        if(idMusique > 0 && !adresseIP.equals(""))
            hasVoted = musiqueDAO.addVoteforSong(idMusique, adresseIP);

        BooleanVar bool = new BooleanVar(hasVoted);
        return bool;
        //return new Boolean(true).toString();
    }
}
