package applets.etsmtl.ca.news.jobs.strategy;

import applets.etsmtl.ca.news.db.EventDAO;
import applets.etsmtl.ca.news.db.NouvellesDAO;
import applets.etsmtl.ca.news.db.SourceDAO;
import applets.etsmtl.ca.news.model.Event;
import applets.etsmtl.ca.news.model.Source;
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

/**
 * Created by nicolas on 26/01/16.
 */
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

    private String getDataFromUrl(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();

        Response response = this.okhttpcli.newCall(request).execute();

        return response.body().string();
    }

    @Override
    public void fetchSources() {
        String url_source = "https://graph.facebook.com/v2.5/" + this.value + "?access_token=" + this.token;
        String url_picture_source = "https://graph.facebook.com/v2.5/" + this.value + "/picture?access_token=" + this.token + "&redirect=false";

        if(!this.sourceDao.isExisting(this.key)) {

            try {

                String data_name = getDataFromUrl(url_source);
                String data_picture = getDataFromUrl(url_picture_source);

                JSONObject Jobjet_data = new JSONObject(data_name);
                JSONObject Jobjet_picture = new JSONObject(data_picture);

                String name = Jobjet_data.getString("name");
                String imageURL = Jobjet_picture.getJSONObject("data").getString("url");

                Source source = new Source();

                source.setType("facebook");

                source.setKey(this.key);
                source.setName(name);
                source.setValue(this.value);
                source.setUrlImage(imageURL);

                this.sourceDao.add(source);
            }

            catch(JSONException e) { System.out.println(e); }
            catch(IOException e) { System.out.println(e); }
        }

    }

    @Override
    public void fetchNouvelles() {
        // https://graph.facebook.com/v2.5/" + this.value + "feed?access_token=
    }


    public void fetchEvenements() {
        String url_event = "https://graph.facebook.com/v2.5/" + this.value + "/events?fields=cover{source},name,place,description,start_time,end_time,id&access_token=" + this.token;

        try {
            String data_event = getDataFromUrl(url_event);

            JSONObject Jobjet_data_event = new JSONObject(data_event);

            JSONArray Jarray_data_event = Jobjet_data_event.getJSONArray("data");

            for(int i = 0; i < Jarray_data_event.length(); i++) {
                JSONObject Jobjet_event = Jarray_data_event.getJSONObject(i);

                String id = Jobjet_event.getString("id");

                if(!this.eventDao.isExisting(id)) {

                    String cover = null;
                    if(Jobjet_event.has("cover"))
                        cover = Jobjet_event.getJSONObject("cover").getString("source");

                    String name = Jobjet_event.getString("name");

                    String location_name = null;
                    String location_city = null;
                    String location_country = null;
                    String location_latitude = null;
                    String location_longitude = null;
                    String location_state = null;
                    String location_street = null;
                    String location_zip = null;
                    if(Jobjet_event.has("place")) {
                        location_name = Jobjet_event.getJSONObject("place").getString("name");

                        if (Jobjet_event.getJSONObject("place").has("location")) {
                            location_city = Jobjet_event.getJSONObject("place").getJSONObject("location").optString("city");
                            location_country = Jobjet_event.getJSONObject("place").getJSONObject("location").optString("country");
                            location_latitude = Jobjet_event.getJSONObject("place").getJSONObject("location").optString("latitude");
                            location_longitude = Jobjet_event.getJSONObject("place").getJSONObject("location").optString("longitude");
                            location_state = Jobjet_event.getJSONObject("place").getJSONObject("location").optString("state");
                            location_street = Jobjet_event.getJSONObject("place").getJSONObject("location").optString("street");
                            location_zip = Jobjet_event.getJSONObject("place").getJSONObject("location").optString("zip");
                        }
                    }

                    String description = Jobjet_event.optString("description");
                    String start_date = Jobjet_event.optString("start_time");
                    String end_date = Jobjet_event.optString("end_time");

                    // ****************************************
                    // ADD EVENT

                    Event event = new Event();

                    event.setId(id);
                    event.setNom(name);
                    event.setDebut(parseDate(start_date));
                    event.setFin(parseDate(end_date));
                    event.setNom_lieu(location_name);
                    event.setVille(location_city);
                    event.setEtat(location_state);
                    event.setPays(location_country);
                    event.setAdresse(location_street);
                    event.setCode_postal(location_zip);

                    if(location_longitude != null)
                        event.setLongitude(Float.valueOf(location_longitude));
                    else
                        event.setLongitude(0);

                    if(location_latitude != null)
                        event.setLatitude(Float.valueOf(location_latitude));
                    else
                        event.setLatitude(0);

                    event.setDescription(description);
                    event.setImage(cover);
                    event.setId_source(this.key);

                    this.eventDao.add(event);
                }
            }


        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (NullPointerException e) {
            e.printStackTrace();
        }


    }

    private static final String DATE_FORMAT = "yyyy-MM-dd'T'HH:mm:ss+SSSS";

    private static Date parseDate(String str) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ", Locale.ENGLISH);

        Date date = null;

        try {
            date = sdf.parse(str);
        } catch(ParseException e) {
        }
        return date;
    }
}