/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
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
/**
 *
 * @author guncda, Piotr, Kacper
 */
@Entity
@Table(name="zgloszenia")
@XmlRootElement(name="zgloszenia")
@XmlType(propOrder={"id_zgloszenia", "id_uzytkownika", "id_typu", "id_statusu", "kalendarz", "id_disqus", "wspolrzedne"})
public class Zgloszenia {
    
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_zgloszenia")
    private int id_zgloszenia;
    @Column(name="id_uzytkownika")
    private int id_uzytkownika;
    @Column(name="id_typu")
    private /*Object*/int id_typu;
    @Column(name="id_statusu")
    private int id_statusu;
    @Column(name="kalendarz")
    private /*Date*/String kalendarz; //<-- Ustawione na String nie na Date, pasowałoby to zmienić
    @Column(name="disqus_identifer")
    private int id_disqus;
    @Column(name="wspolrzedne")
    private PGpoint wspolrzedne;
    /*@Column(name="zdjecie")
    private */  //<-- trzeba jako zdjecie ustawic

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
    public int/*Object*/ getId_typu() {
        return id_typu;
    }

    public void setId_typu(int/*Object*/ typ) {
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
    public /*Date*/String getKalendarz() {
        return kalendarz;
    }

    public void setKalendarz(/*Date*/String kalendarz) {
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
    
    public int setZgloszeniaByJSON(String json) {
        int id_zgloszenia, id_uzytkownika, id_typu, id_statusu;
        String kalendarz;
        int id_disqus;
        double x, y;
        PGpoint wspolrzedne;
        int idx;
        
        if ((idx = json.indexOf("\"id_zgloszenia\":")) > -1) {
            id_zgloszenia = Integer.parseInt(json.substring(idx += "\"id_zgloszenia\":".length(), json.indexOf(",", idx)));
        } else {
            return 0;
        }
        
        if ((idx = json.indexOf("\"id_uzytkownika\":", idx)) > -1) {
            id_uzytkownika = Integer.parseInt(json.substring(idx += "\"id_uzytkownika\":".length(), json.indexOf(",", idx)));
        } else {
            return 0;
        }
        
        if ((idx = json.indexOf("\"id_typu\":", idx)) > -1) {
            id_typu = Integer.parseInt(json.substring(idx += "\"id_typu\":".length(), json.indexOf(",", idx)));
        } else {
            return 0;
        }
        
        if ((idx = json.indexOf("\"id_statusu\":", idx)) > -1) {
            id_statusu = Integer.parseInt(json.substring(idx += "\"id_statusu\":".length(), json.indexOf(",", idx)));
        } else {
            return 0;
        }
        
        if ((idx = json.indexOf("\"kalendarz\":\"", idx)) > -1) {
            kalendarz = json.substring(idx += "\"kalendarz\":\"".length(), json.indexOf("\"", idx));
        } else {
            return 0;
        }
        
        if ((idx = json.indexOf("\"id_disqus\":", idx)) > -1) {
            id_disqus = Integer.parseInt(json.substring(idx += "\"id_disqus\":".length(), json.indexOf(",", idx)));
        } else {
            return 0;
        }
        
        if ((idx = json.indexOf("\"wspolrzedne\"", idx)) > -1) {
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
        } else {
            return 0;
        }
        wspolrzedne = new PGpoint(x, y);

        System.out.println("id_zgloszenia: " + id_zgloszenia
        + "\nid_uzytkownika" + id_uzytkownika
        + "\nid_typu" + id_typu
        + "\nid_statusu" + id_statusu
        + "\nkalendarz" + kalendarz
        + "\nid_disqus" + id_disqus
        + "\nwspolrzedne" + wspolrzedne);
        
        setId_zgloszenia(id_zgloszenia);
        setId_uzytkownika(id_uzytkownika);
        setId_typu(id_typu);
        setId_statusu(id_statusu);
        setKalendarz(kalendarz);
        setId_disqus(id_disqus);
        setWspolrzedne(wspolrzedne);
        return 1;
    }
    
}
