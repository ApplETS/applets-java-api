package applets.etsmtl.ca.amc.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@XmlRootElement
public class TirageSort {

    private int id;
    private String titre;
    private String description;
    private String image;
    private long dateDebut;
    private long dateFin;
    private ArrayList<TirageInscrit> inscrits;
    private ArrayList<TiragePrix> prix;

    public TirageSort() {
        inscrits = new ArrayList<TirageInscrit>();
        prix = new ArrayList<TiragePrix>();
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitre() {
        return titre;
    }

    public void setTitre(String titre) {
        this.titre = titre;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public long getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(long dateDebut) {
        this.dateDebut = dateDebut;
    }

    public long getDateFin() {
        return dateFin;
    }

    public void setDateFin(long dateFin) {
        this.dateFin = dateFin;
    }

    public ArrayList<TirageInscrit> getInscrits() {
        return inscrits;
    }

    public void setInscrits(ArrayList<TirageInscrit> inscrits) {
        this.inscrits = inscrits;
    }

    public ArrayList<TiragePrix> getPrix() {
        return prix;
    }

    public void setPrix(ArrayList<TiragePrix> prix) {
        this.prix = prix;
    }
}
