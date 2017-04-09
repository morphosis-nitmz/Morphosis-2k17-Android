package com.nitmz.morphosis.scoobydoo;

public class Data {

    private String[][] answers = {
            {"", "", ""},
            {"asd", "pks", "14r"},
            {"jfk", "s4", ""}
    };

    boolean checkAnswer(String answer, int index) {
        for (int i = 0; i < 3; i++) {
            if (answers[index][i].equalsIgnoreCase(answer))
                return true;
            else if (answers[index][i].equals(""))
                break;
        }
        return false;
    }
}
