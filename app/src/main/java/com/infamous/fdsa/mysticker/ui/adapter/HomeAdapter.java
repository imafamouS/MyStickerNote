package com.infamous.fdsa.mysticker.ui.adapter;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.AppConfig;
import com.infamous.fdsa.mysticker.common.TextUtils;
import com.infamous.fdsa.mysticker.common.model.note.Note;
import com.infamous.fdsa.mysticker.common.myenum.MyColor;

import java.util.ArrayList;

/**
 * Created by apple on 5/22/17.
 */

public class HomeAdapter extends BaseAdapter {

    public static final int FOR_ACTIVITY = 0;
    public static final int FOR_WIDGET = 1;
    Context context;
    ArrayList<Note> data;
    TextUtils textUtils;
    int type;
    //Hàm khởi tạo
    public HomeAdapter(Context context, ArrayList<Note> data, int type) {
        this.context = context;
        this.data = data;
        textUtils = TextUtils.getInstance(context);
        this.type = type;

    }

    public ArrayList<Note> getData() {
        return data;
    }

    public void setData(ArrayList<Note> data) {
        this.data = data;
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public Note getItem(int position) {
        return data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public int getViewTypeCount() {

        return getCount() == 0 ? 1 : getCount();
    }

    @Override
    public int getItemViewType(int position) {

        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_list_note, parent, false);
            viewHolder = new ViewHolder(convertView);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        Note item = this.data.get(position);
        viewHolder.renderData(item);

        return convertView;
    }


    class ViewHolder {
        View header_color;
        RelativeLayout parent_layout_item_note;
        TextView text_view_title;
        ImageView image_view_type_checklist;
        TextView text_view_time_create;

        View v;

        public ViewHolder(View v) {
            this.v = v;
            header_color = (View) v.findViewById(R.id.header_color);
            parent_layout_item_note = (RelativeLayout) v.findViewById(R.id.parent_layout_item_note);
            text_view_title = (TextView) v.findViewById(R.id.text_view_title);
            image_view_type_checklist = (ImageView) v.findViewById(R.id.image_view_type_checklist);
            text_view_time_create = (TextView) v.findViewById(R.id.text_view_time_create);
        }

        public void renderData(Note note) {
            header_color.setBackgroundColor(Color.parseColor(note.getColor()));
            parent_layout_item_note.setBackgroundColor(Color.parseColor(MyColor.getColorByHex(note.getColor()).getColor3()));
            text_view_time_create.setText(textUtils.formatStringDate(note.getDateCreate()));

            int limitLength = 0;
            if (type == FOR_ACTIVITY) {
                limitLength = AppConfig.LIMIT_CHARACTER_TITLE_SHOW_MAIN_ACTIVITY;
            } else {
                limitLength = AppConfig.LIMIT_CHARACTER_TITLE_SHOW_WIDGET;
            }

            if (note.isComplete()) {
                String str = buildStringCheckComplete(note.getTitle(), limitLength);
                text_view_title.setText(textUtils.showTextFromHTML(str));
            } else {
                text_view_title.setText(textUtils.formatTitleShowing(note.getTitle(), limitLength));
            }

            if (note.getType() == Note.CHECKLIST) {
                image_view_type_checklist.setVisibility(View.VISIBLE);
            } else {
                image_view_type_checklist.setVisibility(View.GONE);
            }
        }

        private String buildStringCheckComplete(String str, int limitLength) {
            String result = "";
            result = textUtils.formatTitleShowing(str, limitLength);
            result = textUtils.buildDeleteString(result);
            result = textUtils.buildFontColor(result, MyColor.COLOR_COMPLETE.getColor2());

            return result;
        }
    }
}
