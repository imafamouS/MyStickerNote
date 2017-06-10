package com.infamous.fdsa.mysticker.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.text.Html;
import android.text.Spannable;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.EditText;
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
import com.infamous.fdsa.mysticker.common.myenum.TypePopup;
import com.infamous.fdsa.mysticker.common.myinterface.ICallBackFromPopup;
import com.infamous.fdsa.mysticker.common.myinterface.IMenuToolClick;
import com.infamous.fdsa.mysticker.ui.popup.TextSizeDialog.TextSizePopup;
import com.infamous.fdsa.mysticker.ui.popup.menupopup.ChangeColorPopup;
import com.infamous.fdsa.mysticker.ui.popup.menupopup.MenuPopup;
import com.infamous.fdsa.mysticker.ui.views.LineEditText;
import com.infamous.fdsa.mysticker.ui.widget.WidgetMySticker;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Created by apple on 5/23/17.
 */

public class EditNormalNoteActivity extends BaseSlideActivity implements View.OnClickListener, ICallBackFromPopup {
    NoteManager noteManager;
    Note currentNote;
    LinearLayout linear_layout_top_bar_edit_note;
    EditText edit_text_title_note;
    ImageView image_view_change_color;
    ImageView image_view_save;
    ImageView image_view_edit_tool;
    LinearLayout linear_layout_tool_bar_note;
    LinearLayout linear_layout_bold;
    LinearLayout linear_layout_italic;
    LinearLayout linear_layout_size;
    TextView text_view_size;
    LinearLayout linear_layout_date_create;
    TextView text_view_date_create;
    LineEditText main_edit_text;
    MenuPopup menuPopup;
    TextUtils textUtils;
    ChangeColorPopup changeColorPopup;
    Map<String, Integer> isClickTool = new HashMap<>();
    MyColor mainColor;
    boolean isNeedSave = true;
    TextSizePopup textSizePopup;

    public EditNormalNoteActivity() {
        super();
    }

    private void initClickTool() {
        isClickTool.put("bold", 0);
        isClickTool.put("italic", 0);
        isClickTool.put("size", 13);
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_normal_note);
        initClickTool();

        initView();

        String noteID = getIntent().getStringExtra("noteid");

        noteManager = NoteManager.getInstance(this.getApplicationContext());
        textUtils = TextUtils.getInstance(this.getApplicationContext());

