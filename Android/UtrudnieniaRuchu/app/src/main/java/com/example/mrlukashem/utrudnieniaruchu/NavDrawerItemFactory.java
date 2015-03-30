package com.example.mrlukashem.utrudnieniaruchu;

/**
 * Created by mrlukashem on 23.03.15.
 */
public class NavDrawerItemFactory {
    //pole wolnych id dla kolejnych instancji navdraweritem. Dzieki temu zachowa sie
    //kolejnosc konkretnych elementow listy. Jezeli nie bedzie mozliwosc odwolania sie za pomcoca adaptera
    private static int freeID = 0;

    //sprecyzowane rodzaje elementow drawer listy
    public static enum TYPE {
        SECTION, TITLE
    }

    /*
     Metoda umozliwia konstrukcje obiektow implementujacych insterface NavDrawerItem bez koniecznosci
     wnikania w szczegoly owej konstrukcji
     @param1 etykieta elementu listy
     @param2 typ elementu
     @param3 id obrazka
     @return interfajs do konkretnego rodzaju elementu listy
     */
    public static NavDrawerItem newInstance(String __label, TYPE __type, int __icon) {
        switch(__type) {
            case SECTION:
                NavDrawerSectionItem _item_s = new NavDrawerSectionItem(__label, __icon);
                _item_s.setId(freeID);
                freeID++;
                return _item_s;
            case TITLE:
                NavDrawerTitleItem _item_t = new NavDrawerTitleItem(__label, __icon);
                _item_t.setId(freeID);
                freeID++;
                return _item_t;
        }

        return null;
    }

    /*
     Metoda umozliwia konstrukcje obiektow implementujacych insterface NavDrawerItem bez koniecznosci
     wnikania w szczegoly owej konstrukcji
     @param1 etykieta elementu listy
     @param2 typ elementu
     @return interfajs do konkretnego rodzaju elementu listy
     */
    public static NavDrawerItem newInstance(String __label, TYPE __type) {
        switch(__type) {
            case SECTION:
                NavDrawerSectionItem _item_s = new NavDrawerSectionItem(__label);
                _item_s.setId(freeID);
                freeID++;
                return _item_s;
            case TITLE:
                NavDrawerTitleItem _item_t = new NavDrawerTitleItem(__label);
                _item_t.setId(freeID);
                freeID++;
                return _item_t;
        }

        return null;
    }

    /*
      Skonkretyzowana metoda tworzaca obiekt implementujacy interfejs bez koniecznosci
      podawania w parametrze jego rodzaju
      @param1 etykieta elementu listy
      @param2 id obrazka
      @return interfejs do konkretnego rodzaju elementu listy
     */
    public static NavDrawerItem newInstanceOfSection(String __label, int __icon) {
        NavDrawerSectionItem _item = new NavDrawerSectionItem(__label, __icon);
        _item.setId(freeID);
        freeID++;
        return _item;
    }

    /*
     Skonkretyzowana metoda tworzaca obiekt implementujacy interfejs bez koniecznosci
     podawania w parametrze jego rodzaju
     @param1 etykieta elementu listy
     @param2 id obrazka
     @return interfejs do konkretnego rodzaju elementu listy
    */
    public static NavDrawerItem newInstanceOfTitle(String __label, int __icon) {
        NavDrawerTitleItem _item = new NavDrawerTitleItem(__label, __icon);
        _item.setId(freeID);
        freeID++;
        return _item;
    }
}
