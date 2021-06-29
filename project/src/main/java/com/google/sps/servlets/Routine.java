package com.google.sps.servlets;

import java.util.ArrayList;

public class Routine {

    String title, description;
    ArrayList<String> moveSet;
    int sets;

    public Routine(String routineTitle, String routineDescription, ArrayList<String> routineMoveSet, int routineSets) {
        title = routineTitle;
        description = routineDescription;
        moveSet = routineMoveSet;
        sets = routineSets;
    }

    public static void main(String[] args) {
        System.out.println("Routine Class");
    }
}