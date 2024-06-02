package com.example.xodatabase;

import java.util.ArrayList;

public class GameStates {

    int _id;
    String _username;
    String _gameType;
    String _symbol;
    ArrayList<ArrayList<Integer>> _board;

    public GameStates() {

    }

    public GameStates(int id, String username, String _gameType, String symbol, ArrayList<ArrayList<Integer>> board) {
        this._id = id;
        this._username = username;
        this._gameType = _gameType;
        this._symbol = symbol;
        this._board = board;
    }

    public GameStates(String username, String _gameType, String symbol, ArrayList<ArrayList<Integer>> board) {
        this._username = username;
        this._gameType = _gameType;
        this._symbol = symbol;
        this._board = board;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public void set_gameType(String _gameType) {
        this._gameType = _gameType;
    }

    public void set_symbol(String _symbol) {
        this._symbol = _symbol;
    }

    public void set_board(ArrayList<ArrayList<Integer>> _board) {
        this._board = _board;
    }

    public int get_id() {
        return _id;
    }

    public String get_username() {
        return _username;
    }

    public String get_gameType() {
        return _gameType;
    }

    public String get_symbol() {
        return _symbol;
    }

    public ArrayList<ArrayList<Integer>> get_board() {
        return _board;
    }
}
