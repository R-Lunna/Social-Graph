package com.redesocial.control;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.redesocial.R;

import org.w3c.dom.Text;

public class FollowFragment extends Fragment {
    private LinearLayout linearLayout;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_follow, container, false);
    }

    @Override
    public void onStart() {
        linearLayout = (LinearLayout) getActivity().findViewById(R.id.lista);
        montarLinha();
        super.onStart();
    }

    private void montarLinha() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");

        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                    TextView name = new TextView(getContext());
                    name.setTextColor(Color.WHITE);
                    name.setText(snapshot2.child("name").getValue().toString());

                    RelativeLayout relativeLayout = new RelativeLayout(getContext());
                    ImageView img = new ImageView(getContext());

                    if (snapshot2.child("urlPhoto").getValue() != null) {
                        Glide.with(getActivity()).load(snapshot2.child("urlPhoto").getValue().toString()).into(img);
                    }

                    name.setPadding(30, 0, 0, 0);
                    relativeLayout.addView(img, 100, 100);
                    relativeLayout.addView(name);

                    linearLayout.addView(relativeLayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

}