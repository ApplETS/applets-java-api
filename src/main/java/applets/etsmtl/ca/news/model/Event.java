package applets.etsmtl.ca.news.model;

import java.util.Date;

/**
 * Created by Marcantvez on 09/03/16.
 */
public class Event implements Comparable{
    private String id;
    private String nom;
    private Date debut; // TODO test for better class
    private Date fin; // TODO test for better class
    private String nom_lieu;
    private String ville;
    private String etat;
    private String pays;
    private String adresse;
    private String code_postal;
    private float longitude;
    private float latitude;
    private String description;
    private String image;
    private String id_source;

    public Event() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getNom() {
        return nom;
    }

    public void setNom(String nom) {
        this.nom = nom;
    }

    public Date getDebut() {
        return debut;
    }

    public void setDebut(Date debut) {
        this.debut = debut;
    }

    public Date getFin() {
        return fin;
    }

    public void setFin(Date fin) {
        this.fin = fin;
    }

    public String getNom_lieu() {
        return nom_lieu;
    }

    public void setNom_lieu(String nom_lieu) {
        this.nom_lieu = nom_lieu;
    }

    public String getVille() {
        return ville;
    }

    public void setVille(String ville) {
        this.ville = ville;
    }

    public String getEtat() {
        return etat;
    }

    public void setEtat(String etat) {
        this.etat = etat;
    }

    public String getPays() {
        return pays;
    }

    public void setPays(String pays) {
        this.pays = pays;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }

    public String getCode_postal() {
        return code_postal;
    }

    public void setCode_postal(String code_postal) {
        this.code_postal = code_postal;
    }

    public float getLongitude() {
        return longitude;
    }

    public void setLongitude(float longitude) {
        this.longitude = longitude;
    }

    public float getLatitude() {
        return latitude;
    }

    public void setLatitude(float latitude) {
        this.latitude = latitude;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getId_source() {
        return id_source;
    }

    public void setId_source(String id_source) {
        this.id_source = id_source;
    }

    @Override
    public int compareTo(Object o) {
        return this.debut.compareTo(((Event)o).getDebut());
    }
}
