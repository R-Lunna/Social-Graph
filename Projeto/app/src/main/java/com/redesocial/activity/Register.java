package com.redesocial.activity;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.redesocial.R;
import com.redesocial.database.LocalUser;
import com.redesocial.database.User;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Locale;
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
    private User user;
    private Uri imageUri;
    private AlertDialog alertDialog;
    private DatePickerDialog datePickerDialog;


    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finish();
        setContentView(R.layout.activity_login);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cadastrar);

        checkObject();
        choosePicture();
        setRegister();
        setCalender();
    }

    private void setCalender() {
        Calendar calendar = Calendar.getInstance();

        int thisDay = calendar.get( Calendar.DAY_OF_MONTH );
        int thisMonth = calendar.get( Calendar.MONTH );
        int thisYear = calendar.get( Calendar.YEAR );

        datePickerDialog = new DatePickerDialog(this, (view, year, month, dayOfMonth) -> birthday.setText( String.format(Locale.getDefault(),"%02d/%02d/%4d", dayOfMonth, month, year ) ), thisYear, thisMonth, thisDay );

        birthday.setOnClickListener(v -> datePickerDialog.show());

        birthday.setOnFocusChangeListener( ( view, hasFocus) -> datePickerDialog.show());

    }

    private void checkObject()
    {
        name = (EditText) findViewById(R.id.name);
        email = (EditText) findViewById(R.id.email);
        password = (EditText) findViewById(R.id.password);
        confirmPassword = (EditText) findViewById(R.id.confirmPassword);
        urlPhoto = (ImageView) findViewById(R.id.urlPhoto);
        birthday = (EditText) findViewById(R.id.birthday);
        male = (RadioButton) findViewById(R.id.male);
        female = (RadioButton) findViewById(R.id.female);
        register = (Button) findViewById(R.id.register);
        user = new User();
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

        final ProgressDialog pd = new ProgressDialog(this);
        pd.setTitle("Enviando Imagem...");
        pd.show();


        final String randomKey = UUID.randomUUID().toString();
        StorageReference riversRef = storageReference.child("image/"+ randomKey);

        riversRef.putFile(imageUri)
                .addOnSuccessListener(taskSnapshot -> riversRef.getDownloadUrl().addOnSuccessListener(uri ->
                {
                    user.setUrlPhoto(uri.toString());

                    pd.dismiss();
                    Snackbar.make(findViewById(android.R.id.content), "Imagem Enviada", Snackbar.LENGTH_LONG).show();
                }))
                .addOnFailureListener(exception ->
                {
                    pd.dismiss();
                    Toast.makeText(getApplicationContext(), "Erro ao Enviar a Imagem", Toast.LENGTH_LONG).show();
                }).addOnProgressListener(snapshot -> {
                    double progressPercent = (100.00 * snapshot.getBytesTransferred()/snapshot.getTotalByteCount());
                    pd.setMessage("" + (int) progressPercent + "%");
                });

    }

    private void checkInfo(AlertDialog alertDialog) {

        boolean sentinelRegister = true;

        if( !name.getText().toString().matches("\\S*.\\S*") )
        {
            error(name, "Nome inválido", alertDialog);
            sentinelRegister = false;
        }

        if( birthday.getText().toString().isEmpty())
        {
            error(birthday, "Data inválida", alertDialog);
            sentinelRegister = false;
        }

        if ( !email.getText().toString().matches("[a-zA-z_0-9]+@[a-zA-Z0-9]+\\.[a-zA-Z0-9]+"))
        {
            error(email, "Email inválido", alertDialog);
            sentinelRegister = false;
        }

        if( !password.getText().toString().matches("\\S*.\\S*") )
        {
            error(password, "Senha inválida", alertDialog);
            sentinelRegister = false;
        }

        if( !confirmPassword.getText().toString().matches("\\S*.\\S*") )
        {
            error(confirmPassword, "Senha inválida", alertDialog);
            sentinelRegister = false;
        }

        if( !password.getText().toString().equals( confirmPassword.getText().toString()))
        {
            error(confirmPassword, "Senhas diferentes", alertDialog);
            sentinelRegister = false;
        }


        if( !(male.isChecked() || female.isChecked()) )
        {
            male.setError("Error");
            male.requestFocus();
            alertDialog.dismiss();
            sentinelRegister = false;
        }

        if( sentinelRegister )
            saveUser();

    }



    private void error(EditText text, String textError, AlertDialog alertDialog) {
        text.setError(textError);
        text.requestFocus();
        alertDialog.dismiss();
    }

    private void setRegister() {
        register.setOnClickListener(v -> checkInfo(showAlert()));

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
        user.setName(name.getText().toString());
        user.setEmail(email.getText().toString());
        user.setPassword(password.getText().toString());

        String[] date = birthday.getText().toString().split("/");
        user.setBirthday( date[0] + "/" + (Integer.parseInt(date[1]) + 1) + "/" + date[2]);
        user.setBirthday( String.format(Locale.getDefault(), "%s/%02d/%s", date[0], (Integer.parseInt(date[1]) + 1), date[2]));




        if (male.isChecked())
            user.setSex(male.getText().toString());
        else
            user.setSex(female.getText().toString());

        if(user.getUrlPhoto() == null)
            user.setUrlPhoto("");

        ArrayList<Integer> positionsX = new ArrayList<>();
        ArrayList<Integer> positionsY = new ArrayList<>();

        // Referencia a tabela usuário no objeto databaseUser
        // Como se tivesse fazendo um insert no banco
        DatabaseReference databaseUser = FirebaseDatabase.getInstance().getReference("User");

        databaseUser.addListenerForSingleValueEvent(new ValueEventListener()
        {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot)
            {
                for(DataSnapshot snapshot2 : snapshot.getChildren())
                {
                    int childID = Integer.parseInt(snapshot2.child("id").getValue().toString());

                    if( LocalUser.getLastID() < childID )
                        LocalUser.setLastID( childID );

                    int positionX = Integer.parseInt(snapshot2.child("positionX").getValue().toString());
                    int positionY = Integer.parseInt(snapshot2.child("positionY").getValue().toString());

                    positionsX.add( positionX );
                    positionsY.add( positionY );

                }

                /* Gera nova posição */
                int x = 0;
                int y = 0;
                boolean sentinel = true;
                final int MARGIN = 100;
                final int RADIUS = 100;
                SecureRandom random = new SecureRandom();

                while( sentinel )
                {
                    sentinel = false;

                    x = random.nextInt(10000) * ((random.nextInt(2) == 0)?-1:1);
                    y = random.nextInt(10000) * ((random.nextInt(2) == 0)?-1:1);

                    for( int count = 0; count < positionsX.size(); count++ )
                    {
                        /* Verifica se há colisão */
                        if( Math.sqrt( Math.pow( (positionsX.get( count ) - x), 2) + Math.pow( positionsY.get( count ) - y, 2)  ) < MARGIN + RADIUS + RADIUS )
                            sentinel = true;
                    }
                }

                user.setPositionX( x );
                user.setPositionY( y );

                user.setId( LocalUser.getLastID() + 1);
                LocalUser.setLastID( LocalUser.getLastID() + 1);

                DatabaseReference usersRef = databaseUser.child(String.valueOf( LocalUser.getLastID()));

                LocalUser.setUser( user );

                usersRef.setValue( user );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        startActivity(new Intent(Register.this, HomeActivity.class));
        finish();

    }
}