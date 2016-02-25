package applets.etsmtl.ca.amc.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by valentin-debris on 2016-02-20.
 */
@XmlRootElement
public class TiragePrix {

    private int id;
    private TirageSort tirage;
    private Participant gagnant; //Correspond au gagnant
    private String prix; //Nom, description du prix
    private String image;

    public TiragePrix() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public TirageSort getTirage() {
        return tirage;
    }

    public void setTirage(TirageSort tirage) {
        this.tirage = tirage;
    }

    public Participant getGagnant() {
        return gagnant;
    }

    public void setGagnant(Participant gagnant) {
        this.gagnant = gagnant;
    }

    public String getPrix() {
        return prix;
    }

    public void setPrix(String prix) {
        this.prix = prix;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
