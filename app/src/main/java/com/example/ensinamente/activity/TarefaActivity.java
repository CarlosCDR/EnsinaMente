package com.example.ensinamente.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.ensinamente.R;
import com.example.ensinamente.model.Tarefa;

import java.util.Arrays;
import java.util.List;

public class TarefaActivity extends AppCompatActivity {

    private Tarefa tarefa;
    private EditText campoTarefa;
    private EditText campoDisciplina;
    private String   campoSpinnerMetodo;
    private String   campoSpinnerCriticidade;


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


        campoTarefa = findViewById(R.id.nomeTarefaMeta);
        campoDisciplina = findViewById(R.id.tarefa);
        campoSpinnerMetodo = spinner.getSelectedItem().toString();
        campoSpinnerCriticidade = spinner1.getSelectedItem().toString();

        //Salva a tarefa e leva para a tela de metodos de estudo
        findViewById(R.id.floatingActionButton).setOnClickListener(view -> {
            String metodos1 = spinner.getSelectedItem().toString();
            String textoTarefa = campoTarefa.getText().toString();
            String textoDisciplina = campoDisciplina.getText().toString();

            //salvando a tarefa
            if(!textoTarefa.isEmpty()){
                if(!textoDisciplina.isEmpty()){
                    tarefa = new Tarefa();
                    tarefa.setNomeTarefa( campoTarefa.getText().toString());
                    tarefa.setDisciplina(campoDisciplina.getText().toString());
                    tarefa.setMetodo(campoSpinnerMetodo);
                    tarefa.setCriticidadeTarefa(campoSpinnerCriticidade);

                    tarefa.salvarTarefa();
                } else{
                    Toast.makeText(TarefaActivity.this,
                            "A Disciplina não foi preenchida!",
                            Toast.LENGTH_SHORT).show();
                }

            }else{
                Toast.makeText(TarefaActivity.this,
                        "Nome da Tarefa não foi preenchido!",
                        Toast.LENGTH_SHORT).show();
            }



            //levando para os metodos basedos na seleção do spinner
            if(metodos1.equals("FlashCards")){
                Intent intent = new Intent(getApplicationContext(),FlashCardsActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), metodos1, Toast.LENGTH_SHORT ).show();
            }if(metodos1.equals("Pomodoro")){
                Intent intent = new Intent(getApplicationContext(),PomodoroActivity.class);
                startActivity(intent);
                Toast.makeText(getApplicationContext(), metodos1, Toast.LENGTH_SHORT ).show();
            }

        });

        //volta a menu principal do app
        findViewById(R.id.floatingActionButtonVoltar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivity(intent);
            }
        });
    }

}