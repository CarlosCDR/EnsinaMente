package com.example.ensinamente.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.ensinamente.R;
import com.example.ensinamente.config.ConfiguracaoFireBase;
import com.example.ensinamente.helper.Base64Custom;
import com.example.ensinamente.model.Tarefa;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.List;

public class TarefaActivity extends AppCompatActivity {

    private Tarefa tarefa;
    private EditText campoTarefa;
    private EditText campoDisciplina;
    private String   campoSpinnerMetodo;
    private String   campoSpinnerCriticidade;
    private FirebaseAuth auth;
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebaseDatabase();
    private String textoNomeTarefa;
    private List<Tarefa> tarefas;
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private String key;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa2);

        List<String> metodos = Arrays.asList("FlashCards",        "Pomodoro");
        final Spinner spinner = findViewById(R.id.spinner_tipos);

        List<String> criticidades = Arrays.asList("Alta", "Médio", "Baixa");
        final Spinner spinner1 = findViewById(R.id.spinner_criticidade);

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, metodos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        ArrayAdapter adapter1 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, criticidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner1.setAdapter(adapter1);

        campoTarefa = findViewById(R.id.nomeTarefa);
        campoDisciplina = findViewById(R.id.tarefaDisciplina);


        //Salva a tarefa e leva para a tela de metodos de estudo
        findViewById(R.id.floatingActionEditaTarefas).setOnClickListener(view -> {
            String metodos1 = spinner.getSelectedItem().toString();
            String textoTarefa = campoTarefa.getText().toString();
            String textoDisciplina = campoDisciplina.getText().toString();
            String nomeTarefa = campoTarefa.getText().toString();
            campoSpinnerMetodo = spinner.getSelectedItem().toString();
            campoSpinnerCriticidade = spinner1.getSelectedItem().toString();

            String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
            DatabaseReference tarefaRef = firebaseRef.child("tarefa").child(idUsuario);
            tarefaRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(!textoTarefa.isEmpty() || !textoDisciplina.isEmpty()){
                        if(snapshot.hasChild(nomeTarefa)){
                            Toast.makeText(TarefaActivity.this, "Tarefa já existe",Toast.LENGTH_SHORT).show();
                        }else{
                            //salvando a tarefa
                            if(!textoTarefa.isEmpty()){
                                if(!textoDisciplina.isEmpty()){

                                    tarefa = new Tarefa();
                                    tarefa.setNomeTarefa( campoTarefa.getText().toString());
                                    tarefa.setDisciplina(campoDisciplina.getText().toString());
                                    tarefa.setMetodo(campoSpinnerMetodo);
                                    tarefa.setCriticidadeTarefa(campoSpinnerCriticidade);

                                    tarefa.salvarTarefa(nomeTarefa);

                                    //levando para os metodos basedos na seleção do spinner
                                    if(metodos1.equals("FlashCards")){
                                        Intent intent = new Intent(getApplicationContext(),FlashCardsActivity.class);
                                        Bundle parametros = new Bundle();
                                        parametros.putString("chave_nomeTarefa", nomeTarefa);
                                        intent.putExtras(parametros);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(getApplicationContext(), metodos1, Toast.LENGTH_SHORT ).show();
                                    }if(metodos1.equals("Pomodoro")){
                                        Intent intent = new Intent(getApplicationContext(),PomodoroActivity.class);
                                        startActivity(intent);
                                        finish();
                                        Toast.makeText(getApplicationContext(), metodos1, Toast.LENGTH_SHORT ).show();
                                    }
                                }else{
                                    Toast.makeText(TarefaActivity.this,
                                            "A Disciplina não foi preenchida!",
                                            Toast.LENGTH_SHORT).show();
                                }
                            }else{
                                Toast.makeText(TarefaActivity.this,
                                        "Nome da Tarefa não foi preenchido!",
                                        Toast.LENGTH_SHORT).show();
                            }
                        }
                    }else{
                        Toast.makeText(TarefaActivity.this, "Preencha os Campos!", Toast.LENGTH_SHORT).show();
                    }

                }
                @Override
                public void onCancelled(@NonNull DatabaseError error) {

                }
            });

        });

        //volta a menu principal do app
        findViewById(R.id.imageViewVoltaListaTarefas).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }



}