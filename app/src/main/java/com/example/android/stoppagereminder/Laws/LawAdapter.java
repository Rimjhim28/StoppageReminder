package com.example.android.stoppagereminder.Laws;

import android.content.Context;
import android.support.annotation.LayoutRes;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.android.stoppagereminder.R;
import com.firebase.ui.auth.User;

import java.util.ArrayList;

/**
 * Created by HP on 24-01-2018.
 */

public class LawAdapter extends ArrayAdapter<Law> {

    String chapter[] = {"Chapter 1", "Chapter 2", "Chapter 3", "Chapter 4", "Chapter 5"};
    String section[] = {"Section 1", "Section 2", "Section 3", "Section 4", "Section 5"};
    String sectionTitle[] ={"Prliminary", "Domestic Violence", "Powers And Duties","Procedure","Miscillaneous"};

    public LawAdapter(Context context, ArrayList<Law> laws) {
        super(context,0,laws);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        if(convertView == null){
            convertView = LayoutInflater.from(getContext()).inflate(R.layout.list_item_law, parent, false);
        }

        Law law = getItem(position);

        TextView txtChapterNAme, txtSectionNumber, txtSectionTitle;

        txtChapterNAme = (TextView) convertView.findViewById(R.id.txt_chapter_name);
        txtSectionNumber = (TextView) convertView.findViewById(R.id.txt_section_number);
        txtSectionTitle = (TextView) convertView.findViewById(R.id.txt_section_title);

        txtSectionTitle.setText(law.getsectionTiltle());
        txtSectionNumber.setText(law.getsectionNumber());
        txtChapterNAme.setText(law.getChapterNumber());

        return convertView;
    }

    


}
