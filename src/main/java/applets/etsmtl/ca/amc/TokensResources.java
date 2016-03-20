package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.db.StreamingDAO;
import applets.etsmtl.ca.amc.db.TokenDAO;
import applets.etsmtl.ca.amc.model.BooleanVar;
import applets.etsmtl.ca.amc.model.Streaming;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-03-18.
 */
@Path("amc-tokens")
public class TokensResources {

    @POST
    @Path("newtoken/")
    @Produces(MediaType.APPLICATION_JSON)
    public BooleanVar postNewToken(String data) {
        BooleanVar val = new BooleanVar(true);
        JsonElement rootElement = new JsonParser().parse(data);
        JsonObject rootObject = rootElement.getAsJsonObject();
        String valueToken = rootObject.get("tokenID").getAsString();

        TokenDAO tokenDAO = new TokenDAO();
        boolean res = tokenDAO.addToken(valueToken);
        val.setValueBool(res);
//        System.out.println("token : "+valueToken);
        return val;
    }
}
