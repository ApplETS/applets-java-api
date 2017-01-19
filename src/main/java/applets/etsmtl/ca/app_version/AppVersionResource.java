package applets.etsmtl.ca.app_version;

import com.squareup.okhttp.OkHttpClient;
import com.squareup.okhttp.Request;
import com.squareup.okhttp.Response;
import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;
import net.sf.ehcache.Element;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import java.io.IOException;

@Path("app_version")
public class AppVersionResource {


    private String ETSMOBILE_IOS_URL = "https://itunes.apple.com/lookup?id=557463461";
    private String ETSMOBILE_ANDROID_URL = "https://play.google.com/store/apps/details?id=ca.etsmtl.applets.etsmobile";


    @GET
    @Path("{os}")
    public String getVersion(@PathParam("os") String os) {

        String softwareVersion = "";
        OkHttpClient client = new OkHttpClient();
        Request request;
        Response response = null;

        CacheManager cacheManager = CacheManager.getInstance();
        Cache cache = cacheManager.getCache("app_version");
        
        Element element;

        switch (os) {
            case "android":

                element = cache.get("android");
                if (element != null) {
                    softwareVersion = (String) element.getObjectValue();
                    break;
                }

                request = new Request.Builder()
                        .url(ETSMOBILE_ANDROID_URL)
                        .get()
                        .build();

                try {
                    response = client.newCall(request).execute();
                    Document document = Jsoup.parseBodyFragment(response.body().string());
                    Elements divSoftwareVersion = document.select("div[itemprop=softwareVersion]");
                    softwareVersion = divSoftwareVersion.text();

                } catch (IOException e) {
                    e.printStackTrace();
                }

                cache.put(new Element("android", softwareVersion));

                break;

            case "ios":

                element = cache.get("ios");
                if (element != null) {
                    softwareVersion = (String) element.getObjectValue();
                    break;
                }

                request = new Request.Builder()
                        .url(ETSMOBILE_IOS_URL)
                        .get()
                        .build();

                try {
                    response = client.newCall(request).execute();
                    JSONObject jsonObject = new JSONObject(response.body().string());
                    JSONArray jsonArray = jsonObject.getJSONArray("results");

                    if (jsonArray.length() == 0)
                        break;

                    softwareVersion = jsonArray.getJSONObject(0).getString("version");

                } catch (IOException | JSONException e) {
                    e.printStackTrace();
                }
                cache.put(new Element("ios", softwareVersion));

                break;
        }

        return softwareVersion;
    }
}
