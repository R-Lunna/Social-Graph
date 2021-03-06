package com.redesocial.control;
import android.annotation.SuppressLint;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.icu.number.NumberRangeFormatter;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
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
import com.redesocial.database.LocalUser;

import org.w3c.dom.Text;

import java.util.Locale;

import de.hdodenhof.circleimageview.CircleImageView;

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
            @SuppressLint({"UseCompatLoadingForDrawables", "ResourceType"})
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                int countID = 0;
                for(DataSnapshot snapshot2 : snapshot.getChildren())
                {
                    int id = Integer.parseInt(snapshot2.child("id").getValue().toString());
                    if( id == LocalUser.getId())
                        continue;

                    TextView name = new TextView(getContext());
                    name.setTextColor(Color.WHITE);
                    name.setText(snapshot2.child("name").getValue().toString());

                    RelativeLayout relativeLayout = new RelativeLayout(getContext());
                    CircleImageView img = new CircleImageView(getContext());
                    Button btn = new Button(getContext());

                    if(snapshot2.child("urlPhoto").getValue().toString().isEmpty())
                        Glide.with(getActivity()).load("").placeholder(R.drawable.ic_baseline_account_circle_24).into(img);
                    else
                        Glide.with(getActivity()).load(snapshot2.child("urlPhoto").getValue().toString()).into(img);

                    name.setPadding(400, 0, 0, 0);
                    name.setTextSize(20);

                    img.setX(20);
                    img.setBorderWidth(3);
                    btn.setX(400);
                    btn.setY(100);
                    btn.setWidth(100);
                    btn.setTextColor(Color.WHITE);

                    btn.setId( id );

                    if( !LocalUser.getEges().contains( btn.getId() ))
                    {
                        btn.setText("Seguir");
                        btn.setBackgroundColor(getResources().getColor(R.color.SecondayVariant));
                    }
                    else
                    {
                        btn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                        btn.setText("Parar de seguir");
                    }

                    btn.setOnClickListener(v -> {

                        if (btn.getText().equals("Seguir"))
                        {
                            btn.setBackgroundColor(getResources().getColor(R.color.teal_700));
                            btn.setText("Parar de seguir");
                            btn.setTextColor(Color.WHITE);

                            LocalUser.getEges().add( btn.getId() );

                            //save here
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("User/" + LocalUser.getId());
                            databaseReference1.child("Edges").setValue( LocalUser.getEges() );

                        }
                        else
                        {
                            btn.setBackgroundColor(getResources().getColor(R.color.SecondayVariant));

                            btn.setText("Seguir");

                            btn.setTextColor(Color.WHITE);

                            LocalUser.getEges().remove( Integer.valueOf( btn.getId() ) );

                            // save here
                            DatabaseReference databaseReference1 = FirebaseDatabase.getInstance().getReference("User/" + LocalUser.getId());
                            databaseReference1.child("Edges").setValue( LocalUser.getEges() );
                        }



                    });

                    TextView number = new TextView(getContext());

                    int countUsers = 0;
                    for( DataSnapshot dataSnapshot : snapshot2.child("Edges").getChildren() )
                        countUsers++;

                    number.setText( String.format(Locale.getDefault(), "N??mero de seguidores: %d", countUsers));
                    number.setTextColor( Color.WHITE );
                    number.setTextSize( 15f );
                    number.setPadding(400, 250, 0,0 );
                    relativeLayout.addView(number);

                    relativeLayout.addView(img, 250, 250);
                    relativeLayout.addView(name);
                    relativeLayout.addView(btn);
                    relativeLayout.setPadding(0, 50, 0, 50);
                    relativeLayout.setBackground(getResources().getDrawable(R.drawable.borda));
                    linearLayout.addView(relativeLayout);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }



}