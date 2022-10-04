package com.example.week05_1;

import java.util.ArrayList;

public class Word {
    public ArrayList<String> badWords = new ArrayList<String>();
    public ArrayList<String> goodWords = new ArrayList<String>();
    public Word(){
        goodWords.add("happy");
        goodWords.add("enjoy");
        goodWords.add("like");
        badWords.add("fuck");
        badWords.add("olo");
    }
}
