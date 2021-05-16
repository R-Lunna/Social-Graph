package com.redesocial.control;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;
import com.redesocial.R;



public class FollowFragment extends Fragment {

    DatabaseReference ref = FirebaseDatabase.getInstance().getReference();
    Query users = ref.child("User");


    private ListView listview;
    private ArrayAdapter arrayAdapter;





    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        System.out.println("users?? " + users);
    }

    @Override
    public void onStart() {
        super.onStart();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_follow, container, false);
    }


    ValueEventListener eventListener = new ValueEventListener() {
        @Override
        public void onDataChange(@NonNull DataSnapshot snapshot) {
            for(DataSnapshot ds : snapshot.getChildren()){
                String name = ds.child("name").getValue(String.class);
                Log.d("TAG", name);
            }
        }

        @Override
        public void onCancelled(@NonNull DatabaseError error) {

        }

    };



}