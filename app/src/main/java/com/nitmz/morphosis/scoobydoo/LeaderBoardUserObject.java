package com.nitmz.morphosis.scoobydoo;

import java.util.Comparator;

public class LeaderBoardUserObject implements Comparator<LeaderBoardUserObject> {
    ;
    private String purl;
    private String username;
    private int score;
    private int crank;

    @Override
    public int compare(LeaderBoardUserObject l, LeaderBoardUserObject r) {
        if(l.getScore()>r.getScore())
            return 1;
        else if(l.getScore()<r.getScore())
            return 0;
        else
        {
            if(l.getCrank()>r.getCrank())
                return 1;
            else
                return -1;
        }

    }


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


