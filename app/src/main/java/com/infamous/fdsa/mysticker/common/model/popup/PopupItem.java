package com.infamous.fdsa.mysticker.common.model.popup;

/**
 * Created by apple on 5/22/17.
 */

public class PopupItem {

    private String id;
    private String title;
    private int image;
    private boolean isSelected;

    public PopupItem(String id, String title, int image) {
        this.title = title;
        this.image = image;
        this.id = id;

    }

    public boolean getIsSelected() {
        return isSelected;
    }

    public void setIsSelected(boolean isSelected) {
        this.isSelected = isSelected;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
