package applets.etsmtl.ca.cooptel.model;

import org.codehaus.jackson.map.annotate.JsonCachable;

import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

/**
 * Created by gnut3ll4 on 08/03/16.
 */

@XmlRootElement
@JsonCachable
public class ConsommationGlobal implements Serializable {
    float restant;

    List<ConsommationDate> consommations;

    public ConsommationGlobal() {
    }

    public ConsommationGlobal(float restant, List<ConsommationDate> consommations) {
        this.restant = restant;
        this.consommations = consommations;
    }

    public float getRestant() {
        return restant;
    }

    public void setRestant(float restant) {
        this.restant = restant;
    }

    public List<ConsommationDate> getConsommations() {
        return consommations;
    }

    public void setConsommations(List<ConsommationDate> consommations) {
        this.consommations = consommations;
    }
}