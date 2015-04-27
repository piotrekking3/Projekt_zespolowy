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
import org.postgresql.geometric.PGpoint;

@Entity
@Table(name="zgloszenia")
@XmlRootElement(name="zgloszenia")
@XmlType(propOrder={"id_zgloszenia", "id_uzytkownika", "id_typu", "id_statusu", "kalendarz", "id_disqus", "wspolrzedne"})
public class Zgloszenia
{
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_zgloszenia")
    private int id_zgloszenia;
    @Column(name="id_uzytkownika")
    private int id_uzytkownika;
    @Column(name="id_typu")
    private int id_typu;
    @Column(name="id_statusu")
    private int id_statusu;
    @Column(name="kalendarz")
    private String kalendarz;
    @Column(name="disqus_identifer")
    private int id_disqus;
    @Column(name="wspolrzedne")
    private PGpoint wspolrzedne;

    @XmlElement
    public int getId_zgloszenia() {
        return id_zgloszenia;
    }

    public void setId_zgloszenia(int id) {
        this.id_zgloszenia = id;
    }

    @XmlElement
    public int getId_uzytkownika() {
        return id_uzytkownika;
    }

    public void setId_uzytkownika(int id_uzytkownika) {
        this.id_uzytkownika = id_uzytkownika;
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
    public int getId_disqus() {
        return id_disqus;
    }

    public void setId_disqus(int id_disqus) {
        this.id_disqus = id_disqus;
    }

    @XmlElement
    public PGpoint getWspolrzedne() {
        return wspolrzedne;
    }

    public void setWspolrzedne(PGpoint wspolrzedne) {
        this.wspolrzedne = wspolrzedne;
    }
}
