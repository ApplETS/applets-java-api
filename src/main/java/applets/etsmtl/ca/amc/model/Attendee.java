package applets.etsmtl.ca.amc.model;

import javax.annotation.Generated;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Generated("org.jsonschema2pojo")
public class Attendee {

    private Object team;
    private String resourceUri;
    private String id;
    private String changed;
    private String created;
    private Integer quantity;
    private Profile profile;
    private List<Barcode> barcodes = new ArrayList<Barcode>();
    private List<Answer> answers = new ArrayList<Answer>();
    private Costs costs;
    private Boolean checkedIn;
    private Boolean cancelled;
    private Boolean refunded;
    private Object affiliate;
    private String status;
    private String eventId;
    private String orderId;
    private String ticketClassId;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     *
     * @return
     *     The team
     */
    public Object getTeam() {
        return team;
    }

    /**
     *
     * @param team
     *     The team
     */
    public void setTeam(Object team) {
        this.team = team;
    }

    /**
     *
     * @return
     *     The resourceUri
     */
    public String getResourceUri() {
        return resourceUri;
    }

    /**
     *
     * @param resourceUri
     *     The resource_uri
     */
    public void setResourceUri(String resourceUri) {
        this.resourceUri = resourceUri;
    }

    /**
     *
     * @return
     *     The id
     */
    public String getId() {
        return id;
    }

    /**
     *
     * @param id
     *     The id
     */
    public void setId(String id) {
        this.id = id;
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
     *     The quantity
     */
    public Integer getQuantity() {
        return quantity;
    }

    /**
     *
     * @param quantity
     *     The quantity
     */
    public void setQuantity(Integer quantity) {
        this.quantity = quantity;
    }

    /**
     *
     * @return
     *     The profile
     */
    public Profile getProfile() {
        return profile;
    }

    /**
     *
     * @param profile
     *     The profile
     */
    public void setProfile(Profile profile) {
        this.profile = profile;
    }

    /**
     *
     * @return
     *     The barcodes
     */
    public List<Barcode> getBarcodes() {
        return barcodes;
    }

    /**
     *
     * @param barcodes
     *     The barcodes
     */
    public void setBarcodes(List<Barcode> barcodes) {
        this.barcodes = barcodes;
    }

    /**
     *
     * @return
     *     The answers
     */
    public List<Answer> getAnswers() {
        return answers;
    }

    /**
     *
     * @param answers
     *     The answers
     */
    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    /**
     *
     * @return
     *     The costs
     */
    public Costs getCosts() {
        return costs;
    }

    /**
     *
     * @param costs
     *     The costs
     */
    public void setCosts(Costs costs) {
        this.costs = costs;
    }

    /**
     *
     * @return
     *     The checkedIn
     */
    public Boolean getCheckedIn() {
        return checkedIn;
    }

    /**
     *
     * @param checkedIn
     *     The checked_in
     */
    public void setCheckedIn(Boolean checkedIn) {
        this.checkedIn = checkedIn;
    }

    /**
     *
     * @return
     *     The cancelled
     */
    public Boolean getCancelled() {
        return cancelled;
    }

    /**
     *
     * @param cancelled
     *     The cancelled
     */
    public void setCancelled(Boolean cancelled) {
        this.cancelled = cancelled;
    }

    /**
     *
     * @return
     *     The refunded
     */
    public Boolean getRefunded() {
        return refunded;
    }

    /**
     *
     * @param refunded
     *     The refunded
     */
    public void setRefunded(Boolean refunded) {
        this.refunded = refunded;
    }

    /**
     *
     * @return
     *     The affiliate
     */
    public Object getAffiliate() {
        return affiliate;
    }

    /**
     *
     * @param affiliate
     *     The affiliate
     */
    public void setAffiliate(Object affiliate) {
        this.affiliate = affiliate;
    }

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
     *     The eventId
     */
    public String getEventId() {
        return eventId;
    }

    /**
     *
     * @param eventId
     *     The event_id
     */
    public void setEventId(String eventId) {
        this.eventId = eventId;
    }

    /**
     *
     * @return
     *     The orderId
     */
    public String getOrderId() {
        return orderId;
    }

    /**
     *
     * @param orderId
     *     The order_id
     */
    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    /**
     *
     * @return
     *     The ticketClassId
     */
    public String getTicketClassId() {
        return ticketClassId;
    }

    /**
     *
     * @param ticketClassId
     *     The ticket_class_id
     */
    public void setTicketClassId(String ticketClassId) {
        this.ticketClassId = ticketClassId;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
