package applets.etsmtl.ca.news.jobs.strategy;

import applets.etsmtl.ca.news.db.EventDAO;
import applets.etsmtl.ca.news.db.NouvellesDAO;
import applets.etsmtl.ca.news.db.SourceDAO;
import applets.etsmtl.ca.news.model.Event;
import applets.etsmtl.ca.news.model.Nouvelle;
import applets.etsmtl.ca.news.model.Source;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.Version;
import com.restfb.json.JsonArray;
import com.restfb.json.JsonObject;
import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class FacebookNewsFetcher implements IFetchNewsStrategy {

    private String key;
    private String value;

    private String token;

    private SourceDAO sourceDao;
    private NouvellesDAO nouvelleDao;
    private EventDAO eventDao;

    public FacebookNewsFetcher(String key, String value, String token) {
        this.key = key;
        this.value = value;

        this.token = token;

        this.sourceDao = new SourceDAO();
        this.nouvelleDao = new NouvellesDAO();
        this.eventDao = new EventDAO();
    }

    /*******************************************
     * Fetching sources
     */

    @Override
    public void fetchSources() {

        FacebookClient facebookClient =
                new DefaultFacebookClient(this.token, Version.VERSION_2_8);

        JsonObject jsonSource = facebookClient.fetchObject(this.value, JsonObject.class,
                Parameter.with("fields", "" +
                        "picture, " +
                        "id, " +
                        "name"));

        String name = jsonSource.getString("name");
        String imageURL = jsonSource.getJsonObject("picture")
                .getJsonObject("data")
                .getString("url");

        Source source = new Source();

        source.setType("facebook");

        source.setKey(this.key);
        source.setName(name);
        source.setValue(this.value);
        source.setUrlImage(imageURL);
        if (this.sourceDao.isExisting(this.key)) {

            this.sourceDao.update(source);
        } else {
            this.sourceDao.add(source);
        }

    }

    public void fetchNouvelles() {

        FacebookClient facebookClient =
                new DefaultFacebookClient(this.token, Version.VERSION_2_8);

        JsonObject jsonNewsData =
                facebookClient.fetchObject(this.value + "/posts",
                        JsonObject.class, Parameter.with("fields", "" +
                                "message, " +
                                "link, " +
                                "created_time, " +
                                "name, " +
                                "picture"));

        JsonArray jsonNewsArray = jsonNewsData.getJsonArray("data");

        for (int i = 0; i < jsonNewsArray.length(); i++) {
            JsonObject jsonSingleNews = jsonNewsArray.getJsonObject(i);

            String id = jsonSingleNews.getString("id");

            String message = jsonSingleNews.optString("message");
            String link = jsonSingleNews.optString("link");
            String date = jsonSingleNews.optString("created_time");

            String name = jsonSingleNews.optString("name");
            if (name == null || name.isEmpty()) {
                name = message.substring(0, Math.min(15, message.length()));
            }

            String picture = jsonSingleNews.optString("picture");

            Nouvelle nouvelle = new Nouvelle();

            nouvelle.setId(id);
            nouvelle.setTitre(name);
            nouvelle.setMessage(message);
            nouvelle.setLink(link);
            nouvelle.setDate(parseDate(date));
            nouvelle.setUrlPicture(picture);

            nouvelle.setId_source(this.key);

            if (this.nouvelleDao.isExisting(id)) {
                this.nouvelleDao.update(nouvelle);
            } else {
                this.nouvelleDao.add(nouvelle);
            }
        }
    }


    public void fetchEvenements() {

        FacebookClient facebookClient =
                new DefaultFacebookClient(this.token, Version.VERSION_2_8);

        JsonObject jsonEventData =
                facebookClient.fetchObject(this.value + "/events",
                        JsonObject.class, Parameter.with("fields", "" +
                                "cover{source}, " +
                                "name, " +
                                "place, " +
                                "description, " +
                                "start_time, " +
                                "end_time, " +
                                "id "));

        JsonArray jsonEventsArray = jsonEventData.getJsonArray("data");

        for (int i = 0; i < jsonEventsArray.length(); i++) {
            JsonObject jsonEvent = jsonEventsArray.getJsonObject(i);

            String id = jsonEvent.getString("id");

            String cover = null;
            if (jsonEvent.has("cover"))
                cover = jsonEvent.getJsonObject("cover").getString("source");

            String name = jsonEvent.getString("name");

            String locationName = null;
            String locationCity = null;
            String locationCountry = null;
            String locationLatitude = null;
            String locationLongitude = null;
            String locationState = null;
            String locationStreet = null;
            String locationZip = null;
            if (jsonEvent.has("place")) {
                locationName = jsonEvent.getJsonObject("place").getString("name");

                if (jsonEvent.getJsonObject("place").has("location")) {
                    JsonObject jsonLocationEvent = jsonEvent.getJsonObject("place").getJsonObject("location");

                    locationCity = jsonLocationEvent.optString("city");
                    locationCountry = jsonLocationEvent.optString("country");
                    locationLatitude = jsonLocationEvent.optString("latitude");
                    locationLongitude = jsonLocationEvent.optString("longitude");
                    locationState = jsonLocationEvent.optString("state");
                    locationStreet = jsonLocationEvent.optString("street");
                    locationZip = jsonLocationEvent.optString("zip");
                }
            }

            String description = jsonEvent.optString("description");
            String startDate = jsonEvent.optString("start_time");
            String endDate = jsonEvent.optString("end_time");

            // ****************************************
            // ADD EVENT

            Event event = new Event();

            event.setId(id);
            event.setNom(name);
            event.setDebut(parseDate(startDate));
            event.setFin(parseDate(endDate));
            event.setNom_lieu(locationName);
            event.setVille(locationCity);
            event.setEtat(locationState);
            event.setPays(locationCountry);
            event.setAdresse(locationStreet);
            event.setCode_postal(locationZip);

            if (locationLongitude != null)
                event.setLongitude(Float.valueOf(locationLongitude));
            else
                event.setLongitude(0);

            if (locationLatitude != null)
                event.setLatitude(Float.valueOf(locationLatitude));
            else
                event.setLatitude(0);

            event.setDescription(description);
            event.setImage(cover);
            event.setId_source(this.key);

            if (this.eventDao.isExisting(id)) {
                this.eventDao.update(event);
            } else {
                this.eventDao.add(event);
            }
        }

    }

    private static Date parseDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);

        Date date = null;

        try {
            date = sdf.parse(str);
        } catch (ParseException e) {
            //todo fix that ugly catch
        }
        return date;
    }
}