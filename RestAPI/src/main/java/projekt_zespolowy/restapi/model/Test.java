/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package projekt_zespolowy.restapi.model;

import java.util.Scanner;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlType;

/**
 *
 * @author guncda
 */
@Entity
@Table(name="test")
@XmlRootElement(name = "test")
@XmlType(propOrder={"id_osoby", "imie", "nazwisko"})
public class Test {
    @Id
    @GeneratedValue(strategy=GenerationType.IDENTITY)
    @Column(name="id_osoby")
    private int id_osoby;
    @Column(name="imie")
    private String imie;
    @Column(name="nazwisko")
    private String nazwisko;
    
    @XmlElement
    public int getId_osoby() {
        return id_osoby;
    }

    public void setId_osoby(int id_osoby) {
        this.id_osoby = id_osoby;
    }

    @XmlElement
    public String getImie() {
        return imie;
    }

    public void setImie(String imie) {
        this.imie = imie;
    }

    @XmlElement
    public String getNazwisko() {
        return nazwisko;
    }

    public void setNazwisko(String nazwisko) {
        this.nazwisko = nazwisko;
    }
    
    /*@return 0 - nie powiodlo się odczytywanie; 1 - odczytywanie sie powiodlo*/
    public int buildByJSON(String json) {
        int id_osoby;
        String imie, nazwisko;
        int idx, length;
        
        if ((idx = json.indexOf("\"id_osoby\":")) > -1) {
            id_osoby = Integer.parseInt(json.substring(idx += "\"id_osoby\":".length(), json.indexOf(",", idx)));
        } else return 0;
        
        if ((idx = json.indexOf("\"imie\":\"", idx)) > -1) {
            imie = json.substring(idx += "\"imie\":\"".length(), json.indexOf("\"", idx));
        } else return 0;
        
        if ((idx = json.indexOf("\"nazwisko\":\"", idx)) > -1) {
            nazwisko = json.substring(idx += "\"nazwisko\":\"".length(), json.indexOf("\"", idx));
        } else return 0;
        
        setId_osoby(id_osoby);
        setImie(imie);
        setNazwisko(nazwisko);
        return 1;
    }
    
}