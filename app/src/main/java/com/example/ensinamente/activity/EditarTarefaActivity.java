package com.example.ensinamente.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ensinamente.R;
import com.example.ensinamente.config.ConfiguracaoFireBase;
import com.example.ensinamente.helper.Base64Custom;
import com.example.ensinamente.model.FlashCards;
import com.example.ensinamente.model.Tarefa;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class EditarTarefaActivity extends AppCompatActivity {

    private TextView campoNomeTarefa;
    private EditText campoDisciplina;
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private String recebeNovoNomeTarefa;
    private String recebeNomeTarefa;
    private Tarefa tarefa;
    private DatabaseReference databaseReference;
    private ImageView voltaListaTarefas;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_tarefa);

        List<String> metodos = Arrays.asList("FlashCards", "Pomodoro");
        final Spinner spinner = findViewById(R.id.spinner_tipos);

        List<String> criticidades = Arrays.asList("Alta", "Médio", "Baixa");
        final Spinner spinner1 = findViewById(R.id.spinner_criticidade);

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, metodos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        ArrayAdapter adapter1 = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, criticidades);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner1.setAdapter(adapter1);

        Intent intent = getIntent();
        Bundle parametros = intent.getExtras();

        //recebendo parametro com o nome da tarefa da  activity Tarefa
        if (parametros != null) {
            String nome = parametros.getString("chave_nomeTarefa");
            String disciplina = parametros.getString("chave_disciplina");
            campoNomeTarefa = findViewById(R.id.nomeTarefa);
            campoDisciplina = findViewById(R.id.tarefaDisciplina);
            campoNomeTarefa.setText(nome);
            campoDisciplina.setText(disciplina);

        }
        recebeNomeTarefa = campoNomeTarefa.getText().toString();
        voltaListaTarefas = findViewById(R.id.imageViewVoltaListaTarefas);
        findViewById(R.id.floatingActionEditaTarefas).setOnClickListener(view -> {

            String campoSpinnerMetodo = spinner.getSelectedItem().toString();
            String campoSpinnerCriticidade = spinner1.getSelectedItem().toString();
            if(!recebeNomeTarefa.isEmpty()){
                updateTarefa(campoSpinnerMetodo, campoSpinnerCriticidade);
            }else{
                Toast.makeText(EditarTarefaActivity.this,
                        "Tarefa não foi editada!",
                        Toast.LENGTH_SHORT).show();
            }

        });

        voltaListaTarefas.setOnClickListener(view -> {
            startActivity(new Intent(getApplicationContext(),ListaTarefasActivity.class));
            finish();
        });

    }

    public void updateTarefa(String campoSinnerMetodo, String campoSpinnerCriticidade){

        String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        String campotextoNomeTarefa = campoNomeTarefa.getText().toString();
        String campotextoDisciplina = campoDisciplina.getText().toString();
        String nomeTarefa = recebeNomeTarefa;
        String idTarefa = recebeNomeTarefa;
        String disciplina = campoDisciplina.getText().toString();

        DatabaseReference tarefaRef = firebaseRef.child("tarefa").child(idUsuario).child(idTarefa);


        if(!campotextoNomeTarefa.isEmpty() || !campotextoDisciplina.isEmpty()){

            HashMap hashMap = new HashMap();
            hashMap.put("nomeTarefa", nomeTarefa);
            hashMap.put("disciplina", disciplina);
            hashMap.put("metodo", campoSinnerMetodo);
            hashMap.put("criticidadeTarefa", campoSpinnerCriticidade);
            tarefaRef.setValue(hashMap);
            Toast.makeText(EditarTarefaActivity.this,"Tarefa alterada",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),ListaTarefasActivity.class));
            finish();

        }else{
            Toast.makeText(EditarTarefaActivity.this,
                    "Campos Vazios!",
                    Toast.LENGTH_SHORT).show();
        }

    }
}