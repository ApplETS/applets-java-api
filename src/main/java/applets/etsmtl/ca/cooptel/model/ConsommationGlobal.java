package applets.etsmtl.ca.cooptel.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;
import java.util.List;

@XmlRootElement
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