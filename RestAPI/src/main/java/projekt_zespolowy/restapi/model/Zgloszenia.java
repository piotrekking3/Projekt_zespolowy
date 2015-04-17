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

    public int setId_zgloszenia(String json, int idx) {
        if ((idx = json.indexOf("\"id_zgloszenia\":", idx)) > -1) {
            id_zgloszenia = Integer.parseInt(json.substring(idx += "\"id_zgloszenia\":".length(), json.indexOf(",", idx)));
        }

        return idx;
    }

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
    public int getId_typu() {
        return id_typu;
    }

    public void setId_typu(int typ) {
        this.id_typu = typ;
    }

    public int setId_typuByJSON(String json, int idx) {
        if ((idx = json.indexOf("\"id_typu\":", idx)) > -1) {
            id_typu = Integer.parseInt(json.substring(idx += "\"id_typu\":".length(), json.indexOf(",", idx)));
        }

        return idx;
    }

    @XmlElement
    public int getId_statusu() {
        return id_statusu;
    }

    public void setId_statusu(int status) {
        this.id_statusu = status;
    }

    public int setId_statusuByJSON(String json, int idx) {
        if ((idx = json.indexOf("\"id_statusu\":", idx)) > -1) {
            id_statusu = Integer.parseInt(json.substring(idx += "\"id_statusu\":".length(), json.indexOf(",", idx)));
        }

        return idx;
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

    public int setId_disqusByJSON(String json, int idx) {
        if ((idx = json.indexOf("\"id_disqus\":", idx)) > -1) {
            id_disqus = Integer.parseInt(json.substring(idx += "\"id_disqus\":".length(), json.indexOf(",", idx)));
        }

        return idx;
    }

    @XmlElement
    public PGpoint getWspolrzedne() {
        return wspolrzedne;
    }

    public void setWspolrzedne(PGpoint wspolrzedne) {
        this.wspolrzedne = wspolrzedne;
    }

    public int setWspolrzedneByJSON(String json, int idx) {
        double x, y;

        if ((idx = json.indexOf("\"x\":", idx)) > -1) {
            x = Double.parseDouble(json.substring(idx += "\"x\":".length(), json.indexOf(",", idx)));
        } else {
            return 0;
        }
        if ((idx = json.indexOf("\"y\":", idx)) > -1) {
            y = Double.parseDouble(json.substring(idx += "\"y\":".length(), json.indexOf("}", idx)));
        } else {
            return 0;
        }
        wspolrzedne = new PGpoint(x, y);

        return idx;
    }
}