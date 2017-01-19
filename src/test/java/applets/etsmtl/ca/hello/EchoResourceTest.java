package applets.etsmtl.ca.hello;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;

public class EchoResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(EchoResource.class);
    }

    @Test
    public void echo() throws Exception {
        String response = target("/echo").queryParam("m","test").request().get(String.class);
        Assert.assertTrue("echo: test".equals(response));
    }

}