        if (noteID == null || noteID.equals("") || noteID.length() == 0) {
            showBlankData();
        } else {
            currentNote = noteManager.findNoteById(noteID);
            showDataNote();
        }
    }
    //Khởi tạo view
    private void initView() {
        linear_layout_top_bar_edit_note = (LinearLayout) findViewById(R.id.linear_layout_top_bar_edit_note);
        edit_text_title_note = (EditText) findViewById(R.id.edit_text_title_note);
        image_view_change_color = (ImageView) findViewById(R.id.image_view_change_color);
        image_view_save = (ImageView) findViewById(R.id.image_view_save);
        image_view_edit_tool = (ImageView) findViewById(R.id.image_view_edit_tool);


        linear_layout_tool_bar_note = (LinearLayout) findViewById(R.id.linear_layout_tool_bar_note);
        linear_layout_bold = (LinearLayout) findViewById(R.id.linear_layout_bold);
        linear_layout_italic = (LinearLayout) findViewById(R.id.linear_layout_italic);
        linear_layout_size = (LinearLayout) findViewById(R.id.linear_layout_size);
        text_view_size = (TextView) findViewById(R.id.text_view_size);

        linear_layout_date_create = (LinearLayout) findViewById(R.id.linear_layout_date_create);
        text_view_date_create = (TextView) findViewById(R.id.text_view_date_create);
        main_edit_text = (LineEditText) findViewById(R.id.main_edit_text);

        menuPopup = new MenuPopup(this, image_view_edit_tool, MenuPopup.MODE_EDIT, new CallBackWhenClickToolMenu());
        textSizePopup = new TextSizePopup();
        textSizePopup.setCallBackFromPopup(this);

        changeColorPopup = new ChangeColorPopup();
        changeColorPopup.setCallback(this);

        image_view_change_color.setOnClickListener(this);
        image_view_save.setOnClickListener(this);
        image_view_edit_tool.setOnClickListener(this);
        linear_layout_bold.setOnClickListener(this);
        linear_layout_size.setOnClickListener(this);
        linear_layout_italic.setOnClickListener(this);
    }
    //Hiện dữ liệu trống
    private void showBlankData() {
        MyColor myColor = MyColor.getColorByIndex(3);
        linear_layout_date_create.setBackgroundColor(Color.parseColor(myColor.getColor3()));

        main_edit_text.setColor(myColor.getColor3(), myColor.getColor4());

        text_view_date_create.setText(textUtils.getCurrentTime());
        mainColor = myColor;
    }
    //Hiện dữ liệu cua3 note
    private void showDataNote() {
        edit_text_title_note.setText(currentNote.getTitle());
        text_view_date_create.setText(textUtils.formatStringDate(currentNote.getDateCreate()));
        MyColor myColor1 = MyColor.getColorByHex(currentNote.getColor());
        main_edit_text.setText(textUtils.showTextFromHTML(((NormalNote) currentNote).getContent()));
        changeColorTheme(myColor1);
        mainColor = myColor1;
    }
    //Đổi màu note
    private void changeColorTheme(MyColor color) {
        linear_layout_date_create.setBackgroundColor(Color.parseColor(color.getColor3()));

        main_edit_text.setColor(color.getColor3(), color.getColor4());
    }

    @Override
    public void callBackFromPopup(TypePopup type, int position) {
        //Hàm xảy ra khi thực hiện xong việc chọn màu note
        if (type == TypePopup.POPUP_CHANGE_COLOR) {
            MyColor myColor = MyColor.getColorByIndex(position);
            changeColorTheme(myColor);
            changeColorPopup.dismiss();
            mainColor = myColor;
        } else if (type == TypePopup.POPUP_CHANGE_TEXT_SIZE) {
            Toast.makeText(this, position + "", Toast.LENGTH_SHORT).show();
        }
    }
    //Reset giao diện
    private void reset() {
        edit_text_title_note.setText("");
        changeColorTheme(MyColor.COLOR_3);
        main_edit_text.setText("");
    }
    //Nhấn Save
    private void onSave(Note note) {
        boolean flag = false;
        String actionWidget;
        if (note != null) {
            note.setTitle(!edit_text_title_note.getText().toString().equalsIgnoreCase("") ? edit_text_title_note.getText().toString() : "Untitled");
            note.setLastMotify(textUtils.getCurrentTime());
            note.setColor(mainColor.getColor());
            note.setComplete(false);
            ((NormalNote) note).setContent(Html.toHtml(main_edit_text.getText()).toString());
            if (noteManager.updateNote(note) > 0) {
                flag = true;
            }
            actionWidget = WidgetMySticker.ACTION_UPDATE;
        } else {
            note = Note.buildNote(Note.NORMAL);
            note.setId(UUID.randomUUID().toString());
            note.setTitle(!edit_text_title_note.getText().toString().equalsIgnoreCase("") ? edit_text_title_note.getText().toString() : "Untitled");
            note.setDateCreate(textUtils.getCurrentTime());
            note.setLastMotify("");
            note.setColor(mainColor.getColor());
            note.setComplete(false);
            ((NormalNote) note).setContent(Html.toHtml(main_edit_text.getText()).toString());
            if (noteManager.createNewNote(note) > 0) {
                flag = true;

            }
            actionWidget = WidgetMySticker.ACTION_CREATE_NOTE;
        }
        if (flag) {
            isNeedSave = false;
            Intent intent1=new Intent();
            intent1.putExtra("reload", true);
            intent1.putExtra("noteid",note.getId());
            setResult(AppConfig.ResultCode.RESULT_CODE_EDIT_OR_CREATE_NOTE,intent1);
            setResult(AppConfig.ResultCode.RESULT_CODE_EDIT_OR_CREATE_NOTE, intent1);
            if (getIntent().getStringExtra("fromWidget") != null && getIntent().getStringExtra("fromWidget").equals("true")) {
                Intent intent = new Intent(actionWidget);
                getApplicationContext().sendBroadcast(intent);
            }

            finish();
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
        } else {
            isNeedSave = true;
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }
    }
    //Nhấn in đậm
    private void onBold() {
        int selectionStart = main_edit_text.getSelectionStart();
        int selectionEnd = main_edit_text.getSelectionEnd();

        if (selectionStart > selectionEnd) {
            int temp = selectionEnd;
            selectionEnd = selectionStart;
            selectionStart = temp;
        }


        if (selectionEnd > selectionStart) {

            Spannable str = main_edit_text.getText();
            boolean exists = false;
            StyleSpan[] styleSpans;
            styleSpans = str.getSpans(selectionStart, selectionEnd, StyleSpan.class);

            for (int i = 0; i < styleSpans.length; i++) {
                if (styleSpans[i].getStyle() == android.graphics.Typeface.BOLD) {
                    str.removeSpan(styleSpans[i]);
                    exists = true;
                }
            }
            if (!exists) {
                str.setSpan(new StyleSpan(android.graphics.Typeface.BOLD), selectionStart, selectionEnd,
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                //linear_layout_bold.setBackgroundResource(R.color.color_main_dark);
            } else {
                //linear_layout_bold.setBackgroundResource(R.drawable.border);
            }
            main_edit_text.setSelection(selectionStart, selectionEnd);
        } else {
        }
    }
    //In nghiêng
    private void onItalic() {
        int selectionStart = main_edit_text.getSelectionStart();
        int selectionEnd = main_edit_text.getSelectionEnd();

        if (selectionStart > selectionEnd) {
            int temp = selectionEnd;
            selectionEnd = selectionStart;
            selectionStart = temp;
        }

        if (selectionEnd > selectionStart) {

            Spannable str = main_edit_text.getText();
            boolean exists = false;
            StyleSpan[] styleSpans;
            styleSpans = str.getSpans(selectionStart, selectionEnd, StyleSpan.class);

            for (int i = 0; i < styleSpans.length; i++) {
                if (styleSpans[i].getStyle() == Typeface.ITALIC) {
                    str.removeSpan(styleSpans[i]);
                    exists = true;
                }
            }
            if (!exists) {
                str.setSpan(new StyleSpan(Typeface.ITALIC), selectionStart, selectionEnd,
                        Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
                //linear_layout_italic.setBackgroundResource(R.color.color_main_dark);
            } else {
               // linear_layout_italic.setBackgroundResource(R.drawable.border);

            }
            main_edit_text.setSelection(selectionStart, selectionEnd);
        } else {
        }
    }
    //Khi nhấn phím back
    @Override
    public void onBackPressed() {
        if (isNeedSave) {
            AlertDialog.Builder builder = new AlertDialog.Builder(this);
            builder.setTitle("Bạn có muốn lưu không ?")
                    .setNegativeButton("Không", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            finish();
                        }
                    })
                    .setPositiveButton("Có", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            onSave(currentNote);
                        }
                    });
            builder.show();
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_change_color:
                changeColorPopup.show(this.getSupportFragmentManager(), "tag");
                break;
            case R.id.image_view_save:
                onSave(currentNote);
                break;
            case R.id.image_view_edit_tool:
                menuPopup.show();
                break;
            case R.id.linear_layout_bold:
                onBold();
                break;
            case R.id.linear_layout_italic:
                onItalic();
                break;
            case R.id.linear_layout_size:
                textSizePopup.show(this.getSupportFragmentManager(), "aa");
                break;
            default:
                break;
        }
    }

    class CallBackWhenClickToolMenu implements IMenuToolClick {
        @Override
        public void onCheckClick() {

        }

        @Override
        public void onAddNewClick() {

        }

        @Override
        public void onEditClick() {

        }

        @Override
        public void onDeleteClick() {

        }

        @Override
        public void onChangeColor() {
            image_view_change_color.performClick();
        }

        //chọn save
        @Override
        public void onSave() {
            EditNormalNoteActivity.this.onSave(currentNote);

        }
        //Chọn reset
        @Override
        public void onReset() {

            AlertDialog.Builder builder = new AlertDialog.Builder(EditNormalNoteActivity.this);

            builder.setTitle("Làm mới bạn sẽ bị xóa hết dữ liệu cũ.Bạn có muốn tiếp tục không ?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditNormalNoteActivity.this.reset();
                        }
                    })
                    .setNegativeButton("Hủy", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    });
            builder.show();
        }
    }


}

