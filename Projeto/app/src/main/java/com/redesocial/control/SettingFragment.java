package com.redesocial.control;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.redesocial.R;

// A classe setting vai ser um fragmento, pois ela vai ser setada na tela inicial do aplicativo somente quando o botão for clicado
public class SettingFragment extends Fragment {
    private EditText nameUser;
    private EditText newEmail;
    private EditText newPassword;
    private ImageView urlPhoto;
    private EditText birthdayUser;
    private RadioButton maleUser;
    private RadioButton femaleUser;
    private Button saveSetting;

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
        super.onStart();
    }

    private void conect() {
        urlPhoto = (ImageView) getActivity().findViewById(R.id.urlPhoto);
        nameUser = (EditText) getActivity().findViewById(R.id.nameUser);
        newEmail = (EditText) getActivity().findViewById(R.id.newEmail);
        newPassword = (EditText) getActivity().findViewById(R.id.newPassword);
        birthdayUser = (EditText) getActivity().findViewById(R.id.birthdayUser);
        maleUser = (RadioButton) getActivity().findViewById(R.id.maleUser);
        femaleUser = (RadioButton) getActivity().findViewById(R.id.femaleUser);
        saveSetting = (Button) getActivity().findViewById(R.id.saveSetting);
    }
}
