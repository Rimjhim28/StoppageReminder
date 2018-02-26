package com.example.android.stoppagereminder;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by HP on 24-01-2018.
 */

public class DbHelper extends SQLiteOpenHelper {

    public static final String TAG = "DbHelper";
    public static final String DATABASE_NAME = "email.db";
    public static final int DATABASE_VERSION = 2;

    public static final String TABLE_NAME = "users";
    public static final String COLUMN_USER_ID = "_id";
    public static final String COLUMN_USER_NAME = "user_name";
    public static final String COLUMN_USER_PASSWORD = "password";
    public static final String COLUMN_BACKUP_MAIL = "back_up_mail";
    public static final String COLUMN_EMERGENCY_MAIL_1 = "emergency_mail_1";
    public static final String COLUMN_EMERGENCY_MAIL_2 = "emergency_mail_2";
    public static final String COLUMN_EMERGENCY_MAIL_3 = "emergency_mail_3";
    public static final String COLUMN_EMERGENCY_MAIL_4 = "emergency_mail_4";
    public static final String COLUMN_EMERGENCY_MAIL_5 = "emergency_mail_5";

    private static final String SQL_CREATE_TABLE = "CREATE TABLE " + TABLE_NAME + "("
            + COLUMN_USER_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, "
            + COLUMN_USER_NAME + " TEXT, "
            + COLUMN_BACKUP_MAIL + " TEXT, "
            + COLUMN_USER_PASSWORD + " TEXT, "
            + COLUMN_EMERGENCY_MAIL_1 + " TEXT, "
            + COLUMN_EMERGENCY_MAIL_2 + " TEXT, "
            + COLUMN_EMERGENCY_MAIL_3 + " TEXT, "
            + COLUMN_EMERGENCY_MAIL_4 + " TEXT, "
            + COLUMN_EMERGENCY_MAIL_5 + " REAL);";

    public DbHelper(Context context) {
        super(context,DATABASE_NAME,null,DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(SQL_CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS "+ TABLE_NAME);
        onCreate(db);
    }
}
