package com.redesocial.control;


import android.content.res.Resources;
import android.os.Bundle;
import androidx.fragment.app.Fragment;

import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redesocial.R;
import com.redesocial.activity.DrawGraph;
import com.redesocial.database.Graph;
import com.redesocial.database.User;

public class GraphFragment extends Fragment {

    DrawGraph drawGraph;
    Graph graph = new Graph();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_graph, container, false);

    }

    @Override
    public void onStart() {
        drawGraph = (DrawGraph) getView().findViewById( R.id.drawGraph );

        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        drawGraph.initialConfig( displayMetrics );

        User user;

        user = new User();
        user.setName("Joao Pedro");
        user.setEmail("0");
        graph.addVertex( user );

        user = new User();
        user.setName("Tiago Augusto");
        user.setEmail("1");
        graph.addVertex( user );

        user = new User();
        user.setName("Maria Dolores");
        user.setEmail("2");
        graph.addVertex( user );

        user = new User();
        user.setName("Guilherme Freitas");
        user.setEmail("3");
        graph.addVertex( user );

        user = new User();
        user.setName("Ana Clara");
        user.setEmail("4");
        graph.addVertex( user );

        user = new User();
        user.setName("Marcos Vinícius");
        user.setEmail("5");
        graph.addVertex( user );

        graph.addEdge( "0", "1" );
        graph.addEdge( "0", "3" );
        graph.addEdge( "1", "3" );
        graph.addEdge( "1", "2" );
        graph.addEdge( "2", "3" );
        graph.addEdge( "2", "2" );
        graph.addEdge( "3", "0" );
        graph.addEdge( "5", "4" );

        drawGraph.setGraph( graph );

        super.onStart();
    }
}