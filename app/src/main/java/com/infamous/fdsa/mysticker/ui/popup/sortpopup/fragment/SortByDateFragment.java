package com.infamous.fdsa.mysticker.ui.popup.sortpopup.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.PublicData;
import com.infamous.fdsa.mysticker.common.myenum.TypePopup;
import com.infamous.fdsa.mysticker.common.myinterface.ICallBackFromPopup;
import com.infamous.fdsa.mysticker.ui.popup.BasePopupAdapter;
import com.infamous.fdsa.mysticker.ui.popup.sortpopup.SortPopup;

/**
 * Created by apple on 5/22/17.
 */

public class SortByDateFragment extends Fragment implements AdapterView.OnItemClickListener {

    Context context;
    ListView list_view;
    ICallBackFromPopup iCallBackFromPopup;
    SortPopup sortPopup;

    public SortByDateFragment() {
        super();
    }

    public void setCallBackFromPopup(ICallBackFromPopup callBackFromPopup) {
        this.iCallBackFromPopup = callBackFromPopup;
    }

    public void setSortPopup(SortPopup sortPopup) {
        this.sortPopup = sortPopup;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sort_date, container, false);
        context = getActivity().getApplicationContext();
        list_view = (ListView) view.findViewById(R.id.list_view);

        list_view.setAdapter(new BasePopupAdapter(context, PublicData.getDataSearchDatePopup()));
        list_view.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        iCallBackFromPopup.callBackFromPopup(TypePopup.POPUP_SEACH_DATE, position);

        this.sortPopup.dismiss();

    }
}
