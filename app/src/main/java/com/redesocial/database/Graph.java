package com.redesocial.database;

import java.util.LinkedList;
import java.util.List;

public class Graph
{
    private final List<Node> nodes = new LinkedList<>();

    public void insert( User data )
    {
        /* Insere um novo vértice ao grafo */
        nodes.add( new Node( data ) );
    }

    /* Retorna um vértice se ele existir buscando pelo email */
    public Node getNode( String email )
    {
        for( Node node : nodes )
            if ( node.getData().getEmail().equals( email ) )
                return node;

        return null;
    }

    /* Conecta dois gráfos por uma aresta */
    public void linkVertex( Node nodeA, Node nodeB )
    {
        nodeA.insert( nodeB );
    }

}

class Node
{
    private User data;
    private List<Node> nodes = new LinkedList<>();

    public Node( User data )
    {
        this.data = data;
    }

    public void insert( Node data )
    {
        nodes.add( data );
    }

    public User getData()
    {
        return data;
    }



}

