package com.example.mrlukashem.utrudnieniaruchu;

/**
 * Created by mrlukashem on 19.03.15.
 */
public class NavDrawerTitleItem implements NavDrawerItem {

    private String label;
    private int id;

    public static final int TYPE = 0;

    public NavDrawerTitleItem(String __label) {
        label = __label;
        id = 0;
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
