package com.example.ensinamente.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ensinamente.R;
import com.example.ensinamente.model.Meta;

public class MetaActivity extends AppCompatActivity {

    private Meta meta;
    private EditText campoNomeTarefa;
    private EditText campoMotivacao;
    private EditText campoData;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(com.example.ensinamente.R.layout.activity_meta);

         campoNomeTarefa = findViewById(R.id.nomeTarefa);
         campoMotivacao = findViewById(R.id.motivacaoTarefa);
         campoData = findViewById(R.id.data);


        findViewById(R.id.floatingEditarMetas).setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View view){
                String textoNomeTarefa = campoNomeTarefa.getText().toString();
                String textoMotivacao = campoMotivacao.getText().toString();
                String textoData = campoData.getText().toString();
                String nomeTarefa = textoNomeTarefa;
                  if(!textoNomeTarefa.isEmpty()){
                      meta = new Meta();
                      meta.setNomeTarefa(textoNomeTarefa);
                      meta.setMotivacao(textoMotivacao);
                      meta.setData(textoData);
                      salvar(nomeTarefa);
                  }else{
                      Toast.makeText(MetaActivity.this,
                              "Nome tarefa não foi preenchido!",
                              Toast.LENGTH_SHORT).show();
                  }
            }
        });
        findViewById(R.id.voltaPrincipaMeta).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), PrincipalActivity.class));
            }
        });

    }

    //salva  a meta do usuário
    public void salvar(String nomeTarefa){

       meta = new Meta();
       meta.setNomeTarefa(campoNomeTarefa.getText().toString());
       meta.setMotivacao(campoMotivacao.getText().toString());
       meta.setData(campoData.getText().toString());

       meta.salvarMeta(nomeTarefa);

        Toast.makeText(this,
                "Meta cadastrada, agora e com você!", Toast.LENGTH_SHORT).show();

        Intent intent = new Intent(getApplicationContext(), MetaActivity.class);
        startActivity(intent);
        finish();

    }

}