package projekt_zespolowy.restapi.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

@Entity
@Table(name="uzytkownicy")
@XmlRootElement(name = "uzytkownicy")
@XmlType(propOrder={"id_uzytkownika", "nick", "email", "haslo", "admin"})
public class Uzytkownicy
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_uzytkownika")
    private int id_uzytkownika;
    @Column(name="nick")
    private String nick;
    @Column(name="email")
    private String email;
    @Column(name="haslo")
    private String haslo;
    @Column(name="admin")
    private boolean admin;

    @XmlElement
    public int getId_uzytkownika() {
        return id_uzytkownika;
    }

    public void setId_uzytkownika(int id_uzytkownika) {
        this.id_uzytkownika = id_uzytkownika;
    }

    public int setId_uzytkownikaByJSON(String json, int idx) {
        if ((idx = json.indexOf("\"id_uzytkownika\":", idx)) > -1) {
            id_uzytkownika = Integer.parseInt(json.substring(idx += "\"id_uzytkownika\":".length(), json.indexOf(",", idx)));
        }

        return idx;
    }

    @XmlElement
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

    public int setNickByJSON(String json, int idx) {
        if ((idx = json.indexOf("\"nick\":\"", idx)) > -1) {
            nick = json.substring(idx += "\"nick\":\"".length(), json.indexOf("\"", idx));
        }

        return idx;
    }

    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public int setEmailByJSON(String json, int idx) {
        if ((idx = json.indexOf("\"email\":\"", idx)) > -1) {
            email = json.substring(idx += "\"email\":\"".length(), json.indexOf("\"", idx));
        }

        return idx;
    }

    @XmlElement
    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    public int setHasloByJSON(String json, int idx) {
        if ((idx = json.indexOf("\"haslo\":\"", idx)) > -1) {
            haslo = json.substring(idx += "\"haslo\":\"".length(), json.indexOf("\"", idx));
        }

        return idx;
    }

    @XmlElement
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}