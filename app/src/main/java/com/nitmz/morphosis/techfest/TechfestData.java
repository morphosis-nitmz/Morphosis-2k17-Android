package com.nitmz.morphosis.techfest;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TechfestData {

    List<String> mEvents = new ArrayList<>(Arrays.asList(
            "Maze Solver", // 0
            "Robo Race", // 1
            "Robo War", // 2
            "Stock Bridge", // 3
            "Quiz", // 4
            "Arcadia", // 5
            "Cube Fever", // 6
            "Scooby Dooby Doo", // 7
            "Power of Speech", // 8
            "Code Warrior", // 9
            "Cypher", // 10
            "Tech Talk", // 11
            "Science Expo", // 12
            "Abhyudaya", // 13
            "Manthan")); // 14

    int[] rulesPages = {
            2, 2, 3, 1, 1, 5, 1, 1, 1, 1, 1, 1, 1, 1, 1
    };


    int[] aboutPage = {
            1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1
    };

    public int getAboutEvent(int position) {
        return aboutPage[position];
    }

    public int getRulesPage(int position) {
        return  rulesPages[position];
    }

    public String getEventName(int position) {
        return mEvents.get(position);
    }
}
