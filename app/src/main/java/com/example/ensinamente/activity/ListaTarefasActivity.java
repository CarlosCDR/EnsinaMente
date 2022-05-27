package com.example.ensinamente.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ensinamente.R;
import com.example.ensinamente.config.ConfiguracaoFireBase;
import com.example.ensinamente.helper.Base64Custom;
import com.example.ensinamente.model.FlashCards;
import com.example.ensinamente.model.Tarefa;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaTarefasActivity extends AppCompatActivity {

    List<Tarefa> tarefas;
    RecyclerView recyclerView;
    TarefaAdpter tarefasAdpter;
    DatabaseReference databaseReference;
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private Button voltar;


    //Query listaTarefasQuery = tarefas.orderByChild("nomeTarefa");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_tarefas);
        recyclerView = findViewById(R.id.recyclerViewTarefa);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        tarefas = new ArrayList<>();
        databaseReference = ConfiguracaoFireBase.getFirebaseDatabase();
        voltar = findViewById(R.id.buttonVoltarTaf);

        String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        String verificaUsuario = idUsuario;
        databaseReference.child("tarefa").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                    if(snapshot.hasChild(idUsuario)){
                        databaseReference.child("tarefa").child(idUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot snapshot) {
                                for(DataSnapshot dn:snapshot.getChildren()){
                                    Tarefa t = dn.getValue(Tarefa.class);
                                    tarefas.add(t);

                                    tarefasAdpter = new TarefaAdpter(tarefas);
                                    recyclerView.setAdapter(tarefasAdpter);
                                    tarefasAdpter.OnRecyclerViewClickListener(new TarefaAdpter.OnRecyclerViewClickListener() {
                                        @Override
                                        public void OnItemClick(int position) {
                                            Intent intent = new Intent(getApplicationContext(), EscolhaMetodosActivity.class);
                                            startActivity(intent);
                                        }
                                    });

                                }
                            }

                            @Override
                            public void onCancelled(@NonNull DatabaseError error) {

                            }
                        });
                    }else{
                        Toast.makeText(ListaTarefasActivity.this,
                                "Nenhuma Tarefa Cadastrada!",
                                Toast.LENGTH_SHORT).show();
                    }





            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        voltar.setOnClickListener(view -> voltarPrincipal());
        //flashCards.setOnClickListener(view -> flashCardsMetodo());
        //pomodoro.setOnClickListener(view -> pomodoroMetodo());

    }

    public void voltarPrincipal(){
        startActivity(new Intent(this, PrincipalActivity.class));
        finish();
    }


}