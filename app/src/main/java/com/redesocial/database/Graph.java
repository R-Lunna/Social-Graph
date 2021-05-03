package com.redesocial.database;

import java.util.ArrayList;
import java.util.List;

public class Graph<T>
{
    private final List<Node<T>> nodes = new ArrayList<>();

    public Graph()
    {

    }

    public void insert( T data )
    {

        nodes.add( new Node<T>( data ) );
    }

}

class Node<T>
{
    private T data;
    private List<T> nodes = new ArrayList<>();

    public Node( T data )
    {
        this.data = data;
    }
}

