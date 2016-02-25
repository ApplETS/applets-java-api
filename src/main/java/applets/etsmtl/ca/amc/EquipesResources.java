package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.EquipeDAO;
import applets.etsmtl.ca.amc.model.Equipe;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@Path("amc-equipes")
public class EquipesResources {

    @GET
    @Path("id/{key}")
    //Exemple : http://localhost:8080/rest/equipes/id/1
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public Equipe getEquipe(@PathParam("key") String idEquipe) {
        EquipeDAO equipeDAO = new EquipeDAO();
        Equipe equipe = equipeDAO.find(idEquipe);

        return equipe;
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<Equipe> getAllEquipes() {
        EquipeDAO equipeDAO = new EquipeDAO();
        ArrayList<Equipe> allEquipe = (ArrayList<Equipe>)equipeDAO.findAll();

        return allEquipe;
    }

    @GET
    @Path("all-event/{key}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    //return all the equipes for an event
    public ArrayList<Equipe> getAllEquipesForEvent(@PathParam("key") String idEvent) {
        EquipeDAO equipeDAO = new EquipeDAO();
        ArrayList<Equipe> allEquipe = (ArrayList<Equipe>)equipeDAO.findAll(idEvent);

        return allEquipe;
    }

    @GET
    @Path("db")
    public Equipe testDB() {
        EquipeDAO equipeDAO = new EquipeDAO();
        Equipe equipe = equipeDAO.find("1");

        return equipe;
    }
}
