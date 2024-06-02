package com.example.xodatabase;

import androidx.annotation.NonNull;

public class Results {

    int _id;
    String _userName;
    String _opponentName;
    String winOrLose;

    public Results() {

    }

    public Results(int id, String userName, String opponentName, String winOrLose) {
        this._id = id;
        this._userName = userName;
        this._opponentName = opponentName;
        this.winOrLose = winOrLose;
    }

    public Results(String userName, String opponentName, String winOrLose) {
        this._userName = userName;
        this._opponentName = opponentName;
        this.winOrLose = winOrLose;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_userName(String _userName) {
        this._userName = _userName;
    }

    public void set_opponentName(String _opponentName) {
        this._opponentName = _opponentName;
    }

    public void setWinOrLose(String winOrLose) {
        this.winOrLose = winOrLose;
    }

    public int get_id() {
        return _id;
    }

    public String get_userName() {
        return _userName;
    }

    public String get_opponentName() {
        return _opponentName;
    }

    public String getWinOrLose() {
        return winOrLose;
    }

    @NonNull
    @Override
    public String toString() {
        return this.get_opponentName() + ":" + this.getWinOrLose();
    }
}
