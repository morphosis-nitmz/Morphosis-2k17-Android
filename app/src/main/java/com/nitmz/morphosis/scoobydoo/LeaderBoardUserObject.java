package com.nitmz.morphosis.scoobydoo;

import android.support.annotation.NonNull;

import java.util.Comparator;

public class LeaderBoardUserObject implements Comparable<LeaderBoardUserObject> {
    ;
    private String purl;
    private String username;
    private int score;
    private int crank;


    @Override
    public int compareTo(@NonNull LeaderBoardUserObject o) {
        return 0;
    }

    public static final Comparator<LeaderBoardUserObject> first = new Comparator<LeaderBoardUserObject>(){

        @Override
        public int compare(LeaderBoardUserObject o1, LeaderBoardUserObject o2) {
            return o1.getScore()- o2.getScore();
        }

    };

    public static final Comparator<LeaderBoardUserObject> second = new Comparator<LeaderBoardUserObject>(){

        @Override
        public int compare(LeaderBoardUserObject o1, LeaderBoardUserObject o2) {
            return o2.getCrank()- o1.getCrank();
        }

    };


    String getPurl() {
        return purl;
    }

    String getUsername() {
        return username;
    }


    void setPurl(String purl) {
        this.purl = purl;
    }

    void setUsername(String username) {
        this.username = username;
    }

    public int getScore() {
        return score;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getCrank() {
        return crank;
    }

    public void setCrank(int crank) {
        this.crank = crank;
    }
}


