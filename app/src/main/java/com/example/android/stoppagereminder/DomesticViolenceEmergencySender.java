package com.example.android.stoppagereminder;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;

import static com.example.android.stoppagereminder.Utils.emergencyMail1;
import static com.example.android.stoppagereminder.Utils.emergencyMail2;

public class DomesticViolenceEmergencySender extends AppCompatActivity {

    Context mContext;
    DbHelper mDbHelper;
    SQLiteDatabase database;
    //ArrayList<String> mails;
    private String userName;
    private String userPassword;
    String emergencyMail1 = null,emergencyMail2 = null,emergencyMail3 = null;
    Button btnSend;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domestic_violence_emergency_sender);
        mDbHelper = new DbHelper(getApplicationContext());
        btnSend = (Button) findViewById(R.id.btn_send);

        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getEmergencyMails();
                sendMail();
            }
        });
    }

    public void getEmergencyMails(){
        database = mDbHelper.getReadableDatabase();
        Cursor cursor = database.query(DbHelper.TABLE_NAME,null,null,null,null,null,null,null);
        if(cursor.moveToFirst()){
            do {
                userName = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_NAME));
                userPassword = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_USER_PASSWORD));
                emergencyMail1 = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_EMERGENCY_MAIL_1));
                emergencyMail2 = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_EMERGENCY_MAIL_1));
                emergencyMail3 = cursor.getString(cursor.getColumnIndex(DbHelper.COLUMN_EMERGENCY_MAIL_1));
            }while (cursor.moveToNext());
        }
        cursor.close();
        //mails.add(emergencyMail1);
        //mails.add(emergencyMail2);
        //mails.add(emergencyMail3);
    }

    public void sendMail(){

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("SendMail: ", "Inside New thread");
                try {
                    GMailSender sender = new GMailSender(userName,
                            userPassword);
                    sender.sendMail("Emeregency Mail", "I need immediate help",
                            userName, emergencyMail1);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("SendMail: ", "Inside New thread");
                try {
                    GMailSender sender = new GMailSender(userName,
                            userPassword);
                    sender.sendMail("Emergency Mail", "I need immediate help",
                            userName, emergencyMail2);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Log.i("SendMail: ", "Inside New thread");
                try {
                    GMailSender sender = new GMailSender(userName,
                            userPassword);
                    sender.sendMail("Emeregency Mail", "I need immediate help",
                            userName, emergencyMail3);
                } catch (Exception e) {
                    Log.e("SendMail", e.getMessage(), e);
                }
            }

        }).start();
    }
}
