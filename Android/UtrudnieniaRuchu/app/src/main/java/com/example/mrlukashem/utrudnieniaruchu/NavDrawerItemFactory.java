package com.example.mrlukashem.utrudnieniaruchu;

/**
 * Created by mrlukashem on 23.03.15.
 */
public class NavDrawerItemFactory {
    private static int freeID = 0;

    public static enum TYPE {
        SECTION, TITLE
    }

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
}
