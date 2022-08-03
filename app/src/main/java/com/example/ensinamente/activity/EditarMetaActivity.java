package com.example.ensinamente.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ensinamente.R;
import com.example.ensinamente.config.ConfiguracaoFireBase;
import com.example.ensinamente.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;

public class EditarMetaActivity extends AppCompatActivity {

    private EditText campoNomeTarefa;
    private EditText campoMotivacao;
    private EditText campoData;
    private String recebeNomeTarefa;
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebaseDatabase();
    private ImageView voltaListaTarefas;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_editar_meta);

        Intent intent = getIntent();
        Bundle parametros = intent.getExtras();

        //recebendo parametro com o nome da tarefa da  activity Tarefa
        if (parametros != null) {
            String nome = parametros.getString("chave_nomeTarefa");
            String motivacao = parametros.getString("chave_motivacao");
            String data = parametros.getString("chave_data");
            campoNomeTarefa = findViewById(R.id.nomeTarefa);
            campoMotivacao = findViewById(R.id.motivacaoTarefa);
            campoData = findViewById(R.id.data);
            campoNomeTarefa.setText(nome);
            campoMotivacao.setText(motivacao);
            campoData.setText(data);
        }
        recebeNomeTarefa = campoNomeTarefa.getText().toString();
        voltaListaTarefas = findViewById(R.id.imageViewVoltaListaMetas);
        findViewById(R.id.floatingEditarMetas).setOnClickListener(view -> {

            if(!recebeNomeTarefa.isEmpty()){
                updateMeta();
            }else{
                Toast.makeText(EditarMetaActivity.this,
                        "Meta não foi editada!",
                        Toast.LENGTH_SHORT).show();
            }

        });
        voltaListaTarefas.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                startActivity(new Intent(getApplicationContext(), ListaMetasActivity.class));
                finish();
            }
        });
    }
    public void updateMeta(){

        String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        String campotextoNomeTarefa = recebeNomeTarefa;
        String campotextoMotivacao = campoMotivacao.getText().toString();
        String nomeTarefa = recebeNomeTarefa;
        String motivacao = campotextoMotivacao;
        String data = campoData.getText().toString();

        DatabaseReference tarefaRef = firebaseRef.child("meta").child(idUsuario).child(nomeTarefa);


        if(!campotextoNomeTarefa.isEmpty() || !campotextoMotivacao.isEmpty()){

            HashMap hashMap = new HashMap();
            hashMap.put("nomeTarefa", nomeTarefa);
            hashMap.put("motivacao", motivacao);
            hashMap.put("data", data);

            tarefaRef.setValue(hashMap);
            Toast.makeText(EditarMetaActivity.this,"Meta alterada",Toast.LENGTH_SHORT).show();
            startActivity(new Intent(getApplicationContext(),ListaMetasActivity.class));
            finish();

        }else{
            Toast.makeText(EditarMetaActivity.this,
                    "Campos Vazios!",
                    Toast.LENGTH_SHORT).show();
        }

    }

}