package com.example.ensinamente.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.Spinner;

import androidx.navigation.NavController;
import androidx.navigation.Navigation;
import androidx.navigation.ui.AppBarConfiguration;
import androidx.navigation.ui.NavigationUI;

import com.example.ensinamente.R;
import com.example.ensinamente.config.ConfiguracaoFireBase;
import com.example.ensinamente.databinding.ActivityPrincipalBinding;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.firebase.auth.FirebaseAuth;

public class PrincipalActivity extends AppCompatActivity {

    private FirebaseAuth autenticacao;
    private AppBarConfiguration appBarConfiguration;
    private ActivityPrincipalBinding binding;
    Spinner activityMetodo,
            activityCriticidade;
    private Button acessarTarefas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();

        binding = ActivityPrincipalBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        setSupportActionBar(binding.toolbar);

        activityMetodo = (Spinner) findViewById(R.id.spinner_tipos);
        activityCriticidade = (Spinner) findViewById(R.id.spinner_criticidade);
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_principal);
        appBarConfiguration = new AppBarConfiguration.Builder(navController.getGraph()).build();
        NavigationUI.setupActionBarWithNavController(this, navController, appBarConfiguration);
        acessarTarefas = findViewById(R.id.buttonAcessaTarefas);

        acessarTarefas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), ListaTarefasActivity.class);
                startActivity(intent);
            }
        });
        /*findViewById(R.id.floatingCadastraTarefa).setOnClickListener(view -> {
            startActivity(new Intent(this,FlashCardsActivity.class));
        });*/

        /*binding.fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Snackbar.make(view, "Replace with your own action", Snackbar.LENGTH_LONG)
                        .setAction("Action", null).show();
            }
        });*/
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_main, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch ( item.getItemId() ){
            case R.id.menuSair:
                 deslogarUsuario();
                 finish();
                break;
            case R.id.tarefas:
                 startActivity(new Intent(this, ListaTarefasActivity.class));
                 break;
            case R.id.visualizarMetas:
                 startActivity(new Intent(this, ListaMetasActivity.class));
                 break;
            case R.id.visualizarMetodos:
                 startActivity(new Intent(this, EscolhaMetodosActivity.class));
                 break;

        }
        return super.onOptionsItemSelected(item);
    }

    public void criarTarefa(View view){
          startActivity(new Intent(this,TarefaActivity.class));
    }
    public void criarMeta(View view){
         startActivity(new Intent(this,MetaActivity.class));
    }
    public void metodoFlashCards(View view){
        startActivity(new Intent(this, FlashCardsActivity.class));

    }
    public void metodoPomodoro(View view){
        startActivity(new Intent(this, PomodoroActivity.class));

    }
    public void deslogarUsuario(){
         try{
             //autenticacao.signOut();
             autenticacao.getInstance().signOut();
             this.finishAffinity();
         }catch (Exception e){
               e.printStackTrace();
         }

    }

    @Override
    public boolean onSupportNavigateUp() {
        NavController navController = Navigation.findNavController(this, R.id.nav_host_fragment_content_principal);
        return NavigationUI.navigateUp(navController, appBarConfiguration)
                || super.onSupportNavigateUp();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        finishAffinity();
    }
}