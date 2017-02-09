package applets.etsmtl.ca.cooptel.model;


import javax.xml.bind.annotation.XmlRootElement;
import java.io.Serializable;

@XmlRootElement
public class ConsommationDate implements Serializable {

    String idChambre;
    String date;
    float upload;
    float download;

    public ConsommationDate() {
    }

    public ConsommationDate(String idChambre, String date, float upload, float download) {
        this.idChambre = idChambre;
        this.date = date;
        this.upload = upload;
        this.download = download;
    }

    public String getIdChambre() {
        return idChambre;
    }

    public void setIdChambre(String idChambre) {
        this.idChambre = idChambre;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public float getUpload() {
        return upload;
    }

    public void setUpload(float upload) {
        this.upload = upload;
    }

    public float getDownload() {
        return download;
    }

    public void setDownload(float download) {
        this.download = download;
    }
}