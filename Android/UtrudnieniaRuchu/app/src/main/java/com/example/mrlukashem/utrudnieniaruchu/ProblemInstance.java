package com.example.mrlukashem.utrudnieniaruchu;

import android.widget.ImageView;

import com.google.android.gms.maps.model.LatLng;
import com.google.android.gms.maps.model.Marker;
import com.google.android.gms.maps.model.MarkerOptions;

import java.util.Objects;

/**
 * Created by mrlukashem on 07.04.15.
 */

/*
    Klasa służy do przechowywania informacji związanych z konkretnym utrudnieniem
 */
public class ProblemInstance {
    private ProblemInstance() { };

    //marker
    private Marker marker;
    //opis
    private String content;
    //email
    private String email;
    //kategoria odzwierciedlona w nazwie
    private String category;
    //przykładowe zdjęcie problemu
    private ImageView imageView;
    //id danej kategorii
    private Integer categoryId;
    //id problemu
    private Integer id = -1;

    private String address;

    private String date;

    private LatLng cords;

    /*
        Metoda tworząca nowy obiekt klasy z zadanymi parametrami
        @param1 budowniczy klasy służący do uproszczenia konstrukcji obiektu poprzez tę metodę
        @param2 marker usytuowany na mapie
        @return gotowy obiekt
     */
    static public ProblemInstance newInstance(ProblemData __builder, Marker __marker, Integer __id) {
        ProblemInstance _problem =  new ProblemInstance();
        if(__marker == null) {
            throw new NullPointerException();
        }

        _problem.marker = __marker;
        _problem.content = __builder.content;
        _problem.email = __builder.email;
        _problem.date = __builder.date;
        _problem.imageView = __builder.imageView;
        _problem.categoryId = __builder.categoryId;
        _problem.category = __marker.getTitle();
        _problem.cords = __marker.getPosition();
        _problem.address = __builder.address;
        _problem.id = __id;

        return _problem;
    }

    static public ProblemInstance newInstance(ProblemData __builder, Marker __marker) {
        ProblemInstance _problem =  new ProblemInstance();
        if(__marker == null) {
            throw new NullPointerException();
        }

        _problem.marker = __marker;
        _problem.content = __builder.content;
        _problem.email = __builder.email;
        _problem.date = __builder.date;
        _problem.id = __builder.id;
        _problem.imageView = __builder.imageView;
        _problem.categoryId = __builder.categoryId;
        _problem.category = __marker.getTitle();
        _problem.cords = __marker.getPosition();
        _problem.address = __builder.address;

        return _problem;
    }

    /*
        Tworzy nowy obiekt budowniczego
        @return obiekt budowniczego
     */
    static public ProblemData createProblemData(String __content,
                                                String __email,
                                                String __date,
                                                String __address,
                                                Integer __category_id,
                                                Integer __id,
                                                LatLng __cords) {
        return new ProblemData(__content, __email, __date, __address, __category_id, __id, __cords);
    }

    static public ProblemData createProblemData(String __content,
                                                String __email,
                                                String __date,
                                                Integer __category_id,
                                                LatLng __cords) {
        return new ProblemData(__content, __email, __date, __category_id, __cords);
    }

    public Marker getMarker() {
        return marker;
    }

    public String getContent() {
        return content;
    }

    public String getEmail() {
        return email;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public String getCategoryString() {
        return category;
    }

    public Integer getId() {
        return id;
    }

    public LatLng getCords() {
        return cords;
    }

    public String getDate() {
        return date;
    }

    public void setId(Integer __id) {
        id = __id;
    }

    public void setAddress(String __address) {
        address = __address;
    }

    public String getAddress() {
        return address;
    }

    public ProblemData getProblemData() {
        return new ProblemData(content, email, date, categoryId, marker.getPosition());
    }

    /*
        Statyczna klasa służąca jako budowniczy dla klasy ProblemInstance. Dzięki takiemu
        zabiegowi jesteśmy w stanie zwiększyć przejrzystość konstrukcji obiektu.
     */
    public static class ProblemData {
        private String content;
        private String email;
        private String date;
        private String address;
        private ImageView imageView;
        private Integer categoryId;
        private Integer id;
        private LatLng cords;

        public ProblemData(
                String __content,
                String __email,
                String __date,
                String __address,
                Integer __category_id,
                Integer __id,
                LatLng __cords) {
            if(__content == null || __email == null || __address == null
                    || __id == null || __cords == null) {
                throw new NullPointerException();
            }

            content = __content;
            email = __email;
            imageView = null;
            categoryId = __category_id;
            cords = __cords;
            address = __address;
            date = __date;
            id = __id;
        };

        public ProblemData(
                String __content,
                String __email,
                String __date,
                Integer __category_id,
                LatLng __cords) {
            if(__content == null || __email == null
                    || __cords == null) {
                throw new NullPointerException();
            }

            content = __content;
            email = __email;
            imageView = null;
            categoryId = __category_id;
            cords = __cords;
            date = __date;
            address = "";
        }

        public String getContent() {
            return content;
        }

        public String getEmail() {
            return email;
        }

        public LatLng getCords() {
            return cords;
        }

        public String getDate() {
            return date;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}
