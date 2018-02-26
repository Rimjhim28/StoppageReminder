package com.example.android.stoppagereminder;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.example.android.stoppagereminder.Laws.Law;
import com.example.android.stoppagereminder.Laws.LawAdapter;

import java.util.ArrayList;

public class DomesticViolenceGovernmentActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_domestic_violence_government);
        populateLawsList();


    }

    private void populateLawsList() {
        // Construct the data source
        ArrayList<Law> arrayOfLaws = Law.getLaws();
        // Create the adapter to convert the array to views
        LawAdapter adapter = new LawAdapter(this, arrayOfLaws);
        // Attach the adapter to a ListView
        ListView listView = (ListView) findViewById(R.id.laws_lv);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
             Intent intent = new Intent(DomesticViolenceGovernmentActivity.this,DomesticViolenceLawDescriptionActivity.class);
                startActivity(intent);

            }
        });

    }


}
