package com.infamous.fdsa.mysticker.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.AppConfig;
import com.infamous.fdsa.mysticker.common.TextUtils;
import com.infamous.fdsa.mysticker.common.ba.NoteManager;
import com.infamous.fdsa.mysticker.common.model.note.NormalNote;
import com.infamous.fdsa.mysticker.common.model.note.Note;
import com.infamous.fdsa.mysticker.common.myenum.MyColor;
import com.infamous.fdsa.mysticker.common.myinterface.IMenuToolClick;
import com.infamous.fdsa.mysticker.ui.popup.addnewpopup.AddNewNotePopup;
import com.infamous.fdsa.mysticker.ui.popup.menupopup.MenuPopup;
import com.infamous.fdsa.mysticker.ui.views.LineEditText;
import com.infamous.fdsa.mysticker.ui.widget.WidgetMySticker;


/**
 * Created by apple on 5/23/17.
 */

public class ViewNormalNoteActivity extends BaseSlideActivity implements View.OnClickListener {
    NoteManager noteManager;
    Note noteItem;
    LinearLayout linear_layout_top_bar_view_note;
    TextView text_view_title_note;
    ImageView image_view_edit_note;
    ImageView image_view_tool;
    LinearLayout linear_layout_date_create;
    TextView text_view_date_create;
    LineEditText main_edit_text;
    MenuPopup menuPopup;
    AddNewNotePopup addNewNotePopup;
    TextUtils textUtils;

    public ViewNormalNoteActivity() {
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_normal_note);

        noteManager = NoteManager.getInstance(this.getApplicationContext());
        String noteID = getIntent().getStringExtra("noteid");
        noteItem = noteManager.findNoteById(noteID);

        textUtils = TextUtils.getInstance(this.getApplicationContext());

        initView();
        showData();
    }

    private void initView() {
        linear_layout_top_bar_view_note = (LinearLayout) findViewById(R.id.linear_layout_top_bar_view_note);
        text_view_title_note = (TextView) findViewById(R.id.text_view_title_note);
        image_view_edit_note = (ImageView) findViewById(R.id.image_view_edit_note);
        image_view_tool = (ImageView) findViewById(R.id.iamge_view_tool);

        linear_layout_date_create = (LinearLayout) findViewById(R.id.linear_layout_date_create);
        text_view_date_create = (TextView) findViewById(R.id.text_view_date_create);
        main_edit_text = (LineEditText) findViewById(R.id.main_edit_text);
        main_edit_text.setFocusable(false);

        menuPopup = new MenuPopup(this, image_view_tool, MenuPopup.MODE_VIEW, new CallBackWhenClickMenuTool());
        menuPopup.setChecked(noteItem.isComplete());
        addNewNotePopup = new AddNewNotePopup(this.getApplicationContext());

        image_view_edit_note.setOnClickListener(this);
        image_view_tool.setOnClickListener(this);

    }

    private void showData() {
        String title = noteItem.getTitle();
        String dateCreate = noteItem.getDateCreate();
        String content = ((NormalNote) noteItem).getContent();
        MyColor myColor = MyColor.getColorByHex(noteItem.getColor());

        if (noteItem.isComplete()) {
            text_view_title_note.setText(textUtils.showTextFromHTML(textUtils.buildDeleteString(noteItem.getTitle())));
        } else {
            text_view_title_note.setText(noteItem.getTitle());
        }
        text_view_date_create.setText(textUtils.formatStringDate(dateCreate));
        main_edit_text.setText(textUtils.showTextFromHTML(content));

        linear_layout_top_bar_view_note.setBackgroundColor(Color.parseColor(myColor.getColor2()));
        linear_layout_date_create.setBackgroundColor(Color.parseColor(myColor.getColor3()));
        main_edit_text.setColor(myColor.getColor3(), myColor.getColor4());
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (resultCode == AppConfig.ResultCode.RESULT_CODE_EDIT_OR_CREATE_NOTE) {
            noteItem = noteManager.findNoteById(data.getStringExtra("noteid"));
            showData();
        }
    }

    private void checkNote() {
        if (menuPopup.isChecked()) {
            menuPopup.setChecked(false);
            noteItem.setComplete(false);
        } else {
            menuPopup.setChecked(true);
            noteItem.setComplete(true);
        }
        if (noteItem.isComplete()) {
            text_view_title_note.setText(textUtils.showTextFromHTML(textUtils.buildDeleteString(noteItem.getTitle())));
        } else {
            text_view_title_note.setText(noteItem.getTitle());
        }
        noteItem.setLastMotify(textUtils.getCurrentTime());
        if (noteManager.updateNote(noteItem) > 0) {
            Intent intent = new Intent(WidgetMySticker.ACTION_UPDATE);
            intent.putExtra("noteid", noteItem.getId());
            getApplicationContext().sendBroadcast(intent);
        }

    }

    private void deleteNote(Note item) {
        if (noteManager.deleteNote(item) > 0) {
            Toast.makeText(this, "Đã xóa note thành công", Toast.LENGTH_SHORT).show();
            setResult(AppConfig.ResultCode.RESULT_CODE_DELETE_NOTE, new Intent().putExtra("success", true));
            finish();
        } else {
            Toast.makeText(this, "Đã xóa note thất bại", Toast.LENGTH_SHORT).show();
        }
    }

    private void addNew() {
        Intent intent = new Intent(ViewNormalNoteActivity.this, EditNormalNoteActivity.class);
        startActivityForResult(intent,AppConfig.RequestCode.REQUEST_CODE_ADD_NORMAL_NOTE);
    }

    private void editNote() {
        Intent intent = new Intent(ViewNormalNoteActivity.this, EditNormalNoteActivity.class);
        intent.putExtra("noteid", noteItem.getId());
        startActivityForResult(intent, AppConfig.RequestCode.REQUEST_CODE_EDIT_NORMAL_NOTE);
    }

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
                        deleteNote(noteItem);
                    }
                });

        alertDialogBuilder.show();
    }

    @Override
    public void onBackPressed() {
        setResult(AppConfig.ResultCode.RESULT_CODE_VIEW_NOTE, new Intent().putExtra("reload", true));
        finish();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_edit_note:
                editNote();
                break;
            case R.id.iamge_view_tool:
                menuPopup.show();
                break;
        }
    }

    class CallBackWhenClickMenuTool implements IMenuToolClick {
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
