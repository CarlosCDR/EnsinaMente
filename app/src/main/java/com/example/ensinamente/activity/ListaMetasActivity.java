package com.example.ensinamente.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import com.example.ensinamente.R;
import com.example.ensinamente.config.ConfiguracaoFireBase;
import com.example.ensinamente.helper.Base64Custom;
import com.example.ensinamente.model.Meta;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ListaMetasActivity extends AppCompatActivity {
    List<Meta> metas;
    RecyclerView recyclerView;
    MetasAdpter metasAdpter;
    DatabaseReference databaseReference;
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private Button voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lista_metas);
        recyclerView = findViewById(R.id.recyclerViewMetas);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        metas = new ArrayList<>();
        databaseReference = ConfiguracaoFireBase.getFirebaseDatabase();
        voltar = findViewById(R.id.buttonVoltarMeta);

        String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        databaseReference.child("meta").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.hasChild(idUsuario)){
                    databaseReference.child("meta").child(idUsuario).addListenerForSingleValueEvent(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot snapshot) {
                            for(DataSnapshot dn:snapshot.getChildren()){
                                Meta m = dn.getValue(Meta.class);
                                metas.add(m);
                            }
                            metasAdpter = new MetasAdpter(metas);
                            recyclerView.setAdapter(metasAdpter);
                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError error) {

                        }
                    });

                }else{
                    Toast.makeText(ListaMetasActivity.this,
                            "Nenhuma Meta Cadastrada!",
                            Toast.LENGTH_SHORT).show();
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });

        voltar.setOnClickListener(view -> voltarPrincipal());

    }

    public void voltarPrincipal(){
        startActivity(new Intent(this, PrincipalActivity.class));
        finish();
    }

}