package com.redesocial.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.redesocial.R;
import com.redesocial.database.LocalUser;
import com.redesocial.database.User;

public class Login extends AppCompatActivity {
    private Button login;
    private Button register;
    private EditText email;
    private EditText password;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new fullscreen(Login.this);
        setContentView(R.layout.activity_login);

        register = (Button) findViewById(R.id.register);
        setRegister();

        login = (Button) findViewById(R.id.login);

        setLogin();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        //finish();
        setContentView(R.layout.activity_cadastrar);
    }


    private void checkLogin(AlertDialog alertDialog) {
        email = (EditText) findViewById(R.id.emailLogin);
        password = (EditText) findViewById(R.id.passwordLogin);

        if (email.getText().toString().equals("")) error(email, "email inválido", alertDialog);
        else if (password.getText().toString().equals("")) error(password, "senha inválida", alertDialog);
        else consultaBancoLogin(email.getText().toString(), password.getText().toString());
    }

    private void error(EditText text, String textError, AlertDialog alertDialog) {
        text.setError(textError);
        text.requestFocus();
        alertDialog.dismiss();
    }

    private AlertDialog showAlert() {
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Login.this);
        LayoutInflater layoutInflater = getLayoutInflater();
        alertBuilder.setView(layoutInflater.inflate(R.layout.activity_main, null));
        alertBuilder.setCancelable(false);
        alertDialog = alertBuilder.create();
        alertDialog.show();
        return alertDialog;
    }

    private void userNotFound() {
        alertDialog.dismiss();
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(Login.this);
        alertBuilder.setTitle("Não te encontramos");
        alertBuilder.setMessage("Não te achamos");
        alertBuilder.setNeutralButton("ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
            }
        });
        alertDialog = alertBuilder.create();
        alertDialog.show();
    }

    private void consultaBancoLogin(String email, String password) {
        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("User");
        // faz um select no banco usando o objeto de referência
        databaseUser.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                boolean exist = false;
                // Percorre todos os ID, fazendo a consulta estilo lista
                for (DataSnapshot snapshot2 : snapshot.getChildren()) {
                    if (snapshot2.child("email").getValue().toString().equalsIgnoreCase(email) && snapshot2.child("password").getValue().toString().equals(password)) {
                        exist = true;
                        LocalUser localUser = new LocalUser(Integer.parseInt(snapshot2.child("id").getValue().toString()));
                        startActivity(new Intent(Login.this, HomeActivity.class));
                    }
                }

                if (!exist) userNotFound();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }

    private void setLogin() {
        login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                checkLogin(showAlert());
            }
        });
    }

    // Chama a tela de registrar ao clicar no botão "cadastrar" usando o método setOnClickListener
    private void setRegister() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instancia um objeto intent passando a tela atual e a nova tela
                startActivity(new Intent(Login.this, Register.class));

                // fecha a tela atual

            }
        });
    }
}