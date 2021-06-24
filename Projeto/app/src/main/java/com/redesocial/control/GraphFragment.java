package com.redesocial.control;


import android.content.res.Resources;
import android.os.Bundle;
import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.redesocial.R;
import com.redesocial.activity.DrawGraph;
import com.redesocial.datastructures.Graph;
import com.redesocial.database.User;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class GraphFragment extends Fragment{

    DrawGraph drawGraph;
    Graph graph = Graph.getGraph();


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {


        return inflater.inflate(R.layout.fragment_graph, container, false);

    }

    @Override
    public void onStart() {
        drawGraph = (DrawGraph) getView().findViewById( R.id.drawGraph );

        DisplayMetrics displayMetrics = Resources.getSystem().getDisplayMetrics();
        drawGraph.initialConfig( displayMetrics );


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                 int id;
                 String name;
                 String email;
                 String password;
                 String urlPhoto;
                 String birthday;
                 String sex;
                 int positionX;
                 int positionY;
                 ArrayList<Integer> edges = new ArrayList<>();

                for( DataSnapshot dataSnapshot : snapshot.getChildren() )
                {
                    id = Integer.parseInt(dataSnapshot.child("id").getValue().toString());
                    name = dataSnapshot.child("name").getValue().toString();
                    email = dataSnapshot.child("email").getValue().toString();
                    password = dataSnapshot.child("password").getValue().toString();
                    urlPhoto = dataSnapshot.child("urlPhoto").getValue().toString();
                    birthday = dataSnapshot.child("birthday").getValue().toString();
                    sex = dataSnapshot.child("sex").getValue().toString();
                    positionX = Integer.parseInt(dataSnapshot.child("positionX").getValue().toString());
                    positionY = Integer.parseInt(dataSnapshot.child("positionY").getValue().toString());

                    List<Integer> edgesAux = new LinkedList<>();

                    for( DataSnapshot dataSnapshot1 : dataSnapshot.child("Edges").getChildren() )
                    {
                        String s = dataSnapshot1.getValue().toString();
                        edgesAux.add( (Integer.parseInt( s ) ) );
                    }

                    for( int count = 0; count < edgesAux.size(); count++ )
                    {
                        edges.add(Integer.parseInt( edgesAux.get( count ).toString()));
                    }

                    graph.importVertex( new User( id, name, email, password, urlPhoto, birthday, sex, positionX, positionY, edges ) );
                }

                /* Configura todas arestas */
                graph.configEdges();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
        });

        drawGraph.setGraph( graph );

        super.onStart();
    }




}