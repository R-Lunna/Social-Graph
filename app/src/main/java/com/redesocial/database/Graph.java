package com.redesocial.database;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class Graph
{
    private final List<Vertex> vertices = new LinkedList<>();

    public void addVertex( User data )
    throws NullPointerException
    {
        if( data == null )
            throw new NullPointerException("Invalid vertex");

        /* Insere um novo vértice no grafo */
        vertices.add( new Vertex( data ) );
    }

    /* Remove um vértice, se existir, buscando pelo email */
    public void removeVertex( String email )
    throws IllegalArgumentException
    {
        Iterator<Vertex> vertexIterator = vertices.iterator();

        while( vertexIterator.hasNext() )
        {
            Vertex vertex = vertexIterator.next();
            if( vertex.getData().getEmail().equals( email ) )
            {
                vertexIterator.remove();
                return;
            }
        }

        /* Lança uma exceção pois o email passado não corresponde a um usuário */
        throw new IllegalArgumentException("Vertex not found");

    }

    /* Retorna um vértice, se ele existir, buscando pelo email do usuário */
    private Vertex getVertex( String email )
    {
        for( Vertex vertex : vertices )
            if ( vertex.getData().getEmail().equals( email ) )
                return vertex;

        /* O vértice não existe */
        return null;
    }

    /* Conecta dois gráfos por uma aresta */
    public void addEdge( String emailA, String emailB )
    throws NullPointerException
    {
        try
        {
            getVertex( emailA ).addEdge( getVertex( emailB ) );
        }
        catch ( NullPointerException e )
        {
            throw new IllegalArgumentException( "Vertex not found" , e );
        }

    }

    public void removeEdge( String emailA, String emailB )
    throws IllegalArgumentException
    {
        try
        {
            Vertex vertex = getVertex( emailA );
            vertex.removeEdge( emailB );
        }
        catch ( NullPointerException e )
        {
            throw new IllegalArgumentException("Vertex not found");
        }

    }

    /* Retorna o total de vértices no grafo */
    public int length()
    {
        return vertices.size();
    }

    /* Testa de o vértice A possui pelo menos uma aresta com o vértice B */
    public boolean adjacent( String emailA, String emailB )
    throws IllegalArgumentException
    {
        boolean sentinel = false;

        try
        {
            sentinel = getVertex( emailA ).isNeighbors( emailB );
        }
        catch ( NullPointerException e )
        {
            throw new IllegalArgumentException( "Vertex not found" , e );
        }

        return sentinel;
    }

    /* Verificia se o grafo contem o usuário especificado pelo email */
    public boolean contains( String email )
    {
        for( Vertex vertex : vertices )
            if( vertex.getData().getEmail().equals( email ) )
                return true;

        return false;
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

    public void addEdge( Vertex data )
    throws NullPointerException
    {
        if ( data == null )
            throw new NullPointerException();

        vertices.add( data );
    }

    public void removeEdge( String email )
    throws NullPointerException
    {
        Iterator<Vertex> vertexIterator = vertices.iterator();

        while( vertexIterator.hasNext() )
        {
            Vertex vertex = vertexIterator.next();
            if( vertex.getData().getEmail().equals( email ) )
            {
                vertexIterator.remove();
                return;
            }
        }

        /* Lança uma exceção pois o email passado não corresponde a um usuário */
        throw new NullPointerException("Vertex not found");
    }


    public User getData()
    {
        return data;
    }

    public boolean isNeighbors( String email )
    {
        for( Vertex vertex : vertices )
            if (vertex.getData().getEmail().equals( email ) )
                return true;
        return false;
    }


}

