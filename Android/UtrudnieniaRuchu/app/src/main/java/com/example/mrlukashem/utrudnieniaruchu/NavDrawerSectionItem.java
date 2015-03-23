package com.example.mrlukashem.utrudnieniaruchu;

/**
 * Created by mrlukashem on 19.03.15.
 */
public class NavDrawerSectionItem implements NavDrawerItem {

    private int id;
    private String label;

    public static final int TYPE = 1;

    public NavDrawerSectionItem(String __label) {
        label = __label;
    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getLabel() {
        return new String(label);
    }

    public void setLabel(String __label) {
        label = __label;
    }

    public void setId(int __id) {
        id = __id;
    }

    @Override
    public int getType() {
        return TYPE;
    }
}
