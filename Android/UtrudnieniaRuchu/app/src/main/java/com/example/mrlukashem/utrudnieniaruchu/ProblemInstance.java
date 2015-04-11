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

    /*
        Metoda tworząca nowy obiekt klasy z zadanymi parametrami
        @param1 budowniczy klasy służący do uproszczenia konstrukcji obiektu poprzez tę metodę
        @param2 marker usytuowany na mapie
        @return gotowy obiekt
     */
    static public ProblemInstance newInstance(ProblemData __builder, Marker __marker) {
        ProblemInstance _problem =  new ProblemInstance();
        _problem.marker = Objects.requireNonNull(__marker);
        _problem.content = __builder.content;
        _problem.email = __builder.email;
        _problem.imageView = __builder.imageView;
        _problem.categoryId = __builder.categoryId;
        _problem.category = __marker.getTitle();

        return _problem;
    }

    /*
        Tworzy nowy obiekt budowniczego
        @return obiekt budowniczego
     */
    static public ProblemData createProblemData(String __content,
                                                String __email,
                                                ImageView __image_view,
                                                Integer __category_id,
                                                LatLng __cords) {
        return new ProblemData(__content, __email, __image_view, __category_id, __cords);
    }

    static public ProblemData createProblemData(String __content,
                                                String __email,
                                                Integer __category_id,
                                                LatLng __cords) {
        return new ProblemData(__content, __email, __category_id, __cords);
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

    /*
        Statyczna klasa służąca jako budowniczy dla klasy ProblemInstance. Dzięki takiemu
        zabiegowi jesteśmy w stanie zwiększyć przejrzystość konstrukcji obiektu.
     */
    public static class ProblemData {
        private String content;
        private String email;
        private ImageView imageView;
        private Integer categoryId;
        private LatLng cords;

        public ProblemData(
                String __content,
                String __email,
                ImageView __image_view,
                Integer __category_id,
                LatLng __cords) {
            content = Objects.requireNonNull(__content);
            email = Objects.requireNonNull(__email);
            imageView = Objects.requireNonNull(__image_view);
            categoryId = Objects.requireNonNull(__category_id);
            cords = Objects.requireNonNull(__cords);
        };

        public ProblemData(
                String __content,
                String __email,
                Integer __category_id,
                LatLng __cords) {
            content = Objects.requireNonNull(__content);
            email = Objects.requireNonNull(__email);
            imageView = null;
            categoryId = Objects.requireNonNull(__category_id);
            cords = Objects.requireNonNull(__cords);
        };

        public String getContent() {
            return content;
        }

        public String getEmail() {
            return email;
        }

        public LatLng getCords() {
            return cords;
        }

        public Integer getCategoryId() {
            return categoryId;
        }

        public ImageView getImageView() {
            return imageView;
        }
    }
}
