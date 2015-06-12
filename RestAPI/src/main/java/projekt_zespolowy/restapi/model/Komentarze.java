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
@Table(name="komentarze")
@XmlRootElement(name="komentarze")
@XmlType(propOrder={"id_zgloszenia", "id_komentarza", "login", "komentarz", "data_nadania"})
public class Komentarze {

    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_zgloszenia")
    private int id_zgloszenia;
    @Column(name="id_komentarza")
    private int id_komentarza;
    @Column(name="login")
    private String login;
    @Column(name="komentarz")
    private String komentarz;
    @Column(name="data_nadania")
    private String data_nadania;

    @XmlElement
    public int getId_zgloszenia() {
        return id_zgloszenia;
    }

    public void setId_zgloszenia(int id_zgloszenia) {
        this.id_zgloszenia = id_zgloszenia;
    }

    @XmlElement
    public int getId_komentarza() {
        return id_komentarza;
    }

    public void setId_komentarza(int id_komentarza) {
        this.id_komentarza = id_komentarza;
    }

    @XmlElement
    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    @XmlElement
    public String getData_nadania() {
        return data_nadania;
    }

    public void setData_nadania(String data_nadania) {
        this.data_nadania = data_nadania;
    }

    @XmlElement
    public String getKomentarz() {
        return komentarz;
    }

    public void setKomentarz(String komentarz) {
        this.komentarz = komentarz;
    }
}
