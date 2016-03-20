
package applets.etsmtl.ca.amc.model;
import java.util.HashMap;
import java.util.Map;
import javax.annotation.Generated;

@Generated("org.jsonschema2pojo")
public class Costs {

    private PaymentFee paymentFee;
    private Gross gross;
    private EventbriteFee eventbriteFee;
    private Tax tax;
    private BasePrice basePrice;
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * 
     * @return
     *     The paymentFee
     */
    public PaymentFee getPaymentFee() {
        return paymentFee;
    }

    /**
     * 
     * @param paymentFee
     *     The payment_fee
     */
    public void setPaymentFee(PaymentFee paymentFee) {
        this.paymentFee = paymentFee;
    }

    /**
     * 
     * @return
     *     The gross
     */
    public Gross getGross() {
        return gross;
    }

    /**
     * 
     * @param gross
     *     The gross
     */
    public void setGross(Gross gross) {
        this.gross = gross;
    }

    /**
     * 
     * @return
     *     The eventbriteFee
     */
    public EventbriteFee getEventbriteFee() {
        return eventbriteFee;
    }

    /**
     * 
     * @param eventbriteFee
     *     The eventbrite_fee
     */
    public void setEventbriteFee(EventbriteFee eventbriteFee) {
        this.eventbriteFee = eventbriteFee;
    }

    /**
     * 
     * @return
     *     The tax
     */
    public Tax getTax() {
        return tax;
    }

    /**
     * 
     * @param tax
     *     The tax
     */
    public void setTax(Tax tax) {
        this.tax = tax;
    }

    /**
     * 
     * @return
     *     The basePrice
     */
    public BasePrice getBasePrice() {
        return basePrice;
    }

    /**
     * 
     * @param basePrice
     *     The base_price
     */
    public void setBasePrice(BasePrice basePrice) {
        this.basePrice = basePrice;
    }

    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
