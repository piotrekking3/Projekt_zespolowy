package com.example.mrlukashem.utrudnieniaruchu;

import android.content.Context;
import android.widget.TextView;

import org.w3c.dom.Text;

/**
 * Created by mrlukashem on 19.03.15.
 */
public class NavDrawerSectionItem implements NavDrawerItem {

    private int id;
    private int icon;
    private String label;

    public static final int TYPE = 1;

    public NavDrawerSectionItem(String __label, int __icon_id) {
        label = __label;
        icon = __icon_id;
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

    public int getIconId() {
        return icon;
    }

    public void setIconId(int __id) {
        icon = __id;
    }

    @Override
    public int getType() {
        return TYPE;
    }

}
