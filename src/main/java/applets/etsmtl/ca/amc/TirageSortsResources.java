package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.TirageSortDAO;
import applets.etsmtl.ca.amc.model.TirageSort;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@Path("amc-tiragesorts")
public class TirageSortsResources {

    @GET
    @Path("id/{key}")
    //Exemple : http://localhost:8080/rest/tirageSorts/id/1
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public TirageSort getTirageSort(@PathParam("key") String idTirageSort) {
        TirageSortDAO tirageSortDAO = new TirageSortDAO();
        TirageSort tirageSort = tirageSortDAO.find(idTirageSort);

        return tirageSort;
    }

    @GET
    @Path("all")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
    public ArrayList<TirageSort> getAllTirageSort() {
        TirageSortDAO tirageSortDAO = new TirageSortDAO();
        ArrayList<TirageSort> allTirageSort = (ArrayList<TirageSort>)tirageSortDAO.findAll();

        return allTirageSort;
    }

    @GET
    @Path("all-event/{key}")
    @Produces({MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML})
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
}
