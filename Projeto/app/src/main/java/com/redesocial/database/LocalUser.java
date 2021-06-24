package com.redesocial.database;

import java.util.ArrayList;
import java.util.List;

public class LocalUser
{
    private static final User localUser = new User();
    private static int lastID;

    private LocalUser()
    {

    }

    public static void setUser( User user )
    {
        localUser.setId( user.getId() );
        localUser.setSex( user.getSex() );
        localUser.setBirthday( user.getBirthday() );
        localUser.setPassword( user.getPassword() );
        localUser.setEmail( user.getEmail() );
        localUser.setName( user.getName() );
        localUser.setUrlPhoto( user.getUrlPhoto() );
        localUser.setPositionX( user.getPositionX() );
        localUser.setPositionY( user.getPositionY() );
        localUser.setEdges( user.getEdges() );
    }

    public static void setLastID( int id )
    {
        lastID = id;
    }

    public static int getLastID()
    {
        return lastID;
    }

    public static void setId( int id )
    {
        localUser.setId( id );
    }

    public static int getId()
    {
        return localUser.getId();
    }

    public static String getName()
    {
        return localUser.getName();
    }

    public static  void setName(String name)
    {
        localUser.setName( name );
    }

    public static  String getEmail() {
        return localUser.getEmail();
    }

    public static  void setEmail(String email)
    {
       localUser.setEmail( email );
    }

    public static  String getPassword() {
        return localUser.getPassword();
    }

    public static  void setPassword(String password)
    {
        localUser.setPassword( password );
    }

    public static  String getUrlPhoto()
    {
        return localUser.getUrlPhoto();
    }

    public static  void setUrlPhoto(String urlPhoto) {
       localUser.setUrlPhoto( urlPhoto );
    }

    public static  String getBirthday() {
        return localUser.getBirthday();
    }

    public static  void setBirthday(String birthday) {
        localUser.setBirthday( birthday );
    }

    public static  String getSex() {
        return localUser.getSex();
    }

    public static  void setSex(String sex) {
        localUser.setSex( sex );
    }

    public static void setEdges(ArrayList<Integer> edgesAux)
    {
        localUser.setEdges( edgesAux );
    }

    public static ArrayList<Integer> getEges()
    {
        return localUser.getEdges();
    }

    public static int getPositionX()
    {
        return localUser.getPositionX();
    }

    public static int getPositionY()
    {
        return localUser.getPositionY();
    }

    public static void setPositionX( int positionX )
    {
        localUser.setPositionX( positionX );
    }

    public static void setPositionY( int positionY )
    {
        localUser.setPositionY( positionY );
    }

}
