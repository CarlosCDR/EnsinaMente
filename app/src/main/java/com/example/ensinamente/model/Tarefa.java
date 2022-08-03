package com.example.ensinamente.model;

import com.example.ensinamente.config.ConfiguracaoFireBase;
import com.example.ensinamente.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

import java.util.HashMap;
import java.util.List;

public class Tarefa {


    private String nomeTarefa;
    private String disciplina;
    private String metodo;
    private String criticidadeTarefa;


    public void salvarTarefa(String nomeTarefa){

        FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
        String idUsuario = Base64Custom.codificarBase64( autenticacao.getCurrentUser().getEmail() );

        DatabaseReference firebase = ConfiguracaoFireBase.getFirebaseDatabase();
        firebase.child("tarefa")
                .child( idUsuario )
                .child(nomeTarefa)
                .setValue(this);
    }



    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public String getDisciplina() {
        return disciplina;
    }

    public void setDisciplina(String disciplina) {
        this.disciplina = disciplina;
    }

    public String getMetodo() {
        return metodo;
    }

    public void setMetodo(String metodo) {
        this.metodo = metodo;
    }

    public String getCriticidadeTarefa() {
        return criticidadeTarefa;
    }

    public void setCriticidadeTarefa(String criticidadeTarefa) {
        this.criticidadeTarefa = criticidadeTarefa;
    }
}
