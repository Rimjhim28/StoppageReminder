package com.example.android.stoppagereminder;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import static android.R.attr.inset;

public class SetupBackupEmergencyMail extends AppCompatActivity {

    EditText txtBackupMail, txtEmgMail1, txtEmgMail2, txtEmgMail3;
    String backupMail, mail1, mail2, mail3;
    Button btnFinish,btnSkip;
    DbHelper mDbHelper;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setup_backup_emergency_mail);

        txtBackupMail = (EditText) findViewById(R.id.txt_backup_mail);
        txtEmgMail1 = (EditText) findViewById(R.id.txt_emergency_mail_1);
        txtEmgMail2 = (EditText) findViewById(R.id.txt_emergency_mail_2);
        txtEmgMail3 = (EditText) findViewById(R.id.txt_emergency_mail_3);
        btnFinish = (Button) findViewById(R.id.btn_finish);
        btnSkip = (Button) findViewById( R.id.btn_skip);

        mDbHelper = new DbHelper(getApplicationContext());

        btnFinish.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                backupMail = txtBackupMail.getText().toString();
                mail1 = txtEmgMail1.getText().toString();
                mail2 = txtEmgMail2.getText().toString();
                mail3 = txtEmgMail3.getText().toString();
                Utils.backupMail = backupMail;
                Utils.emergencyMail1 = mail1;
                Utils.emergencyMail2 = mail2;
                Utils.emergencyMail2 = mail2;
                insertInDatabase();
                Intent intent = new Intent(SetupBackupEmergencyMail.this,DomesticViolenceMainActivity.class);
                startActivity(intent);
            }
        });

        btnSkip.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SetupBackupEmergencyMail.this,DomesticViolenceMainActivity.class);
                startActivity(intent);
            }
        });
    }

    private void insertInDatabase() {
        SQLiteDatabase database = mDbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(DbHelper.COLUMN_USER_NAME,Utils.userName);
        values.put(DbHelper.COLUMN_USER_PASSWORD,Utils.userPassword);
        values.put(DbHelper.COLUMN_BACKUP_MAIL,Utils.backupMail);
        values.put(DbHelper.COLUMN_EMERGENCY_MAIL_1,Utils.emergencyMail1);
        values.put(DbHelper.COLUMN_EMERGENCY_MAIL_2,Utils.emergencyMail2);
        values.put(DbHelper.COLUMN_EMERGENCY_MAIL_3,Utils.emergencyMail3);
        long id = database.insert(DbHelper.TABLE_NAME,null,values);
        Log.i("SetupUserMail: ","ID: "+id);
    }


}
