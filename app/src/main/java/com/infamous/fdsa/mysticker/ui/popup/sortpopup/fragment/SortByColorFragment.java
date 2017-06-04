package com.infamous.fdsa.mysticker.ui.popup.sortpopup.fragment;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.PublicData;
import com.infamous.fdsa.mysticker.common.myenum.TypePopup;
import com.infamous.fdsa.mysticker.common.myinterface.ICallBackFromPopup;
import com.infamous.fdsa.mysticker.ui.popup.BasePopupAdapter;
import com.infamous.fdsa.mysticker.ui.popup.sortpopup.SortPopup;

/**
 * Created by apple on 5/22/17.
 */

public class SortByColorFragment extends android.support.v4.app.Fragment implements AdapterView.OnItemClickListener {
    GridView grid_view;
    Context context;
    ICallBackFromPopup iCallBackFromPopup;
    SortPopup sortPopup;

    public SortByColorFragment() {

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
        View view = inflater.inflate(R.layout.fragment_sort_color, container, false);
        context = getActivity().getApplicationContext();
        grid_view = (GridView) view.findViewById(R.id.grid_view);

        grid_view.setAdapter(new BasePopupAdapter(context, PublicData.getDataSearchColorPopup()));
        grid_view.setOnItemClickListener(this);

        return view;
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        iCallBackFromPopup.callBackFromPopup(TypePopup.POPUP_SEARCH_COLOR, position);

        this.sortPopup.dismiss();
    }
}
