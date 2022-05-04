package com.example.ensinamente.model;

import com.example.ensinamente.config.ConfiguracaoFireBase;
import com.example.ensinamente.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class FlashCards {
    private String nomeTarefa;
    private String frente;
    private String verso;


    public FlashCards() {
    }

    public void salvaMetodo(String nomeTarefaEscolhida){
        FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
        String idUsuario = Base64Custom.codificarBase64( autenticacao.getCurrentUser().getEmail() );
        String tarefaNome = nomeTarefaEscolhida;

        DatabaseReference firebase = ConfiguracaoFireBase.getFirebaseDatabase();
        firebase.child("flashCards")
                .child(idUsuario)
                .child(tarefaNome)
                .push()
                .setValue(this);
    }


    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public String getFrente() {
        return frente;
    }

    public void setFrente(String frente) {
        this.frente = frente;
    }

    public String getVerso() {
        return verso;
    }

    public void setVerso(String verso) {
        this.verso = verso;
    }
}
