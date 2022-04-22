package com.example.ensinamente.model;

import com.example.ensinamente.config.ConfiguracaoFireBase;
import com.example.ensinamente.helper.Base64Custom;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;

public class Meta {

    private String nomeTarefa;
    private String motivacao;
    private String data;

    public Meta() {
    }

    public void salvarMeta(){

        FirebaseAuth autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
        String idUsuario = Base64Custom.codificarBase64(autenticacao.getCurrentUser().getEmail());

        DatabaseReference firebase = ConfiguracaoFireBase.getFirebaseDatabase();
        firebase.child("meta")
                .child( idUsuario )
                .push()
                .setValue(this);
    }

    public String getNomeTarefa() {
        return nomeTarefa;
    }

    public void setNomeTarefa(String nomeTarefa) {
        this.nomeTarefa = nomeTarefa;
    }

    public String getMotivacao() {
        return motivacao;
    }

    public void setMotivacao(String motivacao) {
        this.motivacao = motivacao;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }
}
