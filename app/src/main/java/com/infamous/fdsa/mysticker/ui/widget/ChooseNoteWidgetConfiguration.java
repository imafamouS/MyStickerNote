package com.infamous.fdsa.mysticker.ui.widget;

import android.appwidget.AppWidgetManager;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.AppConfig;
import com.infamous.fdsa.mysticker.common.ba.NoteManager;
import com.infamous.fdsa.mysticker.common.model.note.Note;
import com.infamous.fdsa.mysticker.common.model.note.WidgetNote;
import com.infamous.fdsa.mysticker.common.myenum.MyColor;
import com.infamous.fdsa.mysticker.common.myenum.TypePopup;
import com.infamous.fdsa.mysticker.common.myinterface.ICallBackFromPopup;
import com.infamous.fdsa.mysticker.ui.activity.ViewCheckListNoteActivity;
import com.infamous.fdsa.mysticker.ui.activity.ViewNormalNoteActivity;
import com.infamous.fdsa.mysticker.ui.adapter.HomeAdapter;
import com.infamous.fdsa.mysticker.ui.adapter.NodataAdapter;
import com.infamous.fdsa.mysticker.ui.popup.addnewpopup.AddNewNotePopup;
import com.infamous.fdsa.mysticker.ui.popup.sortpopup.SortPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by apple on 6/1/17.
 */

public class ChooseNoteWidgetConfiguration extends AppCompatActivity implements ICallBackFromPopup, View.OnClickListener, AdapterView.OnItemClickListener {
    LinearLayout search_bar;
    TextView text_view_search;
    ListView list_view;
    RelativeLayout relative_layout_add_note_header;
    AddNewNotePopup addNewNoteBuilder;
    SortPopup sortPopup;
    View headerView;
    HomeAdapter homeAdapter;
    NodataAdapter nodataAdapter;
    ArrayList<Note> noteList;
    NoteManager noteManager;
    Map<TypePopup, Integer> positionType = new HashMap<>();
    TypePopup currentType;
    private int mAppWidgetId = AppWidgetManager.INVALID_APPWIDGET_ID;
    private int mResult = RESULT_CANCELED;

    public ChooseNoteWidgetConfiguration() {
        super();
    }

    private void initPositionType() {
        positionType.put(TypePopup.POPUP_CREATE_NOTE, 0);
        positionType.put(TypePopup.POPUP_SEACH_DATE, 0);
        positionType.put(TypePopup.POPUP_SEARCH_COLOR, 0);
    }


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.layout_widget_choose_note);
        setResult(mResult);

        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        if (extras != null) {
            mAppWidgetId = extras.getInt(AppWidgetManager.EXTRA_APPWIDGET_ID, AppWidgetManager.INVALID_APPWIDGET_ID);
        }

        if (mAppWidgetId == AppWidgetManager.INVALID_APPWIDGET_ID) {

            finish();
        }

        initPositionType();

        list_view = (ListView) findViewById(R.id.list_view);
        search_bar = (LinearLayout) findViewById(R.id.search_bar);
        text_view_search = (TextView) findViewById(R.id.text_view_search);

        addNewNoteBuilder = new AddNewNotePopup(this);
        addNewNoteBuilder.setCallBackFromPopup(this);
        sortPopup = new SortPopup();
        sortPopup.setiCallBackFromPopup(this);

        nodataAdapter = new NodataAdapter(this.getApplicationContext());
        //  headerView = getLayoutInflater().inflate(R.layout.item_header_list_add_new_note, list_view, false);
        // relative_layout_add_note_header = (RelativeLayout) headerView.findViewById(R.id.relative_layout_add_note_header);
        // list_view.addHeaderView(headerView);
        list_view.setAdapter(nodataAdapter);


        noteManager = NoteManager.getInstance(this.getApplicationContext());

        searchByType(TypePopup.POPUP_SEACH_DATE, 0);
        currentType = TypePopup.POPUP_SEACH_DATE;


        list_view.setOnItemClickListener(this);
        search_bar.setOnClickListener(this);
