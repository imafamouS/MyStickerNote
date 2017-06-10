package com.infamous.fdsa.mysticker.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.AppConfig;
import com.infamous.fdsa.mysticker.common.TextUtils;
import com.infamous.fdsa.mysticker.common.ba.NoteManager;
import com.infamous.fdsa.mysticker.common.model.note.CheckListNote;
import com.infamous.fdsa.mysticker.common.model.note.Note;
import com.infamous.fdsa.mysticker.common.myenum.MyColor;
import com.infamous.fdsa.mysticker.common.myinterface.IClickOnCheckListItem;
import com.infamous.fdsa.mysticker.common.myinterface.IMenuToolClick;
import com.infamous.fdsa.mysticker.ui.adapter.ViewCheckListAdapter;
import com.infamous.fdsa.mysticker.ui.popup.menupopup.MenuPopup;
import com.infamous.fdsa.mysticker.ui.widget.WidgetMySticker;

/**
 * Created by apple on 5/23/17.
 */

public class ViewCheckListNoteActivity extends BaseSlideActivity implements View.OnClickListener {
    LinearLayout linear_layout_top_bar_view_note;
    TextView text_view_title_note;
    ImageView image_view_edit_note;
    ImageView image_view_tool;
    ListView list_view_check_list;
    ViewCheckListAdapter viewCheckListAdapter;
    NoteManager noteManager;
    Note currentNote;
    MenuPopup menuPopup;
    TextUtils textUtils;
    LinearLayout linear_layout_date_create;
    TextView text_view_date_create;
    boolean isNeedSaved = true;

    ImageView image_view_back;

    public ViewCheckListNoteActivity() {
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_checklist_note);


        initView();
        noteManager = NoteManager.getInstance(this.getApplicationContext());

