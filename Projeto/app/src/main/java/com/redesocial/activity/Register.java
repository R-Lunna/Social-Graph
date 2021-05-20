package com.redesocial.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.redesocial.R;
import com.redesocial.database.User;

import java.util.UUID;

public class Register extends AppCompatActivity {

    private FirebaseStorage storage;
    private StorageReference storageReference;

    private EditText name;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private ImageView urlPhoto;
    private EditText birthday;
    private RadioButton male;
    private RadioButton female;
    private Button register;

    private Uri imageUri;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        checkObject();
        choosePicture();
        setRegister();
    }

    private void checkObject() {
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        urlPhoto = (ImageView) findViewById(R.id.urlPhoto);
        birthday = (EditText) findViewById(R.id.birthday);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        register = (Button) findViewById(R.id.register);
    }

    private void choosePicture(){
        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        urlPhoto.setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setType("image/*");
            intent.setAction(Intent.ACTION_GET_CONTENT);
            startActivityForResult(intent,1);
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data){
        super.onActivityResult(requestCode,resultCode,data);

        if(requestCode==1 && resultCode == RESULT_OK && data!=null && data.getData()!=null){
            imageUri = data.getData();
            urlPhoto.setImageURI(imageUri);
            uploadPicture();
        }
    }

    private void uploadPicture(){

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Enviando Imagem...");
        pd.show();


        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("image/"+ randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                    @Override
                    public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                        pd.dismiss();
                        Snackbar.make(findViewById(android.R.id.content), "Imagem Enviada", Snackbar.LENGTH_LONG).show();
                    }
                })
                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception exception) {
                        pd.dismiss();
                        Toast.makeText(getApplicationContext(), "Errro ao Enviar a Imagem", Toast.LENGTH_LONG).show();
                    }
                }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                pd.setMessage("" + (int) progressPercent + "%");
            }
        });

    }

    private void checkInfo(AlertDialog alertDialog) {
        if (email.getText().toString().contains("@")) {
            saveUser();
        } else {
            error(email, "Email inválido", alertDialog);
        }
    }



    private void error(EditText text, String textError, AlertDialog alertDialog) {
        text.setError(textError);
        text.requestFocus();
        alertDialog.dismiss();
    }

    private void setRegister() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                checkInfo(showAlert());
            }
        });
    }

    private AlertDialog showAlert() {
        // Instancia o objeto do tipo alertdialog passando a tela atual
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Register.this);

        // Instancia o objeto do tipo layoutInflater
        LayoutInflater layoutInflater = getLayoutInflater();

        // Vai na activity e pega o layout setando na tela sem fechar a outra tela. É como uma tela em cima da outra
        alertBuilder.setView(layoutInflater.inflate(R.layout.activity_main, null));

        // Essa tela não pode ser cancelada
        alertBuilder.setCancelable(false);

        // a variável vai se tornar o alertBuilder
        alertDialog = alertBuilder.create();

        // Faz ela aparecer na tela
        alertDialog.show();

        return alertDialog;
    }


    private void saveUser() {
        User user = new User();

        user.setName(name.getText().toString());
        user.setEmail(email.getText().toString());
        user.setBirthday(birthday.getText().toString());
        user.setPassword(password.getText().toString());
        if (male.isChecked()) user.setSex(male.getText().toString());
        else user.setSex(female.getText().toString());

        // Referencia a tabela usuário no objeto databaseUser
        // Como se tivesse fazendo um insert no banco
        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("User");

        // child cria um id pra o objeto, é como a sequence no banco de dados só que é um id de caracteres
        databaseUser.child(databaseUser.push().getKey()).setValue(user);
    }
}