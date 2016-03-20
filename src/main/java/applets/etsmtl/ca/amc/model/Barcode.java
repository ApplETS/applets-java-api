
package applets.etsmtl.ca.amc.model;

import javax.annotation.Generated;
import java.util.HashMap;
import java.util.Map;

@Generated("org.jsonschema2pojo")
public class Barcode {

    private String status;
    private String barcode;
    private Integer checkinType;
    private String created;
    private String changed;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * 
     * @return
     *     The barcode
     */
    public String getBarcode() {
        return barcode;
    }

    /**
     * 
     * @param barcode
     *     The barcode
     */
    public void setBarcode(String barcode) {
        this.barcode = barcode;
    }

    /**
     * 
     * @return
     *     The checkinType
     */
    public Integer getCheckinType() {
        return checkinType;
    }

    /**
     * 
     * @param checkinType
     *     The checkin_type
     */
    public void setCheckinType(Integer checkinType) {
        this.checkinType = checkinType;
    }

    /**
     * 
     * @return
     *     The created
     */
    public String getCreated() {
        return created;
    }

    /**
     * 
     * @param created
     *     The created
     */
    public void setCreated(String created) {
        this.created = created;
    }

    /**
     * 
     * @return
     *     The changed
     */
    public String getChanged() {
        return changed;
    }

    /**
     * 
     * @param changed
     *     The changed
     */
    public void setChanged(String changed) {
        this.changed = changed;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
