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
    private OkHttpClient okhttpcli;

    public FacebookNewsFetcher(String key, String value, String token) {
        this.key = key;
        this.value = value;

        this.token = token;

        this.sourceDao = new SourceDAO();
        this.nouvelleDao = new NouvellesDAO();
        this.eventDao = new EventDAO();

        this.okhttpcli = new OkHttpClient();
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
        String imageURL = jsonSource.getJsonObject("picture").getJsonObject("data").getString("url");

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
            if ((name == null) || ((name != null) && (name.isEmpty()))) {
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

            String location_name = null;
            String location_city = null;
            String location_country = null;
            String location_latitude = null;
            String location_longitude = null;
            String location_state = null;
            String location_street = null;
            String location_zip = null;
            if (jsonEvent.has("place")) {
                location_name = jsonEvent.getJsonObject("place").getString("name");

                if (jsonEvent.getJsonObject("place").has("location")) {
                    JsonObject jsonLocationEvent = jsonEvent.getJsonObject("place").getJsonObject("location");

                    location_city = jsonLocationEvent.optString("city");
                    location_country = jsonLocationEvent.optString("country");
                    location_latitude = jsonLocationEvent.optString("latitude");
                    location_longitude = jsonLocationEvent.optString("longitude");
                    location_state = jsonLocationEvent.optString("state");
                    location_street = jsonLocationEvent.optString("street");
                    location_zip = jsonLocationEvent.optString("zip");
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
            event.setNom_lieu(location_name);
            event.setVille(location_city);
            event.setEtat(location_state);
            event.setPays(location_country);
            event.setAdresse(location_street);
            event.setCode_postal(location_zip);

            if (location_longitude != null)
                event.setLongitude(Float.valueOf(location_longitude));
            else
                event.setLongitude(0);

            if (location_latitude != null)
                event.setLatitude(Float.valueOf(location_latitude));
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

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss+SSSS";

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