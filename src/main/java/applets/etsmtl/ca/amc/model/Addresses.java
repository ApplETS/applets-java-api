
package applets.etsmtl.ca.amc.model;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@Generated("org.jsonschema2pojo")
public class Addresses {

    private Home home;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The home
     */
    public Home getHome() {
        return home;
    }

    /**
     * 
     * @param home
     *     The home
     */
    public void setHome(Home home) {
        this.home = home;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
