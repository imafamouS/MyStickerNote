package com.infamous.fdsa.mysticker.ui.popup.menupopup;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.myinterface.IMenuToolClick;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by apple on 5/27/17.
 */

public class MenuPopup {

    public static final int MODE_VIEW = 0;
    public static final int MODE_EDIT = 1;
    PopupMenu popupMenu;
    Context context;
    View v;
    IMenuToolClick menuToolClick;
    boolean isChecked;
    int mode;

    public MenuPopup(Context context, View v, @Nullable int mode, IMenuToolClick callBackWhenClickMenuTool) {
        this.context = context;
        this.v = v;
        this.menuToolClick = callBackWhenClickMenuTool;
        this.setMode(mode);

    }

    public int getMode() {
        return mode;
    }

    public void setMode(int mode) {
        this.mode = mode;
        build();
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
        if (isChecked) {
            popupMenu.getMenu().getItem(0).setIcon(R.drawable.icon_uncheck);
            popupMenu.getMenu().getItem(0).setTitle("Uncheck");
        } else {
            popupMenu.getMenu().getItem(0).setIcon(R.drawable.icon_check);
            popupMenu.getMenu().getItem(0).setTitle("Check");
        }
    }

    private void build() {
        popupMenu = new PopupMenu(context, v);
       try {
            Field[] fields = popupMenu.getClass().getDeclaredFields();
            for (Field field : fields) {
                if ("mPopup".equals(field.getName())) {
                    field.setAccessible(true);
                    Object menuPopupHelper = field.get(popupMenu);
                    Class<?> classPopupHelper = Class.forName(menuPopupHelper.getClass().getName());
                    Method setForceIcons = classPopupHelper.getMethod("setForceShowIcon", boolean.class);
                    setForceIcons.invoke(menuPopupHelper, true);
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

        if (mode == MODE_VIEW) {
            buildModeView();
        } else if (mode == MODE_EDIT) {
            buildModeEdit();
        }
    }

    private void buildModeView() {
        popupMenu.getMenuInflater().inflate(R.menu.tool_view_note, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_check:
                        menuToolClick.onCheckClick();
                        return true;
                    case R.id.menu_add_note:
                        menuToolClick.onAddNewClick();
                        return true;
                    case R.id.menu_edit_note:
                        menuToolClick.onEditClick();
                        return true;
                    case R.id.menu_delete_note:
                        menuToolClick.onDeleteClick();
                        return true;
                    default:
                        return true;
                }
            }
        });
    }

    private void buildModeEdit() {
        popupMenu.getMenuInflater().inflate(R.menu.tool_edit_note, popupMenu.getMenu());

        popupMenu.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {

            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_save:
                        menuToolClick.onSave();
                        return true;
                    case R.id.menu_change_color:
                        menuToolClick.onChangeColor();
                        return true;
                    case R.id.menu_make_new:
                        menuToolClick.onReset();
                        return true;
                    default:
                        return true;
                }
            }
        });
    }

    public void show() {
        this.popupMenu.show();
    }

    public void dissmiss() {
        this.popupMenu.dismiss();
    }
}
