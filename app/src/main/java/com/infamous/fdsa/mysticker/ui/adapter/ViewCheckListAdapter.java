package com.infamous.fdsa.mysticker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.TextUtils;
import com.infamous.fdsa.mysticker.common.model.note.ItemCheckList;
import com.infamous.fdsa.mysticker.common.myinterface.IClickOnCheckListItem;

import java.util.ArrayList;

/**
 * Created by apple on 5/28/17.
 */

public class ViewCheckListAdapter extends BaseAdapter {
    public ArrayList<ItemCheckList> data;
    Context context;
    IClickOnCheckListItem clickOnCheckListItem;
    TextUtils textUtils;

    public ViewCheckListAdapter(Context context, ArrayList<ItemCheckList> data, IClickOnCheckListItem clickOnCheckListItem) {
        this.context = context;
        this.data = data;
        this.clickOnCheckListItem = clickOnCheckListItem;
        textUtils = TextUtils.getInstance(context);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ItemCheckList getItem(int position) {
        return this.data.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    public ArrayList<Integer> getAllItemDone(){
        ArrayList<Integer> list=new ArrayList<>();
        for(int i=0;i<this.data.size();i++){
            if(data.get(i).isDone()){
                list.add(i);
            }

        }

        return list;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.item_view_check_list, parent, false);
            holder = new ViewHolder(context, convertView, position, clickOnCheckListItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ItemCheckList item = this.data.get(position);

        if (item.isDone()) {
            holder.text_view_title.setText(textUtils.showTextFromHTML(textUtils.buildDeleteString(item.getContent())));
            holder.image_view_check.setVisibility(View.VISIBLE);
            holder.image_view_edit_checklist.setVisibility(View.GONE);
        } else {
            holder.text_view_title.setText(item.getContent());
            holder.image_view_check.setVisibility(View.GONE);
            holder.image_view_edit_checklist.setVisibility(View.VISIBLE);
        }

        return convertView;
    }

    public boolean isAllDone() {
        boolean flag = true;

        for (int i = 0; i < data.size(); i++) {
            if (!data.get(i).isDone()) {
                flag = false;
                break;
            }
        }
        return flag;
    }

    class ViewHolder implements View.OnClickListener {
        RelativeLayout parent_layout_item_check_list;
        TextView text_view_title;
        ImageView image_view_edit_checklist;
        ImageView image_view_check;
        View v;
        Context context;
        IClickOnCheckListItem clickOnCheckListItem;
        int position;

        public ViewHolder(Context context, View itemView, int position, IClickOnCheckListItem clickOnCheckListItem) {
            this.v = itemView;
            this.context = context;
            this.clickOnCheckListItem = clickOnCheckListItem;
            this.position = position;
            parent_layout_item_check_list = (RelativeLayout) v.findViewById(R.id.parent_layout_item_check_list);
            text_view_title = (TextView) v.findViewById(R.id.text_view_title);
            image_view_edit_checklist = (ImageView) v.findViewById(R.id.image_view_edit_checklist);
            image_view_check = (ImageView) v.findViewById(R.id.image_view_check);

            parent_layout_item_check_list.setOnClickListener(this);
            image_view_edit_checklist.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.parent_layout_item_check_list:
                    clickOnCheckListItem.onClickParent(position);
                    break;
                case R.id.image_view_edit_checklist:
                    clickOnCheckListItem.onClickEdit(position);
                    break;
                default:
                    break;
            }
        }
    }
}
