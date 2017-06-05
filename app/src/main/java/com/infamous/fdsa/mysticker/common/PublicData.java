package com.infamous.fdsa.mysticker.common;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.model.popup.PopupItem;

import java.util.ArrayList;


/**
 * Created by apple on 5/22/17.
 */

public class PublicData {
    //Dữ liệu cho popup tạo mới
    public static ArrayList<PopupItem> getDataAddNewNote() {
        ArrayList<PopupItem> list = new ArrayList<>();

        list.add(new PopupItem("1", "Text Note", R.drawable.icon_text_note));
        list.add(new PopupItem("2", "Checklist Note", R.drawable.icon_check_note));

        return list;
    }
    //Dữ liệu cho popup tìm kiếm theo thời gian
    public static ArrayList<PopupItem> getDataSearchDatePopup() {
        ArrayList<PopupItem> list = new ArrayList<>();

        list.add(new PopupItem("1", "Theo ngày lập", R.drawable.icon_search_date_create));
        list.add(new PopupItem("2", "Theo lần chỉnh sửa cuối cùng", R.drawable.icon_search_last_modify));

        return list;
    }
    //Dữ liệu cho popup tìm kiếm theo màu
    public static ArrayList<PopupItem> getDataSearchColorPopup() {
        ArrayList<PopupItem> list = new ArrayList<>();

        list.add(new PopupItem("1", "Tất cả", R.drawable.icon_color_full));
        list.add(new PopupItem("2", "Màu 1", R.drawable.icon_color_1));
        list.add(new PopupItem("3", "Màu 2", R.drawable.icon_color_2));
        list.add(new PopupItem("4", "Màu 3", R.drawable.icon_color_3));
        list.add(new PopupItem("5", "Màu 4", R.drawable.icon_color_4));
        list.add(new PopupItem("6", "Màu 5", R.drawable.icon_color_5));
        list.add(new PopupItem("7", "Màu 6", R.drawable.icon_color_6));
        list.add(new PopupItem("8", "Màu 7", R.drawable.icon_color_7));
        list.add(new PopupItem("9", "Màu 8", R.drawable.icon_color_8));
        list.add(new PopupItem("10", "Màu 9", R.drawable.icon_color_9));


        return list;
    }
    //Dữ liệu cho popup chọn màu note
    public static ArrayList<PopupItem> getDataGridViewColor() {
        ArrayList<PopupItem> list = new ArrayList<>();

        list.add(new PopupItem("1", "Màu 1", R.drawable.icon_color_1));
        list.add(new PopupItem("2", "Màu 2", R.drawable.icon_color_2));
        list.add(new PopupItem("3", "Màu 3", R.drawable.icon_color_3));
        list.add(new PopupItem("4", "Màu 4", R.drawable.icon_color_4));
        list.add(new PopupItem("5", "Màu 5", R.drawable.icon_color_5));
        list.add(new PopupItem("6", "Màu 6", R.drawable.icon_color_6));
        list.add(new PopupItem("7", "Màu 7", R.drawable.icon_color_7));
        list.add(new PopupItem("8", "Màu 8", R.drawable.icon_color_8));
        list.add(new PopupItem("9", "Màu 9", R.drawable.icon_color_9));


        return list;
    }
}
