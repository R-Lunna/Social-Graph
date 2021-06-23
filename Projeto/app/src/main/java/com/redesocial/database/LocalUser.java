package com.redesocial.database;

public class LocalUser {

    private static int id;

    public LocalUser(){

    }

    public LocalUser(int id){
        this.id = id;
    }

    public static int getId() {
        return id;
    }


}
