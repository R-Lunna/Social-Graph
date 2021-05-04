package com.redesocial.database;

import java.util.LinkedList;
import java.util.List;

public class Graph
{
    private final List<Vertex> vertices = new LinkedList<>();

    public void insert( User data )
    {
        /* Insere um novo vértice ao grafo */
        vertices.add( new Vertex( data ) );
    }

    /* Retorna um vértice, se ele existir, buscando pelo email do usuário */
    private Vertex getNode( String email )
    {
        for( Vertex vertex : vertices )
            if ( vertex.getData().getEmail().equals( email ) )
                return vertex;

        /* O vértice não existe */
        return null;
    }

    /* Conecta dois gráfos por uma aresta */
    public void linkVertex( String emailA, String emailB )
    throws NullPointerException
    {
        try
        {
            getNode( emailA).insert( getNode( emailB ) );
        }
        catch ( NullPointerException e )
        {
            throw new IllegalArgumentException( "Vertex not found" , e );
        }

    }

    /* Total de vértices no grafo */
    public int length()
    {
        return vertices.size();
    }

}

class Vertex
{
    private final User data;
    private final List<Vertex> vertices = new LinkedList<>();
    private int positionX;
    private int PositionY;

    public Vertex(User data )
    {
        this.data = data;
    }

    public void insert( Vertex data )
    throws NullPointerException
    {
        if ( data == null )
            throw new NullPointerException();

        vertices.add( data );
    }

    public User getData()
    {
        return data;
    }

}

