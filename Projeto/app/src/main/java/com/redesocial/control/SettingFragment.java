package com.redesocial.control;

import android.app.ProgressDialog;
import android.content.Intent;
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

import java.util.UUID;

import static android.app.Activity.RESULT_OK;

// A classe setting vai ser um fragmento, pois ela vai ser setada na tela inicial do aplicativo somente quando o botão for clicado
public class SettingFragment extends Fragment {
    private int positionX;
    private int positionY;
    private int id;
    private String oldEmail;
    private String oldPassword;
    private EditText nameUser;
    private EditText newEmail;
    private EditText newPassword;
    private ImageView urlPhoto;
    private EditText birthdayUser;
    private RadioButton maleUser;
    private RadioButton femaleUser;
    private Button saveSetting;
    private FirebaseStorage storage;
    private StorageReference storageReference;
    private Uri imageUri;
    private User user = new User();
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
    public void onStart() {
        connect();
        setSetting();
       // config();
       // choosePicture();
        super.onStart();
    }

    private void connect() {
        urlPhoto = (ImageView) getActivity().findViewById(R.id.urlPhoto);
        nameUser = (EditText) getActivity().findViewById(R.id.nameUser);
        newEmail = (EditText) getActivity().findViewById(R.id.newEmail);
        newPassword = (EditText) getActivity().findViewById(R.id.newPassword);
        birthdayUser = (EditText) getActivity().findViewById(R.id.birthdayUser);
        maleUser = (RadioButton) getActivity().findViewById(R.id.maleUser);
        femaleUser = (RadioButton) getActivity().findViewById(R.id.femaleUser);
        saveSetting = (Button) getActivity().findViewById(R.id.saveSetting);
    }

    private void setSetting() {
        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                    if (Integer.parseInt(snapshot2.child("id").getValue().toString()) == LocalUser.getId()) {
                        nameUser.setText(snapshot2.child("name").getValue().toString());

                        if(snapshot2.child("urlPhoto").getValue().toString().isEmpty()) {
                            Glide.with(getActivity()).load("").placeholder(R.drawable.ic_baseline_account_circle_24).into(urlPhoto);
                        } else {
                            Glide.with(getActivity()).load(snapshot2.child("urlPhoto").getValue().toString()).into(urlPhoto);
                            Log.d("msg", snapshot2.child("urlPhoto").getValue().toString());
                        }

                        birthdayUser.setText(snapshot2.child("birthday").getValue().toString());

                        if (snapshot2.child("sex").getValue().toString().equals("Feminino")) {
                            femaleUser.setChecked(true);
                            oldEmail = "Feminino";
                        } else {
                            maleUser.setChecked(true);
                            oldEmail = "Masculino";
                        }

                        id = Integer.parseInt(snapshot2.child("id").getValue().toString());
                        positionY = Integer.parseInt(snapshot2.child("positionY").getValue().toString());
                        positionX = Integer.parseInt(snapshot2.child("positionX").getValue().toString());
                        oldEmail = snapshot2.child("email").getValue().toString();
                        oldPassword = snapshot2.child("password").getValue().toString();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
/*
    private void config() { saveSetting.setOnClickListener(v -> update());}

    private void update() {


        if (newEmail.getText().toString().equals("")) user.setEmail(oldEmail);
        else user.setEmail(newEmail.getText().toString());

        if (maleUser.isChecked()) user.setSex("Masculino");
        else user.setSex("Feminino");

        if (newPassword.getText().toString().equals("")) user.setPassword(oldPassword);
        else user.setPassword(newPassword.getText().toString());
        user.setName(nameUser.getText().toString());
        user.setUrlPhoto("");
        user.setBirthday(birthdayUser.getText().toString());
        user.setPositionX(positionX);
        user.setPositionY(positionY);
        user.setId(id);

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.child(String.valueOf(LocalUser.getId())).setValue(user).addOnSuccessListener(aVoid -> {
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.fragmentLayout, new FollowFragment()).commit();
        });
    }

    private void choosePicture(){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        urlPhoto.setOnClickListener(v ->
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            urlPhoto.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture(){

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Enviando Imagem...");
        pd.show();


        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("image/"+ randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> riversRef.getDownloadUrl().addOnSuccessListener(uri ->
                {
                    user.setUrlPhoto(uri.toString());

                    pd.dismiss();
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Imagem Enviada", Snackbar.LENGTH_LONG).show();
                }))
                .addOnFailureListener(exception ->
                {
                    pd.dismiss();
                    Toast.makeText       if(snapshot2.child("urlPhoto").getValue().toString().isEmpty()) {
                            Glide.with(getActivity()).load("").placeholder(R.drawable.ic_baseline_account_circle_24).into(urlPhoto);
                        } else {
                            Glide.with(getActivity()).load(snapshot2.child("urlPhoto").getValue().toString()).into(urlPhoto);
                            Log.d("msg", snapshot2.child("urlPhoto").getValue().toString());
                        }

                        birthdayUser.setText(snapshot2.child("birthday").getValue().toString());

                        if (snapshot2.child("sex").getValue().toString().equals("Feminino")) {
                            femaleUser.setChecked(true);
                        } else {
                            maleUser.setChecked(true);
                        }
                        oldEmail = snapshot2.child("email").getValue().toString();
                        oldPassword = snapshot2.child("password").getValue().toString();
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void config() { saveSetting.setOnClickListener(v -> update());}

    private void update() {
        user.setName(nameUser.getText().toString());
        user.setEmail(newEmail.getText().toString());
        user.setBirthday(birthdayUser.getText().toString());
        if (maleUser.isChecked()) user.setSex("Masculino");
        else user.setSex("Feminino");
        user.setPassword(newPassword.getText().toString());

        DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("User");
        databaseReference.child(String.valueOf(LocalUser.getId())).setValue(user).addOnSuccessListener(aVoid -> {
            getActivity().getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.fragmentLayout, new FollowFragment()).commit();
        });
    }
/*
    private void choosePicture(){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        urlPhoto.setOnClickListener(v ->
        {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode == 1 && resultCode == RESULT_OK && data != null && data.getData() != null){
            imageUri = data.getData();
            urlPhoto.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture(){

        final ProgressDialog pd = new ProgressDialog(getContext());
        pd.setTitle("Enviando Imagem...");
        pd.show();


        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("image/"+ randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> riversRef.getDownloadUrl().addOnSuccessListener(uri ->
                {
                    user.setUrlPhoto(uri.toString());

                    pd.dismiss();
                    Snackbar.make(getActivity().findViewById(android.R.id.content), "Imagem Enviada", Snackbar.LENGTH_LONG).show();
                }))
                .addOnFailureListener(exception ->
                {
                    pd.dismiss();
                    Toast.makeText(getActivity().getApplicationContext(), "Erro ao Enviar a Imagem", Toast.LENGTH_LONG).show();
                }).addOnProgressListener(snapshot -> {
            double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
            pd.setMessage("" + (int) progressPercent + "%");
        });

    }*/
}
