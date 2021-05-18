package com.redesocial.control;

import android.content.Intent;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.redesocial.R;
import com.redesocial.activity.DrawGraph;

public class GraphFragment extends Fragment {


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        DrawGraph drawGraph = new DrawGraph();

        drawGraph.configZoom();
        drawGraph.configPainter();
        //drawGraph.onTouchEvent();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        //return inflater.inflate(R.layout.fragment_graph, container, false);

        View view = inflater.inflate(R.layout.fragment_graph, container, false);

       // Intent myIntent = new Intent(getActivity(), DrawGraph.class);
        //startActivity( myIntent );

        return view;

    }

    @Override
    public void onStart() {

        super.onStart();
    }
}