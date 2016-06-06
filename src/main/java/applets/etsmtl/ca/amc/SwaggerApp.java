package applets.etsmtl.ca.amc;

/**
 * Created by ValentinD on 2016-03-23.
 */

import io.swagger.jaxrs.config.BeanConfig;

import javax.ws.rs.ApplicationPath;
import javax.ws.rs.core.Application;
import javax.ws.rs.ext.Provider;
import java.util.HashSet;
import java.util.Set;

@Provider
@ApplicationPath("rest/amc-doc/")
public class SwaggerApp extends Application {

    @Override
    public Set<Class<?>> getClasses() {
        Set<Class<?>> resources = new HashSet();

        resources.add(EvenementsResources.class);
        resources.add(EquipesResources.class);
        resources.add(IntervenantsResources.class);
        resources.add(MusiquesResources.class);
        resources.add(PartenairesResources.class);
        resources.add(ParticipantsResources.class);
        resources.add(StreamingsResources.class);
        resources.add(TirageSortsResources.class);


        //resources.add(SecondResource.class);

        resources.add(io.swagger.jaxrs.listing.ApiListingResource.class);
        resources.add(io.swagger.jaxrs.listing.SwaggerSerializers.class);

        return resources;
    }

    public SwaggerApp(){
        BeanConfig beanConfig = new BeanConfig();
        beanConfig.setVersion("1.0.2");
        beanConfig.setSchemes(new String[]{"http"});
        beanConfig.setHost("localhost:8080");
        beanConfig.setBasePath("/rest");
        beanConfig.setResourcePackage("applets.etsmtl.ca.amc");
        beanConfig.setScan(true);
    }
}