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
@XmlType(propOrder={"email", "haslo", "facebook", "google", "typ", "token", "uprawnienia", "czy_aktywowany", "data_rejestracji"})
public class Uzytkownicy
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="email")
    private String email;
    @Column(name="haslo")
    private String haslo;
    @Column(name="facebook")
    private String facebook;
    @Column(name="google")
    private String google;
    @Column(name="typ")
    private String typ;
    @Column(name="token")
    private String token;
    @Column(name="uprawnienia")
    private String uprawnienia;
    @Column(name="czy_aktywowany")
    private boolean czy_aktywowany;
    @Column(name="data_rejestracji")
    private String data_rejestracji;

    @XmlElement
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @XmlElement
    public String getHaslo() {
        return haslo;
    }

    public void setHaslo(String haslo) {
        this.haslo = haslo;
    }

    @XmlElement
    public String getFacebook() {
        return facebook;
    }

    public void setFacebook(String facebook) {
        this.facebook = facebook;
    }

    @XmlElement
    public String getGoogle() {
        return google;
    }

    public void setGoogle(String google) {
        this.google = google;
    }

    @XmlElement
    public String getTyp() {
        return typ;
    }

    public void setTyp(String typ) {
        this.typ = typ;
    }

    @XmlElement
    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    @XmlElement
    public String getUprawnienia() {
        return uprawnienia;
    }

    public void setUprawnienia(String uprawnienia) {
        this.uprawnienia = uprawnienia;
    }

    @XmlElement
    public boolean isCzy_aktywowany() {
        return czy_aktywowany;
    }

    public void setCzy_aktywowany(boolean czy_aktywowany) {
        this.czy_aktywowany = czy_aktywowany;
    }

    @XmlElement
    public String getData_rejestracji() {
        return data_rejestracji;
    }

    public void setData_rejestracji(String data_rejestracji) {
        this.data_rejestracji = data_rejestracji;
    }
}
