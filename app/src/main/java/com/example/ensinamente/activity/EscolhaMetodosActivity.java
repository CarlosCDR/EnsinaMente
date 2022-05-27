package com.example.ensinamente.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ensinamente.R;

public class EscolhaMetodosActivity extends AppCompatActivity {

    private ImageView flashCards;
    private ImageView pomodoro;
    private TextView tvflashCards;
    private TextView tvPomodoro;
    private TextView tvSRS;
    private ImageView imageViewSRS;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_escolha_metodos);

        flashCards = findViewById(R.id.imageViewFlashCards);
        pomodoro = findViewById(R.id.imageViewPomodoro);
        tvflashCards = findViewById(R.id.textViewFlashCards);
        tvPomodoro = findViewById(R.id.textViewPomodoro);
        imageViewSRS = findViewById(R.id.imageViewSRS);
        tvSRS = findViewById(R.id.textViewSRS);

        flashCards.setOnClickListener(view -> imageViewFlashCards());
        pomodoro.setOnClickListener(view -> imageViewPomodoro());
        tvflashCards.setOnClickListener(view -> tvFlashCards());
        tvPomodoro.setOnClickListener(view -> tvPomodoro());
        imageViewSRS.setOnClickListener(view -> Toast.makeText(EscolhaMetodosActivity.this,
                "Em breve será implementado", Toast.LENGTH_SHORT).show());
        tvSRS.setOnClickListener(view -> Toast.makeText(EscolhaMetodosActivity.this,
                "Em breve será implementado", Toast.LENGTH_SHORT).show());
    }

    public void imageViewFlashCards(){
        startActivity(new Intent(this, FlashCardsActivity.class));
    }
    public void imageViewPomodoro(){
        startActivity(new Intent(this, PomodoroActivity.class));
    }
    public void tvFlashCards(){
        startActivity(new Intent(this, FlashCardsActivity.class));
    }
    public void tvPomodoro(){
        startActivity(new Intent(this, PomodoroActivity.class));
    }


}