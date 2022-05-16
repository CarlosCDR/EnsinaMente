package com.example.ensinamente.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ensinamente.R;
import com.wajahatkarim3.easyflipview.EasyFlipView;

public class FlipAnimationActivity extends AppCompatActivity {

    TextView frente;
    TextView verso;
    private Button botaoVoltar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flip_animation);

        EasyFlipView easyFlipView = ( EasyFlipView ) findViewById ( R . id . easyFlipView );
        frente = findViewById(R.id.textFrente);
        verso = findViewById(R.id.textVerso);
        frente.setText(getIntent().getStringExtra("frenteIntent"));
        verso.setText(getIntent().getStringExtra("versoIntent"));
        botaoVoltar = findViewById(R.id.buttonIniciarCartao);

        botaoVoltar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                onBackPressed();
            }
        });
        /*easyFlipView.setOnFlipListener((easyFlipView1, newCurrentSide) -> {

        });*/
    }

}