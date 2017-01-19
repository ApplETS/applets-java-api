package applets.etsmtl.ca.hello;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Assert;
import org.junit.Test;

import javax.ws.rs.core.Application;

public class HelloWorldResourceTest extends JerseyTest {

    @Override
    protected Application configure() {
        return new ResourceConfig(HelloWorldResource.class);
    }

    @Test
    public void sayhello() throws Exception {
        String response = target("/hello").request().get(String.class);
        Assert.assertTrue("hello".equals(response));
    }

}