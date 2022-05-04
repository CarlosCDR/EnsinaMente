package com.example.ensinamente.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.example.ensinamente.R;
import com.example.ensinamente.model.Meta;

public class MetaActivity extends AppCompatActivity {

    private Meta meta;
    private EditText campoNomeTarefa;
    private EditText campoMotivacao;
    private EditText campoData;
    private String textoNomeTarefa;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.ensinamente.R.layout.activity_meta);

        campoNomeTarefa = findViewById(R.id.nomeTarefaMeta);
        campoMotivacao = findViewById(R.id.motivacaoTarefa);
        campoData = findViewById(R.id.data);
        textoNomeTarefa = campoNomeTarefa.getText().toString();

        findViewById(R.id.floatingCadastraMeta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view){
                  if(!textoNomeTarefa.isEmpty()){
                      salvar();
                  }else{
                      Toast.makeText(MetaActivity.this,
                              "Nome tarefa não foi preenchido!",
                              Toast.LENGTH_SHORT).show();
                  }
            }
        });

    }

    //salva  a meta do usuário
    public void salvar(){

       meta = new Meta();
       meta.setNomeTarefa(campoNomeTarefa.getText().toString());
       meta.setMotivacao(campoMotivacao.getText().toString());
       meta.setData(campoData.getText().toString());

       meta.salvarMeta();

        Toast.makeText(this,
                "Meta cadastrada, agora e com você!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), MetaActivity.class);
        startActivity(intent);

    }

}