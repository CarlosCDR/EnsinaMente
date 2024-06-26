package com.example.ensinamente.activity;


import androidx.annotation.NonNull;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;

import android.widget.TextView;
import android.widget.Toast;

import com.example.ensinamente.R;
import com.example.ensinamente.config.ConfiguracaoFireBase;
import com.example.ensinamente.helper.Base64Custom;
import com.example.ensinamente.model.FlashCards;

import com.google.firebase.auth.FirebaseAuth;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;


public class FlashCardsActivity extends AppCompatActivity {

    private EditText campoNomeTarefa;
    private EditText campoFrente;
    private EditText campoVerso;
    private FlashCards flashCards;
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private String recebeNomeTarefa;
    private String recebeNovoNomeTarefa;
    private TextView visualizarCartoes;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_cards);

        List<String> cartoesTipos = Arrays.asList("Basico", "Invertido");
        final Spinner spinner = findViewById(R.id.spinner_tipos);

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, cartoesTipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        Intent intent = getIntent();
        Bundle parametros = intent.getExtras();

        visualizarCartoes = findViewById(R.id.textViewCartao);
        //recebendo parametro com o nome da tarefa da  activity Tarefa
       if (parametros != null) {
            String nome = parametros.getString("chave_nomeTarefa");
            campoNomeTarefa = findViewById(R.id.tarefaDisciplina);
            campoNomeTarefa.setText(nome);
        }

        campoFrente = findViewById(R.id.frente);
        campoVerso = findViewById(R.id.verso);
        campoNomeTarefa = findViewById(R.id.tarefaDisciplina);

        recebeNomeTarefa = campoNomeTarefa.getText().toString();

        findViewById(R.id.floatingActionEditaTarefas).setOnClickListener(view -> {

            campoNomeTarefa = findViewById(R.id.tarefaDisciplina);
            recebeNovoNomeTarefa = campoNomeTarefa.getText().toString();

            if( recebeNovoNomeTarefa != recebeNomeTarefa){
                recebeNomeTarefa = recebeNovoNomeTarefa;
            }
            if(!recebeNomeTarefa.isEmpty()){
                recuperaNomeTarefa(recebeNomeTarefa);
            }else{
                Toast.makeText(FlashCardsActivity.this,
                        "Nome tarefa não foi preenchido!",
                        Toast.LENGTH_SHORT).show();
            }

        });

        visualizarCartoes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                String metodos1 = spinner.getSelectedItem().toString();
                String textoTarefa = campoNomeTarefa.getText().toString();
                String nomeTarefa = campoNomeTarefa.getText().toString();


                if(!textoTarefa.isEmpty()){

                    if(metodos1.equals("Basico")){
                        Intent intent = new Intent(getApplicationContext(),CartaoBasicoActivity.class);
                        Bundle parametros = new Bundle();
                        parametros.putString("chave_nomeTarefa", nomeTarefa);
                        intent.putExtras(parametros);
                        startActivity(intent);
                        Toast.makeText(getApplicationContext(), metodos1, Toast.LENGTH_SHORT ).show();
                    }if(metodos1.equals("Invertido")){
                        Intent intent1 = new Intent(getApplicationContext(), CartaoInvertidoActivity.class);
                        Bundle parametros = new Bundle();
                        parametros.putString("chave_nomeTarefa", nomeTarefa);
                        intent1.putExtras(parametros);
                        startActivity(intent1);
                        Toast.makeText(getApplicationContext(), metodos1, Toast.LENGTH_SHORT ).show();
                    }
                }else{
                    Toast.makeText(FlashCardsActivity.this,
                            "Nome tarefa não foi preenchido!",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

        //volta a menu principal do app
        findViewById(R.id.imageViewVoltaPrincipalFlashCards).setOnClickListener(view -> {
            startActivity(new Intent(this, PrincipalActivity.class));
            finish();
            onBackPressed();
        });

    }

    public void recuperaNomeTarefa(String recebeNomeTarefa) {

        String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        DatabaseReference tarefaRef = firebaseRef.child("flashCards").child(idUsuario);
        String textoFrente = campoFrente.getText().toString();
        String textoVerso = campoVerso.getText().toString();

        tarefaRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if (snapshot.hasChild(recebeNomeTarefa)) {
                        if(!textoFrente.isEmpty() || !textoVerso.isEmpty()){
                            flashCards = new FlashCards();
                            flashCards.setFrente(campoFrente.getText().toString());
                            flashCards.setVerso(campoVerso.getText().toString());

                            flashCards.salvaMetodo(recebeNomeTarefa);
                            Toast.makeText(FlashCardsActivity.this,
                                    "Tarefa já existe, sera criado um novo cartão ao seu baralho",
                                    Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(FlashCardsActivity.this,
                                    "Cartões Estão Vazios!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }else {
                        salvaTarefa();
                    }
                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) { }
            });

    }

    public void salvaTarefa() {

        String textonomeTarefa = campoNomeTarefa.getText().toString();
        String nomeTarefa = campoNomeTarefa.getText().toString();
        String textoFrente = campoFrente.getText().toString();
        String textoVerso = campoVerso.getText().toString();

        //salvando a tarefa
        if (!textonomeTarefa.isEmpty() ) {
            if (!textonomeTarefa.isEmpty()) {
               if(!textoFrente.isEmpty() && !textoVerso.isEmpty()){
                   flashCards = new FlashCards();
                   flashCards.setFrente(campoFrente.getText().toString());
                   flashCards.setVerso(campoVerso.getText().toString());

                   flashCards.salvaMetodo(nomeTarefa);
                   Toast.makeText(FlashCardsActivity.this,
                           "Tarefa  não existe, sera criado um novo baralho",
                           Toast.LENGTH_SHORT).show();
                   Toast.makeText(FlashCardsActivity.this,
                           "Cartão criado!",
                           Toast.LENGTH_SHORT).show();
               }else{
                   Toast.makeText(FlashCardsActivity.this,
                           "Cartões Estão Vazios!",
                           Toast.LENGTH_SHORT).show();
               }

            }else {
                Toast.makeText(FlashCardsActivity.this,
                        "A Disciplina não foi preenchida!",
                        Toast.LENGTH_SHORT).show();
            }
        }else {
            Toast.makeText(FlashCardsActivity.this,
                    "Nome da Tarefa não foi preenchido!",
                    Toast.LENGTH_SHORT).show();
        }

    }


}