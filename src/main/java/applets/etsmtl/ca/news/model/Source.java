package applets.etsmtl.ca.news.model;

import javax.xml.bind.annotation.XmlRootElement;

/**
 * Created by gnut3ll4 on 22/01/16.
 */
@XmlRootElement
public class Source {

    private String name;
    private String type;
    private String urlImage;
    private String key;

    private String value;

    public Source() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getValue() { return value; }

    public void setValue(String value) { this.value = value; }
}
