package com.redesocial.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.redesocial.R;

public class Login extends AppCompatActivity {
    private Button register;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //new fullscreen(Login.this);
        setContentView(R.layout.activity_login);

        register = (Button) findViewById(R.id.register);
        setRegister();
    }

    // Chama a tela de registrar ao clicar no botão "cadastrar" usando o método setOnClickListener
    private void setRegister() {
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Instancia um objeto intent passando a tela atual e a nova tela
                startActivity(new Intent(Login.this, Register.class));

                // fecha a tela atual
                finish();
            }
        });
    }
}