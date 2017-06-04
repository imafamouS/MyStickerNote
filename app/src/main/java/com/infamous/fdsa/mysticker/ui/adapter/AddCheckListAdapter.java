package com.infamous.fdsa.mysticker.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.model.note.ItemCheckList;
import com.infamous.fdsa.mysticker.common.myinterface.IClickOnCheckListItem;

import java.util.ArrayList;

/**
 * Created by apple on 5/28/17.
 */

public class AddCheckListAdapter extends BaseAdapter {

    public ArrayList<ItemCheckList> data = new ArrayList<>();
    Context context;
    IClickOnCheckListItem clickOnCheckListItem;

    public AddCheckListAdapter(Context context, ArrayList<ItemCheckList> data, IClickOnCheckListItem clickOnCheckListItem) {
        this.context = context;
        this.data = data;
        this.clickOnCheckListItem = clickOnCheckListItem;
    }

    public void addItem(ItemCheckList itemCheckList) {
        for (int i = 0; i < data.size(); i++) {
            if (this.data.get(i).getContent().equalsIgnoreCase(itemCheckList.getContent())) {
                return;
            }
        }
        this.data.add(itemCheckList);
    }

    public void deleteItem(ItemCheckList itemCheckList) {
        this.data.remove(itemCheckList);
    }

    @Override
    public int getCount() {
        return data.size();
    }

    @Override
    public ItemCheckList getItem(int position) {
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
            convertView = LayoutInflater.from(context).inflate(R.layout.item_add_checklist, parent, false);
            holder = new ViewHolder(context, convertView, position, clickOnCheckListItem);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ItemCheckList item = this.data.get(position);
        holder.text_view_title.setText(item.getContent());
        return convertView;
    }

    @Override
    public boolean isEmpty() {
        return false;
    }

    class ViewHolder implements View.OnClickListener {
        RelativeLayout relative_layout_item_checklist;
        TextView text_view_title;
        LinearLayout linear_layout_delete;
        Context context;
        View view;
        int position;

        IClickOnCheckListItem clickOnCheckListItem;

        public ViewHolder(Context context, View view, int position, IClickOnCheckListItem clickOnCheckListItem) {
            this.context = context;
            this.view = view;
            this.position = position;
            this.clickOnCheckListItem = clickOnCheckListItem;

            relative_layout_item_checklist = (RelativeLayout) view.findViewById(R.id.relative_layout_item_checklist);
            text_view_title = (TextView) view.findViewById(R.id.text_view_title);
            linear_layout_delete = (LinearLayout) view.findViewById(R.id.linear_layout_delete);

            relative_layout_item_checklist.setOnClickListener(this);
            linear_layout_delete.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.relative_layout_item_checklist:
                    clickOnCheckListItem.onClickParent(position);
                    break;
                case R.id.linear_layout_delete:
                    clickOnCheckListItem.onClickEdit(position);
                    break;
            }
        }
    }
}
