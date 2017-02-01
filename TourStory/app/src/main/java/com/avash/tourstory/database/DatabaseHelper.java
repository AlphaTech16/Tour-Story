package com.avash.tourstory.database;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DatabaseHelper extends SQLiteOpenHelper{

    private static final String DATABASE_NAME = "tour_story";
    private static final int DATABASE_VERSION = 1;

    private static final  String USER_INFO_TABLE_NAME = "user_info";
    private static final  String USER_INFO_COL_ID = "uid";
    private static final  String USER_INFO_COL_USER_ID = "user_id";
    private static final  String USER_INFO_COL_PASSWORD = "password";
    private static final  String USER_INFO_COL_SYS_DT_TM = "sysdt";

    private static final  String USER_PROFILE_TABLE_NAME = "user_profile";
    private static final  String USER_PROFILE_COL_ID = "pid";
    private static final  String USER_PROFILE_COL_USER_ID = "uid";
    private static final  String USER_PROFILE_COL_USER_NAME = "user_name";
    private static final  String USER_PROFILE_COL_EMAIL = "email";
    private static final  String USER_PROFILE_COL_MOBILE = "mobile";
    private static final  String USER_PROFILE_COL_ADDRESS = "address";

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
