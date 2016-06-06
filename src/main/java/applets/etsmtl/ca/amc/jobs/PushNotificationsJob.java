package applets.etsmtl.ca.amc.jobs;

import applets.etsmtl.ca.amc.db.GlobalDataDAO;
import applets.etsmtl.ca.amc.db.TokenDAO;
import applets.etsmtl.ca.amc.model.GlobalData;
import applets.etsmtl.ca.amc.model.Token;
import com.google.gson.*;
import com.squareup.okhttp.*;
import org.codehaus.jettison.json.JSONArray;
import org.quartz.Job;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by valentin-debris on 2016-02-10.
 */

public class PushNotificationsJob implements Job {

    @Override
    public void execute(JobExecutionContext context) throws JobExecutionException {
//        System.out.println("Executing Push Notif Job : START.");

        ArrayList<Token> listTokens = getListTokens();
//        System.out.println(listTokens.size()+" tokens");

//        sendNotifications(listTokens);
    }

    public ArrayList<Token> getListTokens() {
        TokenDAO tokenDAO = new TokenDAO();
        ArrayList<Token> listTokens = (ArrayList<Token>)tokenDAO.findAll();

        GlobalDataDAO gdDAO = new GlobalDataDAO();
        GlobalData gd = gdDAO.findData();

        String appID = gd.getIonicID();
        String bearer = gd.getIonicBearer();

        return listTokens;
    }

    public void sendNotifications(ArrayList<Token> listTokens) {
        OkHttpClient client = new OkHttpClient();

        JsonObject mainObj = new JsonObject();
        JsonObject notifObj = new JsonObject();
        JsonObject androidObj = new JsonObject();
        JsonObject iosObj = new JsonObject();

        androidObj.addProperty("title", "Test Android");
        androidObj.addProperty("message", "Message Android");

        iosObj.addProperty("title", "Test IOS");
        iosObj.addProperty("message", "Message IOS");

        notifObj.addProperty("title", "Test Notif");
        notifObj.addProperty("message", "Message Notif");
        notifObj.add("android", androidObj);
        notifObj.add("ios", iosObj);

        JsonArray arrayTokens = new JsonArray();
        for (Token token : listTokens) {
            arrayTokens.add(new JsonPrimitive(token.getValue()));
        }
        mainObj.add("tokens", arrayTokens);

        GlobalDataDAO gdDAO = new GlobalDataDAO();
        GlobalData gd = gdDAO.findData();

        mainObj.addProperty("profile", gd.getIonicProfile());
        mainObj.addProperty("title", "AMC Mobile");
        mainObj.add("notification", notifObj);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, mainObj.toString());
        Request request = new Request.Builder()
                .url("https://api.ionic.io/push/notifications")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer "+gd.getIonicBearer())
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
