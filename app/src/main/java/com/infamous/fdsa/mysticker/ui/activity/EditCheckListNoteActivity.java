package com.infamous.fdsa.mysticker.ui.activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.AppConfig;
import com.infamous.fdsa.mysticker.common.TextUtils;
import com.infamous.fdsa.mysticker.common.ba.NoteManager;
import com.infamous.fdsa.mysticker.common.model.note.CheckListNote;
import com.infamous.fdsa.mysticker.common.model.note.ItemCheckList;
import com.infamous.fdsa.mysticker.common.model.note.Note;
import com.infamous.fdsa.mysticker.common.myenum.MyColor;
import com.infamous.fdsa.mysticker.common.myenum.TypePopup;
import com.infamous.fdsa.mysticker.common.myinterface.ICallBackFromPopup;
import com.infamous.fdsa.mysticker.common.myinterface.IClickOnCheckListItem;
import com.infamous.fdsa.mysticker.common.myinterface.IMenuToolClick;
import com.infamous.fdsa.mysticker.ui.adapter.AddCheckListAdapter;
import com.infamous.fdsa.mysticker.ui.adapter.NodataDataCheckListAdapter;
import com.infamous.fdsa.mysticker.ui.popup.menupopup.ChangeColorPopup;
import com.infamous.fdsa.mysticker.ui.popup.menupopup.MenuPopup;
import com.infamous.fdsa.mysticker.ui.widget.WidgetMySticker;

import java.util.ArrayList;
import java.util.UUID;

import static com.infamous.fdsa.mysticker.common.myenum.MyColor.COLOR_3;

/**
 * Created by apple on 5/23/17.
 */

public class EditCheckListNoteActivity extends BaseSlideActivity implements View.OnClickListener, ICallBackFromPopup {
    LinearLayout linear_layout_top_bar_edit_note;
    EditText edit_text_title_note;
    ImageView image_view_change_color;
    ImageView image_view_save;
    ImageView image_view_edit_tool;
    LinearLayout linear_layout_date_create;
    TextView text_view_date_create;
    ListView list_view;
    View headerView;
    View footerView;
    AddCheckListAdapter addCheckListAdapter;
    NodataDataCheckListAdapter nodata;
    NoteManager noteManager;
    Note currentNote;
    TextUtils textUtils;
    MenuPopup menuPopup;
    RelativeLayout header;
    RelativeLayout footer;
    ChangeColorPopup changeColorPopup;
    MyColor mainColor;
    boolean isNeedSave = true;
    boolean isAddNewNote = false;
    ArrayList<Integer> isDoneList=new ArrayList<>();

