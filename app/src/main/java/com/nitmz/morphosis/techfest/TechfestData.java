package com.nitmz.morphosis.techfest;


public class TechfestData {

    int[] rulesPages = {
            2, 2, 3, 0, 1, 6, 0, 0, 0, 0, 0, 0, 1, 0, 0, 0, 0
    };


    int[] aboutPage = {
            0, 0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0, 1
    };

    public int getAboutEvent(int position) {
        return aboutPage[position];
    }

    public int getRulesPage(int position) {
        return  rulesPages[position];
    }
}
