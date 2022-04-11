package com.example.ensinamente.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.Toast;
import com.example.ensinamente.R;
import java.util.Arrays;
import java.util.List;

public class TarefaActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tarefa2);

        List<String> metodos = Arrays.asList("FlashCards",        "Pomodoro");
        final Spinner spinner = findViewById(R.id.spinner_metodo);

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, metodos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);

        findViewById(R.id.floatingActionButton).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String metodos = spinner.getSelectedItem().toString();
                if(metodos.equals("FlashCards")){
                    Intent intent = new Intent(getApplicationContext(),FlashCardsActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), metodos, Toast.LENGTH_SHORT ).show();
                }if(metodos.equals("Pomodoro")){
                    Intent intent = new Intent(getApplicationContext(),PomodoroActivity.class);
                    startActivity(intent);
                    Toast.makeText(getApplicationContext(), metodos, Toast.LENGTH_SHORT ).show();
                }
            }
        });

        findViewById(R.id.floatingActionButtonVoltar).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), PrincipalActivity.class);
                startActivity(intent);
            }
        });
    }
}