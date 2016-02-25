package applets.etsmtl.ca.amc.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@XmlRootElement
public class Evenement {

    private int id;
    private String nom;
    private String presentation;
    private String hashtag;
    private Date dateDebut;
    private Date dateFin;
    private String lienEventbrite;
    private ArrayList<Equipe> equipes;
    private ArrayList<Partenaire> partenaires;
    private ArrayList<Intervenant> intervenants;
    private ArrayList<TirageSort> tirageSorts;
    private ArrayList<TirageInscrit> tirageInscrits;

    public Evenement() {
        this.tirageSorts = new ArrayList<TirageSort>();
        this.intervenants = new ArrayList<Intervenant>();
        this.partenaires = new ArrayList<Partenaire>();
        this.equipes = new ArrayList<Equipe>();
        this.tirageInscrits = new ArrayList<TirageInscrit>();
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public String getPresentation() {
        return presentation;
    }

    public void setPresentation(String presentation) {
        this.presentation = presentation;
    }

    public String getHashtag() {
        return hashtag;
    }

    public void setHashtag(String hashtag) {
        this.hashtag = hashtag;
    }

    public Date getDateDebut() {
        return dateDebut;
    }

    public void setDateDebut(Date dateDeb) {
        this.dateDebut = dateDeb;//dateDeb;
    }

    public Date getDateFin() {
        return dateFin;
    }

    public void setDateFin(Date dateFin) {
        this.dateFin = dateFin;
    }

    public String getLienEventbrite() {
        return lienEventbrite;
    }

    public void setLienEventbrite(String urlEventbrite) {
        this.lienEventbrite = urlEventbrite;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public ArrayList<Equipe> getEquipes() {
        return equipes;
    }

    public void setEquipes(ArrayList<Equipe> equipes) {
        this.equipes = equipes;
    }

    public ArrayList<Partenaire> getPartenaires() {
        return partenaires;
    }

    public void setPartenaires(ArrayList<Partenaire> partenaires) {
        this.partenaires = partenaires;
    }

    public ArrayList<Intervenant> getIntervenants() {
        return intervenants;
    }

    public void setIntervenants(ArrayList<Intervenant> intervenants) {
        this.intervenants = intervenants;
    }

    public ArrayList<TirageSort> getTirageSorts() {
        return tirageSorts;
    }

    public void setTirageSorts(ArrayList<TirageSort> tirageSorts) {
        this.tirageSorts = tirageSorts;
    }

    public ArrayList<TirageInscrit> getTirageInscrits() {
        return tirageInscrits;
    }

    public void setTirageInscrits(ArrayList<TirageInscrit> tirageInscrits) {
        this.tirageInscrits = tirageInscrits;
    }
}
