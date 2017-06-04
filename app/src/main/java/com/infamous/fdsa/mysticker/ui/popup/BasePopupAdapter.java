package com.infamous.fdsa.mysticker.ui.popup;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.model.popup.PopupItem;

import java.util.ArrayList;

/**
 * Created by apple on 5/22/17.
 */

public class BasePopupAdapter extends BaseAdapter {

    Context context;
    ArrayList<PopupItem> data;

    public BasePopupAdapter(Context context, ArrayList<PopupItem> data) {
        this.context = context;
        this.data = data;
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
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;

        if (convertView == null) {
            convertView = LayoutInflater.from(this.context).inflate(R.layout.item_popup, parent, false);
            holder = new ViewHolder(convertView);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        PopupItem item = this.data.get(position);
        holder.renderData(item);

        return convertView;
    }

    class ViewHolder {
        private ImageView image_view;
        private TextView text_view;

        private View v;

        public ViewHolder(View v) {
            this.v = v;
            this.image_view = (ImageView) v.findViewById(R.id.image_view);
            this.text_view = (TextView) v.findViewById(R.id.text_view);
        }

        public void renderData(PopupItem item) {
            Glide.with(context).load(item.getImage()).asBitmap().into(image_view);
            this.text_view.setText(item.getTitle());
        }
    }
}
