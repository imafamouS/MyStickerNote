package com.infamous.fdsa.mysticker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;

import com.infamous.fdsa.mysticker.R;

/**
 * Created by apple on 5/28/17.
 */

public class NodataDataCheckListAdapter extends BaseAdapter {
    Context context;

    public NodataDataCheckListAdapter(Context context) {
        this.context = context;
    }

    @Override
    public int getCount() {
        return 1;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        convertView = LayoutInflater.from(context).inflate(R.layout.layout_blank, parent, false);

        return convertView;
    }
}
