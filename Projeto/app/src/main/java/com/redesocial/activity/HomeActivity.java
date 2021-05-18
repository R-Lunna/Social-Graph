package com.redesocial.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.redesocial.R;
import com.redesocial.control.FollowFragment;
import com.redesocial.control.GraphFragment;
import com.redesocial.control.SettingFragment;

public class HomeActivity extends AppCompatActivity {

    // Variável do tipo BottomNavigationView que será usada para referênciar a barra de navegação do menu e por setar o controle dela
    BottomNavigationView bottomNavigationView;

    // Controle da barra de navegação, é instânciado um objeto que funcionara como um identificador de qual botão o usuário clicou na barra de menu
    BottomNavigationView.OnNavigationItemSelectedListener btno = new BottomNavigationView.OnNavigationItemSelectedListener() {
        @Override
        public boolean onNavigationItemSelected(@NonNull MenuItem item) {

            // Switch case responsável por identificar qual o botão selecionado
            // -- Configurações, grafo ou seguir
            // -- O método recebe como parâmetro uma variável do tipo MenuItem,
            switch (item.getItemId()) {
                // Caso seja a aba de configurações, execute um openView passando o fragmento de tela "configurações"

                case R.id.setting:
                    openView(new SettingFragment());
                    break;
                case R.id.graph:
                    openView(new GraphFragment());
                    break;
                case R.id.follow:
                    openView(new FollowFragment());
                    break;

            }
            return false;
        }
    };

    // Método que recebe o fragmento de tela e seta na tela principal
    private void openView(Fragment fragment) {
        // Pega o suporte de fragmento, aplica uma transição junto com uma animação de fade_in, fade_out
        // Em seguida passa o ID do fragmento de layout e o fragmento de tela (configurações, seguir ou grafo) e por último commita pra que seja apresentado em tela
        getSupportFragmentManager().beginTransaction().setCustomAnimations(android.R.anim.fade_in, android.R.anim.fade_out).replace(R.id.fragmentLayout, fragment).commit();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        // Referencia a variável com o menu de navegação através do ID
        bottomNavigationView = (BottomNavigationView) findViewById(R.id.nav_view);

        // Seta o objeto/fragmento de tela
        bottomNavigationView.setOnNavigationItemSelectedListener(btno);
    }
}