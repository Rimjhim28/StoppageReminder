package com.example.android.stoppagereminder;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.firebase.ui.auth.AuthUI;

public class DomesticViolenceMainActivity extends AppCompatActivity {

    Button btnBackupMail, btnEmergencyMail, btnLaws;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_domestic_violence);

        btnBackupMail = (Button) findViewById(R.id.btn_setup_backup_mail);
        btnEmergencyMail = (Button) findViewById(R.id.btn_setup_emergence_mail);
        btnLaws = (Button) findViewById(R.id.btn_laws);

        btnEmergencyMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DomesticViolenceMainActivity.this,DomesticViolenceEmergencySender.class);
                startActivity(intent);
            }
        });

        btnLaws.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DomesticViolenceMainActivity.this,DomesticViolenceGovernmentActivity.class);
                startActivity(intent);
            }
        });

        btnBackupMail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DomesticViolenceMainActivity.this,DomesticViolenceBackupActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onBackPressed() {
        new AlertDialog.Builder(this)
                .setIcon(android.R.drawable.ic_dialog_alert)
                .setTitle("Alert")
                .setMessage("Pressing back will automatically log you out!")
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        AuthUI.getInstance().signOut(DomesticViolenceMainActivity.this);
                        Intent intent = new Intent(DomesticViolenceMainActivity.this,EnterLocationActivity.class);
                        startActivity(intent);
                    }
                })
                .setNegativeButton("Dismiss", null)
                .show();
    }
}
