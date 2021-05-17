package com.redesocial.database;

import org.jetbrains.annotations.NotNull;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class Graph
{
    private final List<Vertex> vertices = new LinkedList<>();

    /* Cria um novo vértice para o grafo */
    public void addVertex( User data )
    throws NullPointerException
    {
        if( data == null )
            throw new NullPointerException("Invalid vertex");

        /* Cria e insere um novo vértice no grafo */
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
                /* Remove o vértice do grafo */
                vertexIterator.remove();

                /* Para cada vértice no grafo, remove o vértice da lista de arestas */
                for ( Vertex vertex1 : vertices )
                {
                    try
                    {
                        vertex1.removeEdge( email );
                    }
                    catch (NullPointerException ignored)
                    {

                    }
                }

                return;
            }
        }

        /* Lança uma exceção pois o email passado não corresponde a um usuário */
        throw new IllegalArgumentException("Vertex not found");

    }

    /* Retorna um vértice, se ele existir, buscando pelo email do usuário */
    private Vertex getVertex( String email )
    throws NullPointerException
    {
        for( Vertex vertex : vertices )
            if ( vertex.getData().getEmail().equals( email ) )
                return vertex;

        /* O vértice não existe */
        throw new NullPointerException("Vexter not found");
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

    /* Remove uma aresta entre dois vértices (buscados pelo email)*/
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

    /* Retorna o total de arestas no grafo */
    public int getTotalEdge()
    {
        int countEdge = 0;

        for ( Vertex vertex : vertices )
            countEdge += vertex.getTotalNeighbors();

        return countEdge;
    }

    /* Retorna o total de vértices no grafo */
    public int length()
    {
        return vertices.size();
    }

    /* Testa de o vértice A possui pelo menos uma aresta com o vértice B */
    public boolean isAdjacent( String emailA, String emailB )
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

    /* Verifica se o grafo contem o usuário especificado pelo email */
    public boolean contains( String email )
    {
        for( Vertex vertex : vertices )
            if( vertex.getData().getEmail().equals( email ) )
                return true;

        return false;
    }

    /* Retorna, se existir, o usuário especificado pelo email */
    public User getVertexData( String email )
    throws IllegalArgumentException
    {
        Vertex vertex = null;
        try
        {
            vertex = getVertex( email );
        }
        catch ( NullPointerException e )
        {
            throw new IllegalArgumentException("Vertex not found");
        }

        return vertex.getData();
    }

    /* Retorna, se existir, o usuário especificado pelo indice */
    public User getVertexData( int index )
    throws ArrayIndexOutOfBoundsException
    {
        Vertex vertex = null;

        return vertices.get( index ).getData();

    }

    @NotNull
    @Override
    public String toString()
    {
        return String.format(Locale.ENGLISH,"Graph with %d vertices", length());
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

    /* Adiciona uma nova aresta ao vértice */
    public void addEdge( Vertex data )
    throws NullPointerException
    {
        if ( data == null )
            throw new NullPointerException();

        vertices.add( data );
    }

    /* Remove a aresta do vértice especificada pelo email */
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

    /* Obtem o usuário armazenado no vértice */
    public User getData()
    {
        return data;
    }

    /* Verifica se um dado vértice (especificado pelo email) é vizinho do vértice atual (ou seja, possui ao menos uma aresta) */
    public boolean isNeighbors( String email )
    {
        for( Vertex vertex : vertices )
            if (vertex.getData().getEmail().equals( email ) )
                return true;
        return false;
    }

    /* Retorna o total de vizinhos ( arestas )*/
    public int getTotalNeighbors()
    {
        return vertices.size();
    }
}

