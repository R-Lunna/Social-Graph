package com.redesocial.database;


public class User {

    private int id;
    private String name;
    private String email;
    private String password;
    private String urlPhoto;
    private String birthday;
    private String sex;
    private int positionX;
    private int positionY;

    public User()
    {

    }


    public User( int id, String name, String email, String password, String urlPhoto, String birthday, String sex, int positionX, int positionY )
    throws IllegalArgumentException
    {
        if( id < 0 )
            throw new IllegalArgumentException("Id cannot be negative");

        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.urlPhoto = urlPhoto;
        this.birthday = birthday;
        this.sex = sex;
        this.positionX = positionX;
        this.positionY = positionY;
    }

    public int getId() {
        return id;
    }

    public void setId( int id )
    throws IllegalArgumentException
    {
        if( id < 0 )
            throw new IllegalArgumentException("Id cannot be negative");

        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUrlPhoto() {
        return urlPhoto;
    }

    public void setUrlPhoto(String urlPhoto) {
        this.urlPhoto = urlPhoto;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getSex() {
        return sex;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public int getPositionX() {
        return positionX;
    }

    public void setPositionX(int positionX) {
        this.positionX = positionX;
    }

    public int getPositionY() {
        return positionY;
    }

    public void setPositionY(int positionY) {
        this.positionY = positionY;
    }
}
