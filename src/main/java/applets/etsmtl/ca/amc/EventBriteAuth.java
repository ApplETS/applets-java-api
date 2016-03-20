package applets.etsmtl.ca.amc;

import applets.etsmtl.ca.amc.model.Answer;
import applets.etsmtl.ca.amc.model.Attendee;
import com.google.gson.*;
import com.google.gson.reflect.TypeToken;
import com.squareup.okhttp.*;

import java.io.IOException;
import java.util.*;

public class EventBriteAuth {

    /*public static void main(String[] args) throws Exception {

        //EventBriteAuth http = new EventBriteAuth();

        ArrayList<Attendee> attendees = getAttendees(1, new ArrayList<Attendee>());

        System.out.print(attendees.get(1).getProfile().getEmail());
        System.out.print(attendees.get(0).getBarcodes().get(0).getBarcode());
    }*/

    public EventBriteAuth() {}

    public ArrayList<Attendee> getAttendees(int pageNumber, ArrayList<Attendee> attendees) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://www.eventbriteapi.com/v3/events/" +
                        Constants.EVENT_ID +
                        "/attendees/?page=" + pageNumber +
                        "&token=" + Constants.EVENTBRITE_TOKEN)
                .get()
                .build();

        Response response = client.newCall(request).execute();


        JsonElement rootElement = new JsonParser().parse(response.body().string());
        JsonObject rootObject = rootElement.getAsJsonObject();
        JsonObject pagination = rootObject.getAsJsonObject("pagination");
        int pageCount = pagination.get("page_count").getAsInt();

        JsonElement attendeesObject = rootObject.getAsJsonArray("attendees");

        attendees.addAll((ArrayList<Attendee>) new Gson().fromJson(attendeesObject,
                new TypeToken<List<Attendee>>() {
                }.getType()));

        if (pageNumber < pageCount) {
            return getAttendees(pageNumber + 1, attendees);
        } else {
            return attendees;
        }

    }

    public ArrayList<String> getEmailsFromToken(String token) throws IOException {
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://www.eventbriteapi.com/v3/users/me/?token=" + token)
                .get()
                .build();

        Response response = client.newCall(request).execute();

        JsonElement rootElement = new JsonParser().parse(response.body().string());
        JsonObject rootObject = rootElement.getAsJsonObject();

        JsonArray emailsObject = rootObject.getAsJsonArray("emails");
        ArrayList<String> emailsToken = new ArrayList<String>();
        if(emailsObject != null && !emailsObject.isJsonNull()) {
            for (JsonElement codeHolder : emailsObject) {
                emailsToken.add(codeHolder.getAsJsonObject().getAsJsonPrimitive("email").getAsString());
            }
        }
        return emailsToken;
    }

}