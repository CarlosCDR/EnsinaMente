package com.example.ensinamente.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ensinamente.R;

public class IntroducaoActivity extends AppCompatActivity {

    private Button buttonConfirma;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_introducao);

        buttonConfirma = findViewById(R.id.buttonConfirmaOk);

        buttonConfirma.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivity(intent);
                finish();
            }
        });


    }


}