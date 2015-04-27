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

    @XmlElement
    public String getNick() {
        return nick;
    }

    public void setNick(String nick) {
        this.nick = nick;
    }

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
    public boolean isAdmin() {
        return admin;
    }

    public void setAdmin(boolean admin) {
        this.admin = admin;
    }
}
