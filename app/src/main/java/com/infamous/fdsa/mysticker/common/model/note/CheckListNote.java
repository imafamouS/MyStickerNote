package com.infamous.fdsa.mysticker.common.model.note;

import java.util.ArrayList;

/**
 * Created by apple on 5/21/17.
 */

public class CheckListNote extends Note {
    ArrayList<ItemCheckList> itemCheckLists = new ArrayList<>();

    public CheckListNote() {
        this.type = Note.CHECKLIST;
    }

    public ArrayList<ItemCheckList> getItemCheckLists() {
        return itemCheckLists;
    }

    public void setItemCheckLists(ArrayList<ItemCheckList> itemCheckLists) {
        this.itemCheckLists = itemCheckLists;
    }

}
