package com.example.ensinamente.activity;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ensinamente.R;

public class PomodoroHomeActivity extends AppCompatActivity {

    Intent intent;
    public static final String number="Value";
    public static final String LastIndex="Index";
    public static final String myPref="pref";
    public static final String Day="day";
    public static String mintAchive = "mints" ;
    private ImageButton voltar;
    SharedPreferences Preferences;
    private Button pausa;
    private Button pausaLonga;
    private String pause = "0";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pomodoro_home);

        intent=new Intent(PomodoroHomeActivity.this,ClockActivity.class);
        Preferences=getSharedPreferences(myPref,MODE_PRIVATE);
        voltar = findViewById(R.id.imageButtonVoltarPrincipal);
        pausa = findViewById(R.id.buttonPausa);
        pausaLonga = findViewById(R.id.buttonPausaLonga);

        TextView Slider=findViewById(R.id.textslide);
        TextView today=findViewById(R.id.today);
        TextView mints=findViewById(R.id.mints);
        today.setText(Utils.getInstance().getData());


        int i=Preferences.getInt(LastIndex,-1);
        if(i==-1)
        {
            // first time when you open app
            SharedPreferences.Editor editor;
            editor = Preferences.edit();
            editor.putInt(LastIndex,0);
            editor.putString(Day,Utils.getInstance().getData());
            editor.putInt(mintAchive,0);
            today.setText(Utils.getInstance().getData());
            editor.commit();
        }
        else{

            if(i==Utils.getInstance().getQuotes().size())
                i=0;

            Slider.setText(Utils.getInstance().getQuotes().get(i));
            i++;
            SharedPreferences.Editor editor=Preferences.edit();
            editor.putInt(LastIndex,i);
            editor.commit();
        }

        int achivements=Preferences.getInt(mintAchive,-1);

        if(achivements != -1)
        {
            if(Preferences.getString(Day,"").equals(Utils.getInstance().getData()))
            {
                // same day
                if(achivements == 5 || achivements == 10){
                    achivements = 0;
                }else{
                    mints.setText(achivements+" M");
                }

            }
            else {
                // second day
                SharedPreferences.Editor editor;
                editor = Preferences.edit();
                editor.putString(Day,Utils.getInstance().getData());
                editor.putInt(mintAchive,0);
                editor.commit();
            }
        }
        voltar.setOnClickListener(view -> {
            voltar();
        });
        pausa.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pausa(view);
            }
        });
        pausaLonga.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                pausaLonga(view);
            }
        });
    }


    // on click button
    public void TewntyFive(View view)
    {
        intent.putExtra(number,25);
        startActivity(intent);
    }
    public void ThrityFive(View view)
    {
        intent.putExtra(number,35);
        startActivity(intent);

    }
    public void Hour(View view)
    {
        intent.putExtra(number,60);
        startActivity(intent);

    }
    public void pausa(View view){
        intent.putExtra(number, 5);
        startActivity(intent);
    }
    public void pausaLonga(View view){
        intent.putExtra(number, 10);
        startActivity(intent);
    }

    public void voltar(){
        /*SharedPreferences.Editor editor;
        editor = Preferences.edit();
        editor.putString(Day,Utils.getInstance().getData());
        editor.putInt(mintAchive,0);
        editor.commit();*/
        startActivity(new Intent(this, PrincipalActivity.class));
        finish();
    }

}