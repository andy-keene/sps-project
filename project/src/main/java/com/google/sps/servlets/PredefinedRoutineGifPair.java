package com.google.sps.servlets;

import java.util.ArrayList;

public class PredefinedRoutineGifPair {

    Routine routineObj;
    ArrayList<String> gifs;

    public PredefinedRoutineGifPair(Routine obj, ArrayList<String> gifset) {
        routineObj = obj;
        gifs = gifset;
    }

    public PredefinedRoutineGifPair() {
        ArrayList<String> empty = new ArrayList<String>();
        routineObj = new Routine("", "", "", empty, 0);
        gifs = empty;
    }

    public static void main(String[] args) {
        System.out.println("Routine-Gif Pair");
    }
}