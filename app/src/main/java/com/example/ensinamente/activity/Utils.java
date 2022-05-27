package com.example.ensinamente.activity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

public class Utils {

    private  static  Utils instance;
    private  static ArrayList<String> quotes=new ArrayList<>();

    private Utils() {
        quotes.add("Tudo que um sonho precisa para ser realizado \n é alguem que acredite nele");
        quotes.add("Não espere, ponha em prática!");
        quotes.add("Mesmo que pareça difícil, não pare!");
        quotes.add("Só trabalhando é possível trilhar o caminho!");
        quotes.add("Não espere que as respostas caiam do céu");
        quotes.add("Não é a carga que o derruba, mas a maneira como você carrega.");
        quotes.add("Todas as grandes conquistas exigem tempo");
    }

    public static Utils getInstance() {
        if(instance==null)
            instance=new Utils();

        return instance;
    }
    public String getData(){
        Calendar calendar=Calendar.getInstance();
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("dd-MMM-yyyy");
        String date=simpleDateFormat.format(calendar.getTime());
        return date ;
    }

    public static ArrayList<String> getQuotes() {
        return quotes;
    }

}
