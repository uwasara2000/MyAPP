package com.s23010541.myapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class IconAdapter2 extends BaseAdapter {
    Context context;
    String[] labels;
    int[] icons;

    public IconAdapter2(Context context, String[] labels, int[] icons) {
        this.context = context;
        this.labels = labels;
        this.icons = icons;
    }

    @Override
    public int getCount() {
        return labels.length;
    }

    @Override
    public Object getItem(int position) {
        return labels[position];
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        View view = LayoutInflater.from(context).inflate(R.layout.grid_item2, parent, false);
        ImageView icon = view.findViewById(R.id.iconImage);
        TextView label = view.findViewById(R.id.iconLabel);

        icon.setImageResource(icons[position]);
        label.setText(labels[position]);

        return view;
    }
}