        String noteID = getIntent().getStringExtra("noteid");
        currentNote = noteManager.findNoteById(noteID);
        if (noteID != null || !noteID.equals("")) {
            showData();
        }

    }
    //Hàm khởi tạo
    private void initView() {
        linear_layout_top_bar_view_note = (LinearLayout) findViewById(R.id.linear_layout_top_bar_view_note);
        image_view_back=(ImageView)findViewById(R.id.image_view_back);
        text_view_title_note = (TextView) findViewById(R.id.text_view_title_note);
        image_view_edit_note = (ImageView) findViewById(R.id.image_view_edit_note);
        image_view_tool = (ImageView) findViewById(R.id.iamge_view_tool);
        textUtils = TextUtils.getInstance(this.getApplicationContext());

        linear_layout_date_create = (LinearLayout) findViewById(R.id.linear_layout_date_create);
        text_view_date_create = (TextView) findViewById(R.id.text_view_date_create);

        menuPopup = new MenuPopup(this, image_view_tool, MenuPopup.MODE_VIEW, new OnClickMenuToolItem());

        list_view_check_list = (ListView) findViewById(R.id.list_view_check_list);

        image_view_edit_note.setOnClickListener(this);
        image_view_tool.setOnClickListener(this);
        image_view_back.setOnClickListener(this);
    }
    //Hiện dữ liệu note
    private void showData() {

        MyColor myColor = MyColor.getColorByHex(currentNote.getColor());

        if (currentNote.isComplete()) {
            text_view_title_note.setText(textUtils.showTextFromHTML(textUtils.buildDeleteString(currentNote.getTitle())));
        } else {
            text_view_title_note.setText(currentNote.getTitle());
        }
        menuPopup.setChecked(currentNote.isComplete());
        linear_layout_top_bar_view_note.setBackgroundColor(Color.parseColor(myColor.getColor2()));

        text_view_date_create.setText(textUtils.formatStringDate(currentNote.getDateCreate()));
        linear_layout_date_create.setBackgroundColor(Color.parseColor(myColor.getColor3()));

        list_view_check_list.setBackgroundColor(Color.parseColor(myColor.getColor3()));
        list_view_check_list.setDivider(new ColorDrawable(Color.parseColor(myColor.getColor4())));
        list_view_check_list.setDividerHeight(5);

        viewCheckListAdapter = new ViewCheckListAdapter(this.getApplicationContext(), ((CheckListNote) currentNote).getItemCheckLists(), new OnClickitemCheckList());
        list_view_check_list.setAdapter(viewCheckListAdapter);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_edit_note:
                //Khi nhấn nút edit
                editNote();
                break;
            case R.id.iamge_view_tool:
                //Khi nhấn vào bộ công cụ (dấu 3.)
                menuPopup.show();
                break;
            case R.id.image_view_back:
                onBackPressed();
                break;

        }
    }
    //Khi nhấn check note
    private void checkNote() {
        if (menuPopup.isChecked()) {
            menuPopup.setChecked(false);
            currentNote.setComplete(false);
        } else {
            menuPopup.setChecked(true);
            currentNote.setComplete(true);
        }
        if (currentNote.isComplete()) {
            text_view_title_note.setText(textUtils.showTextFromHTML(textUtils.buildDeleteString(currentNote.getTitle())));
        } else {
            text_view_title_note.setText(currentNote.getTitle());
        }

        if (menuPopup.isChecked()) {
            for (int i = 0; i < this.viewCheckListAdapter.data.size(); i++) {
                this.viewCheckListAdapter.data.get(i).setDone(true);
            }
        } else {
            for (int i = 0; i < this.viewCheckListAdapter.data.size(); i++) {
                this.viewCheckListAdapter.data.get(i).setDone(false);
            }
        }
        currentNote.setLastMotify(textUtils.getCurrentTime());

        viewCheckListAdapter.notifyDataSetChanged();
        if (this.viewCheckListAdapter.data != null && this.viewCheckListAdapter.data.size() >= 0) {
            ((CheckListNote) currentNote).setItemCheckLists(this.viewCheckListAdapter.data);
        }

        if (noteManager.updateNote(currentNote) > 0) {
            isNeedSaved = false;
        }
    }
    //Xóa note
    private void deleteNote(Note item) {
        if (noteManager.deleteNote(item) > 0) {
            Toast.makeText(this, "Đã xóa note thành công", Toast.LENGTH_SHORT).show();
            setResult(AppConfig.ResultCode.RESULT_CODE_DELETE_NOTE, new Intent().putExtra("success", true));
            finish();
        } else {
            Toast.makeText(this, "Đã xóa note thất bại", Toast.LENGTH_SHORT).show();
        }
    }
    //Thêm mới
    private void addNew() {
        Intent intent = new Intent(ViewCheckListNoteActivity.this, EditCheckListNoteActivity.class);
        startActivityForResult(intent, AppConfig.RequestCode.REQUEST_CODE_ADD_CHECKLIST_NOTE);
    }
    //Chỉnh sửa note
    private void editNote() {
        Intent intent = new Intent(ViewCheckListNoteActivity.this, EditCheckListNoteActivity.class);
        intent.putExtra("noteid", currentNote.getId());
        intent.putExtra("isdonelist", viewCheckListAdapter.getAllItemDone());
        startActivityForResult(intent, AppConfig.RequestCode.REQUEST_CODE_EDIT_CHECKLIST_NOTE);
    }
    //Hiện popup thông báo xóa
    private void showAlertDelete(String mess) {
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder
                .setMessage(mess)
                .setCancelable(false)
                .setPositiveButton("Không", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                })
                .setNegativeButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        deleteNote(currentNote);
                    }
                });

        alertDialogBuilder.show();
    }
    //lưu note
    private void onSave(Note note) {
        ((CheckListNote) note).setItemCheckLists(this.viewCheckListAdapter.data);
        if (this.viewCheckListAdapter.isAllDone()) {
            text_view_title_note.setText(textUtils.showTextFromHTML(textUtils.buildDeleteString(note.getTitle())));
            note.setComplete(true);
        } else {
            note.setComplete(false);
        }

        if (noteManager.updateNote(note) > 0) {

        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppConfig.ResultCode.RESULT_CODE_EDIT_OR_CREATE_NOTE) {

            currentNote = noteManager.findNoteById(data.getStringExtra("noteid"));
            Log.d("NOTEID", currentNote.getTitle());
            showData();
        }
    }
    //Save note khi nhấn phím back
    @Override
    public void onBackPressed() {
        if (isNeedSaved) {
            onSave(currentNote);
        }
        setResult(AppConfig.ResultCode.RESULT_CODE_VIEW_NOTE, new Intent().putExtra("reload", true));

        Intent intent = new Intent(WidgetMySticker.ACTION_UPDATE);
        intent.putExtra("noteid", currentNote.getId());
        getApplicationContext().sendBroadcast(intent);

        finish();
    }

    class OnClickitemCheckList implements IClickOnCheckListItem {
        @Override
        public void onClickParent(int position) {

            if (viewCheckListAdapter.data.get(position).isDone()) {
                viewCheckListAdapter.data.get(position).setDone(false);
            } else {
                viewCheckListAdapter.data.get(position).setDone(true);
            }
            if (viewCheckListAdapter.isAllDone()) {
                text_view_title_note.setText(textUtils.showTextFromHTML(textUtils.buildDeleteString(currentNote.getTitle())));

            } else {
                text_view_title_note.setText(currentNote.getTitle());
            }
            viewCheckListAdapter.notifyDataSetChanged();

        }

        @Override
        public void onClickEdit(final int position) {
            AlertDialog.Builder builder = new AlertDialog.Builder(ViewCheckListNoteActivity.this);

            builder.setTitle(viewCheckListAdapter.data.get(position).getContent())


                    .setItems(new String[]{"Chỉnh sửa"}, new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            if (which == 0) {
                                Intent intent = new Intent(ViewCheckListNoteActivity.this, EditCheckListNoteActivity.class);
                                intent.putExtra("noteid", currentNote.getId());
                                intent.putExtra("position", position + "");
                                intent.putExtra("isdonelist", viewCheckListAdapter.getAllItemDone());
                                startActivityForResult(intent, AppConfig.ResultCode.RESULT_CODE_EDIT_ITEM_CHECKLIST);
                            }
                        }
                    });
            builder.show();
        }
    }

    class OnClickMenuToolItem implements IMenuToolClick {
        @Override
        public void onCheckClick() {
            checkNote();
        }

        @Override
        public void onAddNewClick() {
            addNew();
        }

        @Override
        public void onEditClick() {
            editNote();
        }

        @Override
        public void onDeleteClick() {
            showAlertDelete("Bạn có thật sự muốn xóa note này ?");
        }

        @Override
        public void onChangeColor() {

        }

        @Override
        public void onSave() {

        }

        @Override
        public void onReset() {

        }
    }
}
