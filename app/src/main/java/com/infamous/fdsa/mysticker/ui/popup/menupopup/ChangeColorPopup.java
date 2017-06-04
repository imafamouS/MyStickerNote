package com.infamous.fdsa.mysticker.ui.popup.menupopup;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.GridView;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.PublicData;
import com.infamous.fdsa.mysticker.common.model.popup.PopupItem;
import com.infamous.fdsa.mysticker.common.myenum.TypePopup;
import com.infamous.fdsa.mysticker.common.myinterface.ICallBackFromPopup;

import java.util.ArrayList;

/**
 * Created by apple on 5/28/17.
 */

public class ChangeColorPopup extends DialogFragment implements AdapterView.OnItemClickListener {

    Context context;
    GridView gridView;
    ICallBackFromPopup callback;
    public ChangeColorPopup() {
        super();
    }

    public void setCallback(ICallBackFromPopup callback) {
        this.callback = callback;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_change_color_popup, container, false);
        gridView = (GridView) v.findViewById(R.id.grid_view);

        gridView.setAdapter(new ChangeColorAdapter(this.getContext(), PublicData.getDataGridViewColor()));
        gridView.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        callback.callBackFromPopup(TypePopup.POPUP_CHANGE_COLOR, position + 1);
    }

    class ChangeColorAdapter extends BaseAdapter {
        Context context;
        ArrayList<PopupItem> data;

        public ChangeColorAdapter(Context context, ArrayList<PopupItem> data) {
            this.context = context;
            this.data = data;
        }

        @Override
        public int getCount() {
            return data.size();
        }

        @Override
        public PopupItem getItem(int position) {
            return this.data.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if (convertView == null) {
                convertView = LayoutInflater.from(context).inflate(R.layout.item_change_color_popup, parent, false);
                holder = new ViewHolder(convertView);
                convertView.setTag(holder);
            } else {
                holder = (ViewHolder) convertView.getTag();
            }
            PopupItem item = this.data.get(position);
            Glide.with(context).load(item.getImage()).asBitmap().override(800,800).fitCenter().into(holder.imageView);

            return convertView;
        }

        class ViewHolder {
            ImageView imageView;
            View v;

            public ViewHolder(View v) {
                this.v = v;
                imageView = (ImageView) v.findViewById(R.id.image_view);
            }
        }
    }
}
