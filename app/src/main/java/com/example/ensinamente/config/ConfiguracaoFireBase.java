package com.example.ensinamente.config;

import com.google.firebase.auth.FirebaseAuth;

public class ConfiguracaoFireBase {

    private static FirebaseAuth autenticacao;

    //retorna a instancia do FireBaseAuth
    public static FirebaseAuth getFireBaseAutenticacao(){
        if( autenticacao == null){
            autenticacao = FirebaseAuth.getInstance();
        }
        return  autenticacao;
    }

}
