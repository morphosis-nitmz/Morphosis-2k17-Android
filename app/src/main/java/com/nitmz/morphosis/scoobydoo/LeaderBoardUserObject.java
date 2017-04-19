package com.nitmz.morphosis.scoobydoo;

public class LeaderBoardUserObject {;
    private String purl;
    private String username;
    private String score;
    private String crank;

    public String getCrank() {
        return crank;
    }

    public void setCrank(String crank) {
        this.crank = crank;
    }

    String getPurl() {
        return purl;
    }

    String getUsername() {
        return username;
    }

    String getScore() {
        return score;
    }

    void setPurl(String purl) {
        this.purl = purl;
    }

    void setUsername(String username) {
        this.username = username;
    }

    void setScore(String score) {
        this.score = score;
    }
}
