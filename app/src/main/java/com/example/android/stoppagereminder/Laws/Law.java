package com.example.android.stoppagereminder.Laws;

import com.firebase.ui.auth.User;

import java.util.ArrayList;

/**
 * Created by HP on 24-01-2018.
 */

public class Law {

    //declare private data instead of public to ensure the privacy of data field of each class
    private String chapterNumber;
    private String sectionNumber;
    private String sectionTiltle;

    public Law(String sectionTiltle, String sectionNumber,String chapterNumber) {
        this.sectionTiltle = sectionTiltle;
        this.sectionNumber = sectionNumber;
        this.chapterNumber = chapterNumber;
    }

    //retrieve user's name
    public String getsectionTiltle(){
        return sectionTiltle;
    }

    //retrieve users' hometown
    public String getsectionNumber(){
        return sectionNumber;
    }

    public String getChapterNumber(){
        return chapterNumber;
    }

    public static ArrayList<Law> getLaws() {
        ArrayList<Law> laws = new ArrayList<Law>();

        laws.add(new Law("Preliminary","Section 1", "Chapter 1"));

        laws.add(new Law("Domestic Violence","Section 2", "Chapter 2"));

        laws.add(new Law("Powers And Services","Section 3", "Chapter 3"));

        laws.add(new Law("Procedure","Section 4", "Chapter 4"));

        laws.add(new Law("Miscellaneous","Section 5", "Chapter 5"));

        return laws;
    }
}
