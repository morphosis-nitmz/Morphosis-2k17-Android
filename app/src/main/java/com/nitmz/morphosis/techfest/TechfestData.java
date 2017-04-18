package com.nitmz.morphosis.techfest;


public class TechfestData {

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
}
