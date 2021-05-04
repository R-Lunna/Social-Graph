package com.redesocial;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.redesocial.database.User;

import java.util.Date;

public class register extends AppCompatActivity {
    private EditText name;
    private EditText email;
    private EditText password;
    private EditText confirmPassword;
    private ImageView urlPhoto;
    private EditText birthday;
    private RadioButton male;
    private RadioButton female;
    private Button register;

    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);
        checkObject();
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
        AlertDialog.Builder alertBuilder = new AlertDialog.Builder(register.this);

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