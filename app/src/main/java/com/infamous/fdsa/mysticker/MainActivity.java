package com.infamous.fdsa.mysticker;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.PopupMenu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.infamous.fdsa.mysticker.common.AppConfig;
import com.infamous.fdsa.mysticker.common.ba.NoteManager;
import com.infamous.fdsa.mysticker.common.model.note.Note;
import com.infamous.fdsa.mysticker.common.myenum.MyColor;
import com.infamous.fdsa.mysticker.common.myenum.TypePopup;
import com.infamous.fdsa.mysticker.common.myinterface.ICallBackFromPopup;
import com.infamous.fdsa.mysticker.ui.activity.EditCheckListNoteActivity;
import com.infamous.fdsa.mysticker.ui.activity.EditNormalNoteActivity;
import com.infamous.fdsa.mysticker.ui.activity.ViewCheckListNoteActivity;
import com.infamous.fdsa.mysticker.ui.activity.ViewNormalNoteActivity;
import com.infamous.fdsa.mysticker.ui.adapter.HomeAdapter;
import com.infamous.fdsa.mysticker.ui.adapter.NodataAdapter;
import com.infamous.fdsa.mysticker.ui.popup.addnewpopup.AddNewNotePopup;
import com.infamous.fdsa.mysticker.ui.popup.sortpopup.SortPopup;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;


public class MainActivity extends AppCompatActivity implements View.OnClickListener, ICallBackFromPopup, AdapterView.OnItemClickListener {

    ImageView image_view_menu_about;
    ImageView image_view_add_note;
    LinearLayout search_bar;
    TextView text_view_search;

    AddNewNotePopup addNewNoteBuilder;
    SortPopup sortPopup;

    ListView list_view;
    NoteManager noteManager;
    HomeAdapter homeAdapter;
    NodataAdapter noDataAdapter;
    ArrayList<Note> noteList = new ArrayList<>();

    View footerListView;
    LinearLayout linear_layout_footer;
    Map<TypePopup, Integer> positionType = new HashMap<>();
    TypePopup currentType;

