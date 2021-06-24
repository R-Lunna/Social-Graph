package com.redesocial.control;

import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.redesocial.R;
import com.redesocial.activity.HomeActivity;
import com.redesocial.activity.Register;
import com.redesocial.database.LocalUser;
import com.redesocial.database.User;

import java.util.Locale;
import java.util.UUID;

import de.hdodenhof.circleimageview.CircleImageView;

import static android.app.Activity.RESULT_OK;


public class ProfileFragment extends Fragment
{

    private TextView nameUser;
    private TextView emailUser;
    private TextView birthdayUser;
    private TextView sexUser;
    private TextView followCountUser;
    private ImageView urlPhoto;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri imageUri;

    // Método create para a classe fragmento
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        /*
        *   -- Linka a tela de Layout settings.xml com o código
        *   -- Como ela é um fragmento, é necessário associar o código com o layout
        *   -- Passa como argumento para inflate a tela atual, um container e o argumento como false pois a tela ainda não foi selecionada
        * */
        return inflater.inflate(R.layout.settings, container, false);
    }

    // Método que inicia a aplicação
    @Override
    public void onStart()
    {
        connect();
        setSetting();
        super.onStart();
    }

    private void connect()
    {
        nameUser = (TextView) getActivity().findViewById(R.id.nameUser);
        emailUser = (TextView) getActivity().findViewById(R.id.emailUser);
        sexUser = (TextView) getActivity().findViewById(R.id.sexUser);
        birthdayUser = (TextView) getActivity().findViewById(R.id.birthdayUser);
        urlPhoto = (CircleImageView) getActivity().findViewById(R.id.urlPhoto);
        followCountUser = (TextView) getActivity().findViewById(R.id.followCountUser);
    }

    private void setSetting() {
        nameUser.setText(String.format(Locale.getDefault(), "Nome: %s", LocalUser.getName()));
        nameUser.setTextSize(20);

        emailUser.setText(String.format(Locale.getDefault(), "Email: %s", LocalUser.getEmail()));
        emailUser.setTextSize(20);

        birthdayUser.setText(String.format(Locale.getDefault(), "Nascimento: %s", LocalUser.getBirthday()));
        birthdayUser.setTextSize(20);

        sexUser.setText(String.format(Locale.getDefault(), "Sexo: %s", LocalUser.getSex()));
        sexUser.setTextSize(20);


        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User/" + LocalUser.getId());
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener()
        {

            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {

                int countFollows = 0;

                for( DataSnapshot dataSnapshot : snapshot.child("Edges").getChildren() )
                    countFollows++;

                followCountUser.setText(String.format(Locale.getDefault(), "Quantidade de seguidores: %d", countFollows));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error)
            {

            }
            });

        followCountUser.setTextSize(20);

        if (!LocalUser.getUrlPhoto().equals(""))
            Glide.with(getActivity()).load(LocalUser.getUrlPhoto()).into(urlPhoto);
        else
            Glide.with(getActivity()).load("").placeholder(R.drawable.ic_iconborder).into(urlPhoto);

    }
}
