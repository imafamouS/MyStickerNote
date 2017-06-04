package com.infamous.fdsa.mysticker.ui.popup.addnewpopup;


import android.content.Context;
import android.content.DialogInterface;
import android.support.v7.app.AlertDialog;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.PublicData;
import com.infamous.fdsa.mysticker.common.myenum.TypePopup;
import com.infamous.fdsa.mysticker.common.myinterface.ICallBackFromPopup;
import com.infamous.fdsa.mysticker.ui.popup.BasePopupAdapter;

/**
 * Created by apple on 5/22/17.
 */

public class AddNewNotePopup extends AlertDialog.Builder {


    ICallBackFromPopup callBackFromPopup;

    public AddNewNotePopup(Context context) {

        super(context, 0);
        build();
    }

    public AddNewNotePopup(Context context, int themeResId) {
        super(context, themeResId);
    }

    public void setCallBackFromPopup(ICallBackFromPopup callBackFromPopup) {
        this.callBackFromPopup = callBackFromPopup;
    }

    public void build() {
        this.setTitle(getContext().getString(R.string.TEXT_ADD_NOTE))
                .setAdapter(new BasePopupAdapter(getContext(), PublicData.getDataAddNewNote()), new OnItemPopupClick());
    }


    class OnItemPopupClick implements DialogInterface.OnClickListener {
        @Override
        public void onClick(DialogInterface dialog, int which) {
            callBackFromPopup.callBackFromPopup(TypePopup.POPUP_CREATE_NOTE, which);
        }
    }
}
