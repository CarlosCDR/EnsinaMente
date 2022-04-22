package com.example.ensinamente.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.example.ensinamente.R;

import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.List;

public class FlashCardsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_flash_cards);


        List<String> cartoesTipos = Arrays.asList("Basico", "Invertido");
        final Spinner spinner = findViewById(R.id.spinner_tipos);

        ArrayAdapter adapter = new ArrayAdapter(getApplicationContext(), android.R.layout.simple_spinner_item, cartoesTipos);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinner.setAdapter(adapter);






    }
}