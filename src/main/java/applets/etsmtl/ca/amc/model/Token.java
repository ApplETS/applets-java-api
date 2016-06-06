package applets.etsmtl.ca.amc.model;

import javax.xml.bind.annotation.XmlRootElement;
import java.util.Date;

/**
 * Created by valentin-debris on 2016-02-10.
 */
@XmlRootElement
public class Token {

    private int id;
    private String value;
    private long lastUse;

    public Token() { }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public long getLastUse() {
        return lastUse;
    }

    public void setLastUse(long lastUse) {
        this.lastUse = lastUse;
    }
}
