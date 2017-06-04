package com.infamous.fdsa.mysticker.common;

/**
 * Created by apple on 5/22/17.
 */

public class AppConfig {

    public static final int LIMIT_CHARACTER_TITLE_SHOW_MAIN_ACTIVITY = 20;
    public static final int LIMIT_CHARACTER_TITLE_SHOW_WIDGET = 10;

    public class RequestCode {
        public static final int REQUEST_CODE_OPEN_VIEW_NORMAL = 1;
        public static final int REQUEST_CODE_OPEN_VIEW_CHECKLIST = 2;
        public static final int REQUEST_CODE_EDIT_NORMAL_NOTE = 3;
        public static final int REQUEST_CODE_EDIT_CHECKLIST_NOTE = 4;
        public static final int REQUEST_CODE_ADD_NORMAL_NOTE = 5;
        public static final int REQUEST_CODE_ADD_CHECKLIST_NOTE = 6;
        public static final int REQUEST_CODE_ADD_NORMAL_NOTE_FROM_WIDGET = 7;
        public static final int REQUEST_CODE_ADD_CHECKLIST_NOTE_FROM_WIDGET = 8;
    }

    public class ResultCode {
        public static final int RESULT_CODE_DELETE_NOTE = 10;
        public static final int RESULT_CODE_VIEW_NOTE = 20;
        public static final int RESULT_CODE_EDIT_OR_CREATE_NOTE = 30;
        public static final int RESULT_CODE_EDIT_ITEM_CHECKLIST = 40;
    }


}
