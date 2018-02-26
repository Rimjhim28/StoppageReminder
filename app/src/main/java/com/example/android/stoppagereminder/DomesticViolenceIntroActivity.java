package com.example.android.stoppagereminder;

import android.content.Intent;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

/**
 * Created by HP on 24-01-2018.
 */

public class DomesticViolenceIntroActivity extends AppCompatActivity {

    Button btnSignUp;
    boolean hasPreviouslyStarted;
    PreferenceManager preferenceManager;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domestic_violence_intro);
        btnSignUp = (Button) findViewById(R.id.btn_sign_up);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(DomesticViolenceIntroActivity.this,SetupUserMail.class);
                startActivity(intent);
            }
        });
    }
}
