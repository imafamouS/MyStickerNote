package com.infamous.fdsa.mysticker.common;

import android.content.Context;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;

import com.infamous.fdsa.mysticker.R;
import com.infamous.fdsa.mysticker.common.model.note.ItemCheckList;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

/**
 * Created by apple on 5/22/17.
 */

public class TextUtils {

    protected static TextUtils ins;
    private Context context;

    private TextUtils(Context context) {
        this.context = context;
    }

    public synchronized static TextUtils getInstance(Context context) {
        if (ins == null) {
            return new TextUtils(context);
        }
        return ins;
    }

    public int compare2DateFromString(String date1, String date2) {
        Calendar calendar1 = null;
        Calendar calendar2 = null;
        try {
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
            calendar1 = Calendar.getInstance();
            calendar1.setTime(format.parse(date1));
            calendar2 = Calendar.getInstance();
            calendar2.setTime(format.parse(date2));


        } catch (ParseException e) {
            e.printStackTrace();
        }
        return calendar1.compareTo(calendar2);
    }

    public String formatStringDate(String date) {
        String result = "";
        try {
            Calendar calendar1;
            SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");

            calendar1 = Calendar.getInstance();
            calendar1.setTime(format.parse(date));

            Calendar current = Calendar.getInstance();
            String  minute=calendar1.get(Calendar.MINUTE)<10?"0"+calendar1.get(Calendar.MINUTE):calendar1.get(Calendar.MINUTE)+"";
            result += calendar1.get(Calendar.HOUR_OF_DAY) + ":" +  minute + " ";

            if (calendar1.get(Calendar.DAY_OF_MONTH) == current.get(Calendar.DAY_OF_MONTH) && calendar1.get(Calendar.YEAR) == current.get(Calendar.YEAR)) {
                result += context.getString(R.string.TEXT_TODAY) + " ";
            } else if (calendar1.get(Calendar.YEAR) == current.get(Calendar.YEAR)) {
                result += "Ngày " + calendar1.get(Calendar.DAY_OF_MONTH) + "/" + (calendar1.get(Calendar.MONTH) + 1) + " ";
            } else {
                result += "Ngày " + calendar1.get(Calendar.DAY_OF_MONTH) + "/" + (calendar1.get(Calendar.MONTH) + 1) + "/" + calendar1.get(Calendar.YEAR) + " ";
            }

        } catch (ParseException e) {
            e.printStackTrace();

        }
        return result;
    }

    public String getCurrentTime() {
        String result = "";
        Calendar calendar;

        calendar = Calendar.getInstance();
        result = calendar.get(Calendar.DAY_OF_MONTH) + "/" + (calendar.get(Calendar.MONTH) + 1)
                + "/" + calendar.get(Calendar.YEAR) + " " + calendar.get(Calendar.HOUR_OF_DAY) + ":" + calendar.get(Calendar.MINUTE) + ":" + calendar.get(Calendar.SECOND);
        return result;
    }

    public String formatTitleShowing(String title, int limitLength) {
        return title.trim().length() <= limitLength ? title.trim() : title.trim().substring(0, limitLength) + "...";
    }

    public String buildDelelteString(String input, int start, int end) {
        return "<del>" + input.substring(start, end) + "</del>";
    }

    public String buildDeleteString(String input) {
        return buildDelelteString(input, 0, input.length());
    }

    public String buildBoldString(String input, int start, int end) {
        return "<b>" + input.substring(start, end) + "</b>";
    }

    public String buildBoldString(String input) {
        return buildBoldString(input, 0, input.length());
    }

    public String buildFontColor(String input, String color) {
        return "<font color='" + color + "'>" + input + "</font>";
    }

    public Spanned showTextFromHTML(String html) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            return Html.fromHtml(html, Html.FROM_HTML_MODE_COMPACT);
        } else {
            return Html.fromHtml(html);
        }
    }

    public String buildLibraryStringForCheckList(ArrayList<ItemCheckList> itemCheckListArrayList) {


        String result = "<ul>";

        for (ItemCheckList i : itemCheckListArrayList) {
            if (i.isDone()) {
                result += "<li>" + this.buildDeleteString(i.getContent()) + "</li>";
            } else {
                result += "<li>" + i.getContent() + "</li>";
            }
        }

        result += "</ul>";

        return result;
    }


}