    private void initPositionType() {
        positionType.put(TypePopup.POPUP_CREATE_NOTE, 0);
        positionType.put(TypePopup.POPUP_SEACH_DATE, 0);
        positionType.put(TypePopup.POPUP_SEARCH_COLOR, 0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initPositionType();

        initView();
        showStartData();
    }
    //hàm khởi tạo
    private void initView() {
        image_view_menu_about = (ImageView) findViewById(R.id.image_view_menu_about);
        image_view_add_note = (ImageView) findViewById(R.id.image_view_add_note);
        search_bar = (LinearLayout) findViewById(R.id.search_bar);
        text_view_search = (TextView) findViewById(R.id.text_view_search);

        addNewNoteBuilder = new AddNewNotePopup(this);
        addNewNoteBuilder.setCallBackFromPopup(this);
        sortPopup = new SortPopup();
        sortPopup.setiCallBackFromPopup(this);

        list_view = (ListView) findViewById(R.id.list_view);

        noDataAdapter = new NodataAdapter(this.getApplicationContext());
        footerListView = View.inflate(this.getApplicationContext(), R.layout.layout_footer_main_list_view, null);
        linear_layout_footer = (LinearLayout) footerListView.findViewById(R.id.linear_layout_footer);
        list_view.setAdapter(noDataAdapter);
        list_view.addFooterView(footerListView);
        list_view.setOnItemClickListener(this);

        image_view_menu_about.setOnClickListener(this);
        image_view_add_note.setOnClickListener(this);
        search_bar.setOnClickListener(this);
        linear_layout_footer.setOnClickListener(this);
        noteManager = NoteManager.getInstance(this.getApplicationContext());
    }
    //Hàm hiện dữ liệu ban đầu
    private void showStartData() {
        updateStringSearch( getString(R.string.TEXT_SEACH_BY_DATE_CREATE));
        searchByType(TypePopup.POPUP_SEACH_DATE, 0);
        currentType = TypePopup.POPUP_SEACH_DATE;
    }

    @Override
    public void callBackFromPopup(TypePopup type, int position) {
        if (type == TypePopup.POPUP_CREATE_NOTE) {
            if (position == 0) {
                Intent intent = new Intent(this, EditNormalNoteActivity.class);
                startActivityForResult(intent, AppConfig.RequestCode.REQUEST_CODE_ADD_NORMAL_NOTE);
            } else if (position == 1) {
                Intent intent = new Intent(this, EditCheckListNoteActivity.class);
                startActivityForResult(intent, AppConfig.RequestCode.REQUEST_CODE_ADD_CHECKLIST_NOTE);
            }
        } else {
            searchByType(type, position);
        }
    }
    //Khi click vào item trên listview
    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        if (parent.getId() == R.id.list_view) {

            if (noteList == null || noteList.size() <= 0) {
                //No item
                if (position != 0) {
                    return;
                }
                image_view_add_note.performClick();
            } else {
                openActivityViewNote(homeAdapter.getItem(position));
            }

        }
    }
    //Hiện activity tương ứng với loại note (xem nội dung note)
    private void openActivityViewNote(Note note) {
        String noteID = note.getId();
        if (note.getType() == Note.NORMAL) {
            Intent intent = new Intent(this, ViewNormalNoteActivity.class);

            intent.putExtra("noteid", noteID);
            startActivityForResult(intent, AppConfig.RequestCode.REQUEST_CODE_OPEN_VIEW_NORMAL);
        } else if (note.getType() == Note.CHECKLIST) {
            Intent intent = new Intent(this, ViewCheckListNoteActivity.class);

            intent.putExtra("noteid", noteID);
            startActivityForResult(intent, AppConfig.RequestCode.REQUEST_CODE_OPEN_VIEW_CHECKLIST);
        }

    }
    //Hàm lọc note theo loại
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
                homeAdapter = new HomeAdapter(this.getApplicationContext(), noteList, HomeAdapter.FOR_ACTIVITY);
                list_view.setAdapter(homeAdapter);
                homeAdapter.notifyDataSetChanged();
                return;
            }

            footerListView.setVisibility(View.VISIBLE);
            homeAdapter.setData(noteList);
            list_view.setAdapter(homeAdapter);
            homeAdapter.notifyDataSetChanged();

        } else {
            footerListView.setVisibility(View.GONE);
            list_view.setAdapter(noDataAdapter);
        }
        positionType.put(type, position);
        currentType = type;
    }

    private void updateStringSearch(String str) {
        this.text_view_search.setText(str);
    }

    //Hiền popup
    private void showPopup(View v) {
        PopupMenu popup = new PopupMenu(this, v);
        popup.getMenuInflater().inflate(R.menu.menu_about, popup.getMenu());
        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            @Override
            public boolean onMenuItemClick(MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.menu_about:
                        showAlert(getString(R.string.ABOUT_ME));
                        return true;
                    default:
                        return false;
                }
            }
        });
        popup.show();
    }

    //Hiện thông báo
    private void showAlert(String mess) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage(mess)
                .setCancelable(false)
                .setPositiveButton(getString(R.string.TEXT_ACTION_CLOSE), new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        alertDialogBuilder.show();

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppConfig.ResultCode.RESULT_CODE_DELETE_NOTE || resultCode == AppConfig.ResultCode.RESULT_CODE_VIEW_NOTE ||
                resultCode == AppConfig.ResultCode.RESULT_CODE_EDIT_OR_CREATE_NOTE) {
            if (data.getBooleanExtra("success", false) || data.getBooleanExtra("reload", false)) {

                searchByType(currentType, positionType.get(currentType));
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_menu_about:
                showPopup(v);
                break;
            case R.id.image_view_add_note:
                addNewNoteBuilder.show();
                break;
            case R.id.search_bar:
                sortPopup.show(getSupportFragmentManager(), "TAG");
                break;
            case R.id.linear_layout_footer:
                image_view_add_note.performClick();
                break;
            default:
                break;
        }
    }
}