//        relative_layout_add_note_header.setOnClickListener(this);

    }


    @Override
    public void callBackFromPopup(TypePopup type, int position) {
        if (type == TypePopup.POPUP_CREATE_NOTE) {
            if (position == 0) {
                Intent intent = new Intent(this, ViewNormalNoteActivity.class);
                startActivityForResult(intent, AppConfig.RequestCode.REQUEST_CODE_ADD_NORMAL_NOTE_FROM_WIDGET);
            } else if (position == 1) {
                Intent intent = new Intent(this, ViewCheckListNoteActivity.class);
                startActivityForResult(intent, AppConfig.RequestCode.REQUEST_CODE_ADD_CHECKLIST_NOTE_FROM_WIDGET);
            }
        } else {
            searchByType(type, position);
        }
    }

    private void searchByType(TypePopup type, int position) {
        String typeSearch = "";
        if (type == TypePopup.POPUP_SEARCH_COLOR) {
            if (position != 0) {
                noteList = noteManager.findNoteByColor(MyColor.getColorByIndex(position).getColor());
            } else {
                noteList = noteManager.findAllNote();
            }
            typeSearch = getString(R.string.TEXT_SEACH_BY_COLOR);
        } else if (type == TypePopup.POPUP_SEACH_DATE) {
            switch (position) {
                case 0:
                    noteList = noteManager.findNoteByDateCreate();
                    typeSearch = getString(R.string.TEXT_SEACH_BY_DATE_CREATE);
                    break;
                case 1:
                    noteList = noteManager.findNoteByLastModify();
                    typeSearch = getString(R.string.TEXT_SEACH_BY_LAST_MODIFY);
                    break;
                case 2:
                    break;
                default:
                    break;

            }
        }

        if (noteList != null && noteList.size() > 0) {
            updateStringSearch(typeSearch);

            if (homeAdapter == null) {
                homeAdapter = new HomeAdapter(this.getApplicationContext(), noteList, HomeAdapter.FOR_WIDGET);
                list_view.setAdapter(homeAdapter);
                homeAdapter.notifyDataSetChanged();
                return;
            }

//            headerView.setVisibility(View.VISIBLE);
            homeAdapter.setData(noteList);
            list_view.setAdapter(homeAdapter);
            homeAdapter.notifyDataSetChanged();

        } else {
            // headerView.setVisibility(View.GONE);
            list_view.setAdapter(nodataAdapter);
        }

        positionType.put(type, position);
        currentType = type;
    }

    private void updateStringSearch(String str) {
        this.text_view_search.setText(str);
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

        if (parent.getId() == R.id.list_view) {

            if (noteList == null || noteList.size() <= 0) {
                //No item

                if (position != 0 && headerView.getVisibility() == View.VISIBLE) {
                    return;
                } else {
                    //addNewNoteBuilder.show();
                }

            } else {
               /* Intent intent = new Intent(WidgetMySticker.ACTION_OPEN_ACTIVITY);
                intent.putExtra("data","hihihihi");
                getApplicationContext().sendBroadcast(intent);
                finish();*/
                Note note = noteManager.findNoteById(this.noteList.get(position).getId());
                noteManager.insertWidgetNote(new WidgetNote(mAppWidgetId + "", note.getId()));

                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(this.getApplicationContext());

                WidgetMySticker.updateAppWidget(this.getApplicationContext(), appWidgetManager, mAppWidgetId, note);

                Intent resultValue = new Intent();
                resultValue.putExtra(AppWidgetManager.EXTRA_APPWIDGET_ID, mAppWidgetId);
                setResult(mResult = RESULT_OK, resultValue);
                finish();
            }

        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.search_bar:
                sortPopup.show(this.getSupportFragmentManager(), "taggg");
                break;
            case R.id.relative_layout_add_note_header:
                addNewNoteBuilder.show();
                break;

        }
    }
}
