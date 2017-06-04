package com.infamous.fdsa.mysticker.ui.popup.TextSizeDialog;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.model.popup.PopupItem;

import java.util.ArrayList;

/**
 * Created by apple on 5/30/17.
 */

public class TextSizeAdapter extends BaseAdapter {
    ArrayList<PopupItem> data;
    Context context;

    public TextSizeAdapter(Context context, ArrayList<PopupItem> data) {
        this.context = context;
        this.data = data;
    }

    public void resetSelected() {
        for (int i = 0; i < data.size(); i++) {
            this.data.get(i).setIsSelected(false);
        }
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public PopupItem getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getItemViewType(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {
        return getCount() == 0 ? 1 : getCount();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_text_size, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        PopupItem item = data.get(position);
        viewHolder.textView.setText(item.getTitle());

        if (item.getIsSelected()) {
            viewHolder.imageView.setVisibility(View.VISIBLE);
        } else {
            viewHolder.imageView.setVisibility(View.GONE);
        }

        return convertView;
    }

    class ViewHolder {
        TextView textView;
        ImageView imageView;

        public ViewHolder(View v) {
            textView = (TextView) v.findViewById(R.id.text_view);
            imageView = (ImageView) v.findViewById(R.id.image_view);
        }
    }
}