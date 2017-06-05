package com.infamous.fdsa.mysticker.ui.popup.TextSizeDialog;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.myenum.TypePopup;
import com.infamous.fdsa.mysticker.common.myinterface.ICallBackFromPopup;

/**
 * Created by apple on 5/30/17.
 */

public class TextSizePopup extends DialogFragment implements AdapterView.OnItemClickListener {
    ListView listView;
    TextSizeAdapter adapter;
    ICallBackFromPopup callBackFromPopup;
    public TextSizePopup() {
        super();
    }

    public ICallBackFromPopup getCallBackFromPopup() {
        return callBackFromPopup;
    }

    public void setCallBackFromPopup(ICallBackFromPopup callBackFromPopup) {
        this.callBackFromPopup = callBackFromPopup;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.layout_text_size, container, false);

        listView = (ListView) v.findViewById(R.id.list_view);
        //adapter = new TextSizeAdapter(this.getContext(), PublicData.getDataTextSize());
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(this);
        return v;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        callBackFromPopup.callBackFromPopup(TypePopup.POPUP_CHANGE_TEXT_SIZE, position);
    }
}
