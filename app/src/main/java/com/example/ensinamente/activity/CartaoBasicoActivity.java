package com.example.ensinamente.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ensinamente.R;
import com.example.ensinamente.config.ConfiguracaoFireBase;
import com.example.ensinamente.helper.Base64Custom;
import com.example.ensinamente.model.FlashCards;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class CartaoBasicoActivity extends AppCompatActivity{


    List<FlashCards> cartoes;
    RecyclerView recyclerView;
    CartoesAdpter cartoesAdpter;
    DatabaseReference databaseReference;
    private DatabaseReference firebaseRef = ConfiguracaoFireBase.getFirebaseDatabase();
    private FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
    private String nomeTarefa;
    private TextView recebeNomeTarefa;
    private String pegaNomeTarefa;
    private ImageButton voltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cartao_basico);
        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        cartoes = new ArrayList<>();
        databaseReference = ConfiguracaoFireBase.getFirebaseDatabase();

        String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());
        Intent intent = getIntent();
        Bundle parametros = intent.getExtras();
        if (parametros != null) {
            String nome = parametros.getString("chave_nomeTarefa");
            nomeTarefa = nome;
        }
        recebeNomeTarefa = findViewById(R.id.tvRecebeNomeTarefa);
        pegaNomeTarefa = nomeTarefa;
        recebeNomeTarefa.setText(pegaNomeTarefa);
        voltar = findViewById(R.id.imageButtonVoltar);

        databaseReference.child("flashCards").child(idUsuario).child(nomeTarefa).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                for(DataSnapshot dn:snapshot.getChildren()){
                    FlashCards f = dn.getValue(FlashCards.class);
                    cartoes.add(f);
                }
                cartoesAdpter = new CartoesAdpter(cartoes);
                recyclerView.setAdapter(cartoesAdpter);

                cartoesAdpter.OnRecyclerViewClickListener(new CartoesAdpter.OnRecyclerViewClickListener() {
                    @Override
                    public void OnItemClick(int position) {
                        Intent intent = new Intent(CartaoBasicoActivity.this, FlipAnimationActivity.class);
                        intent.putExtra("frenteIntent", cartoes.get(position).getFrente() );
                        intent.putExtra("versoIntent", cartoes.get(position).getVerso() );
                        startActivity(intent);
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
       voltar.setOnClickListener(view -> {
           voltar();
       });
    }

    public void voltar(){
        onBackPressed();
    }

}