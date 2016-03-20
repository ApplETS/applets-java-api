package applets.etsmtl.ca.amc.jobs;

import applets.etsmtl.ca.amc.db.TokenDAO;
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

    //TODO, stocker la valeur dans la BD ?
    private  String appID = "8aab76d5";

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

//        OkHttpClient client = new OkHttpClient();
//
//        //TODO, optimiser les valeurs de Bearer et de l'app id avec des valeurs dans la BD ??
//        Request request = new Request.Builder()
//                .url("https://api.ionic.io/push/tokens")
//                .get()
//                .addHeader("content-type", "application/json")
//                .addHeader("authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwZGFjMDM5Yi03ZjJlLTRmZTAtYTc2NS04ZDc1ZDJhZjAwMzEifQ.8Jzmr34P-kXSXu0M82CAhIjZ4n6nT3Wp7tV0ivg54eA")
//                .build();
//
//        ArrayList<String> listTokens = new ArrayList<String>();
//        try {
//            Response response = client.newCall(request).execute();
//
//            System.out.println("deb");
//            JsonElement rootElement = new JsonParser().parse(response.body().string());
//            JsonObject rootObject = rootElement.getAsJsonObject();
//
//            JsonArray tokenObject = rootObject.getAsJsonArray("data");
//            if(tokenObject != null && !tokenObject.isJsonNull()) {
//                for (JsonElement tokenElem : tokenObject) {
//                    if(tokenElem.getAsJsonObject().getAsJsonPrimitive("app_id").getAsString().equalsIgnoreCase(appID) &&
//                            tokenElem.getAsJsonObject().getAsJsonPrimitive("valid").getAsBoolean() == true) {
//                        listTokens.add(tokenElem.getAsJsonObject().getAsJsonPrimitive("token").getAsString());
//                    }
//                }
//            }
//        } catch (IOException e) {
//            System.out.print("Erreur : "+e);
//            e.printStackTrace();
//        }
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
        //TODO, sauvegarder le nom du profil dans la bd ?
        mainObj.addProperty("profile", "amc_mobile_prod");
        mainObj.addProperty("title", "AMC Mobile");
        mainObj.add("notification", notifObj);

        MediaType mediaType = MediaType.parse("application/json");
        RequestBody body = RequestBody.create(mediaType, mainObj.toString());
        Request request = new Request.Builder()
                .url("https://api.ionic.io/push/notifications")
                .post(body)
                .addHeader("content-type", "application/json")
                .addHeader("authorization", "Bearer eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJqdGkiOiIwZGFjMDM5Yi03ZjJlLTRmZTAtYTc2NS04ZDc1ZDJhZjAwMzEifQ.8Jzmr34P-kXSXu0M82CAhIjZ4n6nT3Wp7tV0ivg54eA")
                .build();
        try {
            Response response = client.newCall(request).execute();
            System.out.println(response.body().string());

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
