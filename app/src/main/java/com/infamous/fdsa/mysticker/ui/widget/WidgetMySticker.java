package com.infamous.fdsa.mysticker.ui.widget;

import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.appwidget.AppWidgetProvider;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.text.Spanned;
import android.widget.RemoteViews;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.TextUtils;
import com.infamous.fdsa.mysticker.common.ba.NoteManager;
import com.infamous.fdsa.mysticker.common.model.note.CheckListNote;
import com.infamous.fdsa.mysticker.common.model.note.ItemCheckList;
import com.infamous.fdsa.mysticker.common.model.note.NormalNote;
import com.infamous.fdsa.mysticker.common.model.note.Note;
import com.infamous.fdsa.mysticker.common.model.note.WidgetNote;
import com.infamous.fdsa.mysticker.common.myenum.MyColor;
import com.infamous.fdsa.mysticker.ui.activity.ViewCheckListNoteActivity;
import com.infamous.fdsa.mysticker.ui.activity.ViewNormalNoteActivity;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * Implementation of App Widget functionality.
 */
public class WidgetMySticker extends AppWidgetProvider {

    public static final String ACTION_WIDGET_CLICKED = "com.infamous.fdsa.ui.widget.WidgetMySticker.ACTION_WIDGET_CLICKED_";
    public static final String ACTION_UPDATE = "com.infamous.fdsa.ui.widget.WidgetMySticker.ACTION_UPDATE";
    public static final String ACTION_CREATE_NOTE = "com.infamous.fdsa.ui.widget.WidgetMySticker.ACTION_CREATE_NOTE";


    NoteManager noteManager;

    public static void updateAppWidget(Context context, AppWidgetManager appWidgetManager,
                                       int appWidgetId, Note note) {

        RemoteViews views = createRemoteView(context, note, appWidgetId);

        appWidgetManager.updateAppWidget(appWidgetId, views);
    }

    static private RemoteViews createRemoteView(Context context, Note note, int appWidgetId) {
        // Construct the RemoteViews object
        TextUtils textUtils = TextUtils.getInstance(context);
        RemoteViews views = new RemoteViews(context.getPackageName(), R.layout.layout_widget_2x2);


        Spanned title = note.isComplete() ? textUtils.showTextFromHTML(textUtils.buildDeleteString(note.getTitle())) :
                textUtils.showTextFromHTML(note.getTitle());

        Spanned content = null;
        if (note.getType() == Note.NORMAL) {
            content = textUtils.showTextFromHTML(((NormalNote) note).getContent());
        } else {
            ArrayList<ItemCheckList> itemCheckLists = ((CheckListNote) note).getItemCheckLists();
            content = textUtils.showTextFromHTML(textUtils.buildLibraryStringForCheckList(itemCheckLists));
        }

        MyColor myColor = MyColor.getColorByHex(note.getColor());

        views.setTextViewText(R.id.text_view_widget_title, title);
        views.setTextViewText(R.id.text_view_widget_content, content);
        views.setInt(R.id.linear_layout_widget_title, "setBackgroundColor", Color.parseColor(myColor.getColor2()));
        views.setInt(R.id.linear_layout_widget_content, "setBackgroundColor", Color.parseColor(myColor.getColor3()));
        Intent intent = null;
        if (note.getType() == Note.NORMAL) {
            intent = new Intent(context, ViewNormalNoteActivity.class);
        } else {
            intent = new Intent(context, ViewCheckListNoteActivity.class);
        }
        intent.putExtra("noteid", note.getId());
        intent.putExtra("fromWidget", "true");

        //  intent.setAction(ACTION_WIDGET_CLICKED+String.valueOf(appWidgetId));
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);

        views.setOnClickPendingIntent(R.id.layout_parent, pendingIntent);
        return views;
    }

    @Override
    public void onUpdate(Context context, AppWidgetManager appWidgetManager, int[] appWidgetIds) {
        noteManager = NoteManager.getInstance(context);
        HashMap<String, WidgetNote> widgetNotesHashmap = noteManager.getHashMapWidgetNote();
        for (int appWidgetId : appWidgetIds) {

            String noteid = widgetNotesHashmap.get(appWidgetId + "") != null ? widgetNotesHashmap.get(appWidgetId + "").getNoteID() : null;
            if (noteid == null) {
                return;
            }
            updateAppWidget(context, appWidgetManager, appWidgetId, noteManager.findNoteById(noteid));
        }
    }

    @Override
    public void onReceive(Context context, Intent intent) {
//        super.onReceive(context, intent);

        AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(context);
        ComponentName widgetComponent = new ComponentName(context, WidgetMySticker.class);
        int[] widgetIds = appWidgetManager.getAppWidgetIds(widgetComponent);

        if (intent.getAction().equals(ACTION_UPDATE)) {
            onUpdate(context, appWidgetManager, widgetIds);
        } else if (intent.getAction().startsWith(ACTION_WIDGET_CLICKED)) {

        }
    }

    @Override
    public void onDeleted(Context context, int[] appWidgetIds) {
        super.onDeleted(context, appWidgetIds);
        final int N = appWidgetIds.length;
        noteManager = NoteManager.getInstance(context);
        for (int i = 0; i < N; i++) {
            int appWidgetId = appWidgetIds[i];
            noteManager.deleteWidgetNote(noteManager.findWidgetNoteByWidgetId(appWidgetId + ""));
        }
    }

    @Override
    public void onEnabled(Context context) {
        // Enter relevant functionality for when the first widget is created
    }

    @Override
    public void onDisabled(Context context) {
        // Enter relevant functionality for when the last widget is disabled
    }
}

