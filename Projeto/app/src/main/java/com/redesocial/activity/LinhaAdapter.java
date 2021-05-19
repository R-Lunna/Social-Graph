package com.redesocial.activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.redesocial.R;

public class LinhaAdapter extends BaseAdapter {

    public LinhaAdapter() {
        layoutInflater = (LayoutInflater) this.consultar.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        final View viewLinha = layoutInflater.inflate(R.layout.activity_linha, null);

        TextView txtCodigo = (TextView) viewLinha.findViewById(R.id.linhLblCodigo);
        Button btnEditar = (Button) viewLinha.findViewById(R.id.linBtnEditar);




        return viewLinha;
    }

    private void consultarBancoUsuarios() {
        DatabaseReference user = FirebaseDatabase.getInstance().getReference("User");
        user.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                    snapshot2.child("name").getValue().toString();
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
