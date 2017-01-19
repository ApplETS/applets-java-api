package partners;

import applets.etsmtl.ca.partners.Partner;
import applets.etsmtl.ca.partners.PartnersResource;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.GenericType;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class PartnersResourcesTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(PartnersResource.class);
    }

    @Test
    public void testGetPartners() {
        MockWebServer server = new MockWebServer();

        try {
            final String filePath = getClass().getResource("/clubapplets_partners_body.html").getPath();
            String partnersHtml = readFile(filePath, StandardCharsets.UTF_8);

            server.enqueue(new MockResponse().setBody(partnersHtml));
            server.start();
            String url = server.url("/partners").toString();
            PartnersResource.PARTNERS_URL = url;

            List<Partner> response = target("/partners").request().get(new GenericType<List<Partner>>() {
            });

            Assert.assertTrue(response.size() > 0);

            PartnersResource.PARTNERS_URL = "";

            //Test caching
            response = target("/partners").request().get(new GenericType<List<Partner>>() {
            });
            Assert.assertTrue(response.size() > 0);

            server.shutdown();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    String readFile(String path, Charset encoding) throws IOException {
        byte[] encoded = Files.readAllBytes(Paths.get(path));
        return new String(encoded, encoding);
    }


}


