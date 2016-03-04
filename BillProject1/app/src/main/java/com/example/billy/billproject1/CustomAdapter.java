package com.example.billy.billproject1;

import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import java.util.ArrayList;

/**
 * Created by Billy on 3/3/16.
 */

// not use atm...

public class CustomAdapter extends BaseAdapter{

    private String[] strings;
    private ArrayList<String> selectedStrings;

    public CustomAdapter(String[] strings) {
        this.strings = strings;
        selectedStrings = new ArrayList<>();
    }

    @Override
    public int getCount() {
        return strings.length;
    }

    @Override
    public String getItem(int i) {
        return strings[i];
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(viewGroup.getContext()).inflate(android.R.layout.simple_list_item_1, viewGroup, false);
            holder.text = (TextView) view;
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.text.setText(getItem(i));
        if (selectedStrings.contains(getItem(i))) {
            holder.text.setPaintFlags(holder.text.getPaintFlags() | Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.text.setPaintFlags(0);
        }
        return view;
    }

    private class ViewHolder {
        TextView text;
    }

    public ArrayList<String> getSelectedStrings() {
        return selectedStrings;
    }


}



