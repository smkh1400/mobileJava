package com.example.xodatabase;

public class Users {

    int _id;
    String _username;
    String _password;
    String _nickname;

    public Users() {

    }

    public Users(int id, String username, String password, String nickname) {
        _id = id;
        _username = username;
        _password = password;
        _nickname = nickname;
    }

    public Users(String username, String password, String nickname) {
        _username = username;
        _password = password;
        _nickname = nickname;
    }

    public void set_id(int _id) {
        this._id = _id;
    }

    public void set_username(String _username) {
        this._username = _username;
    }

    public void set_password(String _password) {
        this._password = _password;
    }

    public void set_nickname(String _nickname) {
        this._nickname = _nickname;
    }

    public int get_id() {
        return _id;
    }

    public String get_username() {
        return _username;
    }

    public String get_password() {
        return _password;
    }

    public String get_nickname() {
        return _nickname;
    }
}