    public EditCheckListNoteActivity() {
        super();
    }

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_checklist_note);


        initView();

        String noteID = getIntent().getStringExtra("noteid");
        String contentID = getIntent().getStringExtra("position") + "";
        isDoneList=getIntent().getIntegerArrayListExtra("isdonelist");
        noteManager = NoteManager.getInstance(this.getApplicationContext());
        textUtils = TextUtils.getInstance(getApplicationContext());

        if (noteID == null || noteID.equals("") || noteID.length() == 0) {
            isAddNewNote = true;
            currentNote = Note.buildNote(Note.CHECKLIST);
            currentNote.setId(UUID.randomUUID().toString());

            showDataBlank();
        } else {
            currentNote = noteManager.findNoteById(noteID);
            Log.d("2", currentNote.toString());
            showDatanote();


            if (contentID != null && !contentID.equals("") && !contentID.equals("null")) {
                editItemCheckListPopup(addCheckListAdapter.data.get(Integer.parseInt(contentID)).getContent(), Integer.parseInt(contentID));
            }


        }
        if(isDoneList!=null && isDoneList.size()>=0){
            for(int i=0;i<isDoneList.size();i++){
                addCheckListAdapter.data.get(isDoneList.get(i)).setDone(true);
            }
        }

    }

    private void initView() {

        linear_layout_top_bar_edit_note = (LinearLayout) findViewById(R.id.linear_layout_top_bar_edit_note);
        edit_text_title_note = (EditText) findViewById(R.id.edit_text_title_note);
        image_view_change_color = (ImageView) findViewById(R.id.image_view_change_color);
        image_view_save = (ImageView) findViewById(R.id.image_view_save);
        image_view_edit_tool = (ImageView) findViewById(R.id.image_view_edit_tool);

        linear_layout_date_create = (LinearLayout) findViewById(R.id.linear_layout_date_create);
        text_view_date_create = (TextView) findViewById(R.id.text_view_date_create);

        list_view = (ListView) findViewById(R.id.list_view);

        headerView = getLayoutInflater().inflate(R.layout.item_header_list_add_checklist, list_view, false);
        footerView = getLayoutInflater().inflate(R.layout.item_footer_list_add_checklist, list_view, false);

        header = (RelativeLayout) headerView.findViewById(R.id.relative_layout_add_item_header);
        footer = (RelativeLayout) footerView.findViewById(R.id.relative_layout_add_item_footer);

        list_view.addHeaderView(headerView);
        list_view.addFooterView(footerView);

        menuPopup = new MenuPopup(this, image_view_edit_tool, MenuPopup.MODE_EDIT, new CallBackWhenClickToolMenu());

        changeColorPopup = new ChangeColorPopup();
        changeColorPopup.setCallback(this);

        image_view_edit_tool.setOnClickListener(this);
        image_view_change_color.setOnClickListener(this);
        image_view_save.setOnClickListener(this);
        header.setOnClickListener(this);
        footer.setOnClickListener(this);

    }

    private void showDatanote() {
        addCheckListAdapter = new AddCheckListAdapter(this.getApplicationContext(), ((CheckListNote) currentNote).getItemCheckLists(), new OnItemclick());
        list_view.setAdapter(addCheckListAdapter);
        MyColor myColor = MyColor.getColorByHex(currentNote.getColor());
        linear_layout_date_create.setBackgroundColor(Color.parseColor(myColor.getColor3()));
        edit_text_title_note.setText(currentNote.getTitle());
        text_view_date_create.setText(textUtils.formatStringDate(currentNote.getDateCreate()));

        list_view.setBackgroundColor(Color.parseColor(myColor.getColor3()));
        list_view.setDivider(new ColorDrawable(Color.parseColor(myColor.getColor4())));
        list_view.setDividerHeight(5);

        mainColor = myColor;

    }

    private void showDataBlank() {
        text_view_date_create.setText(textUtils.getCurrentTime());
        nodata = new NodataDataCheckListAdapter(this.getApplicationContext());
        MyColor myColor = MyColor.getColorByIndex(3);
        linear_layout_date_create.setBackgroundColor(Color.parseColor(myColor.getColor3()));
        list_view.setBackgroundColor(Color.parseColor(myColor.getColor3()));
        list_view.setDivider(new ColorDrawable(Color.parseColor(myColor.getColor4())));
        list_view.setDividerHeight(5);
        addCheckListAdapter = new AddCheckListAdapter(this.getApplicationContext(), ((CheckListNote) currentNote).getItemCheckLists(), new OnItemclick());
        list_view.setAdapter(addCheckListAdapter);
        mainColor = myColor;
    }

    @Override
    public void callBackFromPopup(TypePopup type, int position) {
        if (type == TypePopup.POPUP_CHANGE_COLOR) {
            MyColor myColor = MyColor.getColorByIndex(position);
            changeColorTheme(myColor);

            changeColorPopup.dismiss();

            mainColor = myColor;
        }
    }

    private void changeColorTheme(MyColor color) {
        linear_layout_date_create.setBackgroundColor(Color.parseColor(color.getColor3()));

        list_view.setBackgroundColor(Color.parseColor(color.getColor3()));
        list_view.setDivider(new ColorDrawable(Color.parseColor(color.getColor4())));
        list_view.setDividerHeight(5);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_view_edit_tool:
                menuPopup.show();
                break;
            case R.id.image_view_change_color:
                changeColorPopup.show(this.getSupportFragmentManager(), "tag");
                break;
            case R.id.image_view_save:
                onSave(currentNote);
                break;
            case R.id.relative_layout_add_item_header:
                addFirst();
                break;
            case R.id.relative_layout_add_item_footer:
                addEnd();
                break;
        }
    }

    private void addFirst() {
        addCheckListPopup(true);
    }

    private void addEnd() {
        addCheckListPopup(false);
    }

    private void onReset() {
        edit_text_title_note.setText("");
        changeColorTheme(COLOR_3);
        mainColor = COLOR_3;
        addCheckListAdapter.data.clear();
        addCheckListAdapter.notifyDataSetChanged();

    }

    private void addCheckListPopup(final boolean isAddFirst) {
        final AlertDialog.Builder builder = new AlertDialog.Builder(EditCheckListNoteActivity.this);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_popup_add_checklist, null);
        final EditText editText = (EditText) v.findViewById(R.id.edit_text);
        builder.setTitle("Thêm mới");
        builder.setView(v);
        builder
                .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();

                        if (editText.getText().length() <= 0) {
                            return;
                        }
                        if (isAddFirst) {
                            addCheckListAdapter.data.add(0, new ItemCheckList(currentNote.getId(), editText.getText().toString(), false));
                        } else {
                            addCheckListAdapter.data.add(new ItemCheckList(currentNote.getId(), editText.getText().toString(), false));
                        }
                        addCheckListAdapter.notifyDataSetChanged();
                    }
                })
                .setNeutralButton("Tiếp", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        addCheckListAdapter.addItem(new ItemCheckList(currentNote.getId(), editText.getText().toString(), false));
                        addCheckListAdapter.notifyDataSetChanged();
                        dialog.dismiss();
                        if (isAddFirst) {
                            header.performClick();
                        } else {
                            footer.performClick();
                        }

                    }
                });
        builder.show();
    }

    private void editItemCheckListPopup(String content, final int position) {
        AlertDialog.Builder builder = new AlertDialog.Builder(EditCheckListNoteActivity.this);
        View v = LayoutInflater.from(getApplicationContext()).inflate(R.layout.layout_popup_add_checklist, null);
        final EditText editText = (EditText) v.findViewById(R.id.edit_text);
        editText.setText(content);
        builder.setTitle("Chỉnh sửa");
        builder.setView(v);
        builder
                .setNegativeButton("Đóng", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                        addCheckListAdapter.getItem(position).setContent(editText.getText().toString());
                        addCheckListAdapter.notifyDataSetChanged();
                    }
                });
        builder.show();
    }

    private void onSave(Note note) {
        boolean flag = false;
        if (!isAddNewNote) {
            note.setTitle(!edit_text_title_note.getText().toString().equalsIgnoreCase("") ? edit_text_title_note.getText().toString() : "Untitled");
            note.setLastMotify(textUtils.getCurrentTime());
            note.setColor(mainColor.getColor());
            note.setComplete(false);
            ((CheckListNote) note).setItemCheckLists(this.addCheckListAdapter.data);
            if (noteManager.updateNote(note) > 0) {

                flag = true;
            }
        } else {
            note.setTitle(!edit_text_title_note.getText().toString().equalsIgnoreCase("") ? edit_text_title_note.getText().toString() : "Untitled");
            note.setDateCreate(textUtils.getCurrentTime());
            note.setLastMotify(textUtils.getCurrentTime());
            note.setColor(mainColor.getColor());
            note.setComplete(false);
            ((CheckListNote) note).setItemCheckLists(this.addCheckListAdapter.data);
            if (noteManager.createNewNote(note) > 0) {
                flag = true;
            }
        }
        if (flag) {
            isNeedSave = false;
            Intent intent1=new Intent();
            intent1.putExtra("reload", true);
            intent1.putExtra("noteid",note.getId());
            setResult(AppConfig.ResultCode.RESULT_CODE_EDIT_OR_CREATE_NOTE,intent1);
            if (getIntent().getStringExtra("fromWidget") != null && getIntent().getStringExtra("fromWidget").equals("true")) {
                Intent intent = new Intent(WidgetMySticker.ACTION_UPDATE);
                getApplicationContext().sendBroadcast(intent);
            }
            finish();
            Toast.makeText(this, "Cập nhật thành công", Toast.LENGTH_SHORT).show();
        } else {
            isNeedSave = true;
            Toast.makeText(this, "Cập nhật thất bại", Toast.LENGTH_SHORT).show();
        }
    }

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

    class OnItemclick implements IClickOnCheckListItem {
        @Override
        public void onClickParent(int position) {
            editItemCheckListPopup(addCheckListAdapter.data.get(position).getContent(), position);
        }

        @Override
        public void onClickEdit(int position) {
            addCheckListAdapter.data.remove(position);
            addCheckListAdapter.notifyDataSetChanged();
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

        @Override
        public void onSave() {
            image_view_save.performClick();
        }

        @Override
        public void onReset() {
            AlertDialog.Builder builder = new AlertDialog.Builder(EditCheckListNoteActivity.this);

            builder.setTitle("Làm mới bạn sẽ bị xóa hết dữ liệu cũ.Bạn có muốn tiếp tục không ?")
                    .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            EditCheckListNoteActivity.this.onReset();
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
