package projekt_zespolowy.restapi.model;

import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;
import org.postgresql.geometric.PGpoint;

@Entity
@Table(name="zgloszenia")
@XmlRootElement(name="zgloszenia")
@XmlType(propOrder={"id_zgloszenia", "id_typu", "id_statusu", "kalendarz", "wspolrzedne", "opis", "email_uzytkownika", "adres", "komentarze"})
public class Zgloszenia
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_zgloszenia")
    private int id_zgloszenia;
    @Column(name="id_typu")
    private int id_typu;
    @Column(name="id_statusu")
    private int id_statusu;
    @Column(name="kalendarz")
    private String kalendarz;
    @Column(name="wspolrzedne")
    private PGpoint wspolrzedne;
    @Column(name="opis")
    private String opis;
    @Column(name="email_uzytkownika")
    private String email_uzytkownika;
    @Column(name="adres")
    private String adres;
    @Column(name="komentarze")
    private List<Komentarze> komentarze;

    @XmlElement
    public int getId_zgloszenia() {
        return id_zgloszenia;
    }

    public void setId_zgloszenia(int id) {
        this.id_zgloszenia = id;
    }

    @XmlElement
    public int getId_typu() {
        return id_typu;
    }

    public void setId_typu(int typ) {
        this.id_typu = typ;
    }

    @XmlElement
    public int getId_statusu() {
        return id_statusu;
    }

    public void setId_statusu(int status) {
        this.id_statusu = status;
    }

    @XmlElement
    public String getKalendarz() {
        return kalendarz;
    }

    public void setKalendarz(String kalendarz) {
        this.kalendarz = kalendarz;
    }

    @XmlElement
    public PGpoint getWspolrzedne() {
        return wspolrzedne;
    }

    public void setWspolrzedne(PGpoint wspolrzedne) {
        this.wspolrzedne = wspolrzedne;
    }

    @XmlElement
    public String getOpis() {
        return opis;
    }

    public void setOpis(String opis) {
        this.opis = opis;
    }

    public void cutOpis(int length) {
        if (opis.length() < length) {
            return;
        }
        opis = opis.substring(0, length);

        while (opis.charAt(--length) != ' ') {
        }
        opis = opis.substring(0, length) + "...";
    }

    @XmlElement
    public String getEmail_uzytkownika() {
        return email_uzytkownika;
    }

    public void setEmail_uzytkownika(String email_uzytkownika) {
        this.email_uzytkownika = email_uzytkownika;
    }

    @XmlElement
    public String getAdres() {
        return adres;
    }

    public void setAdres(String adres) {
        this.adres = adres;
    }

    @XmlElement
    public List<Komentarze> getKomentarze() {
        return komentarze;
    }

    public void setKomentarze(List<Komentarze> komentarze) {
        this.komentarze = komentarze;
    }
}
