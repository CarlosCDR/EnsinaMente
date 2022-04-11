package com.example.ensinamente.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;

import com.example.ensinamente.R;
import com.example.ensinamente.config.ConfiguracaoFireBase;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FacebookAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.heinrichreimersoftware.materialintro.app.IntroActivity;


public class MainActivity extends IntroActivity {

    private FirebaseAuth autenticacao;
    private GoogleSignInClient mGoogleSignInClient;
    private final static int RC_SIGN_IN = 9001;
    private static final String TAG = "FacebookLogin";
    private CallbackManager mCallbackManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        //recupera o token
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("442949868033-f3l31q0haaeadiitrm826mf8uefmv4ul.apps.googleusercontent.com")
                .requestEmail()
                .build();

        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        autenticacao = FirebaseAuth.getInstance();

        findViewById(R.id.signInButton).setOnClickListener(view -> {
           signIn();
        });

        mCallbackManager = CallbackManager.Factory.create();
        LoginButton loginButton = findViewById(R.id.btnFacebook);
        loginButton.setReadPermissions("email", "public_profile");
        loginButton.registerCallback(mCallbackManager, new FacebookCallback<LoginResult>() {
            @Override
            public void onSuccess(LoginResult loginResult) {
                Log.d(TAG, "facebook:onSuccess:" + loginResult);
                handleFacebookAccessToken(loginResult.getAccessToken());
            }

            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });


    }

    //utiliza o metodo de verificação de usuario
    public void onStart(){
        super.onStart();
        verificarUsuarioLogado();
        FirebaseUser currentUser = autenticacao.getCurrentUser();
        updateUI(currentUser);
    }


    //Metodo de Login com o google
    //abri o google intent
    private void signIn() {
        Intent signInIntent = mGoogleSignInClient.getSignInIntent();
        //startActivityForResult(signInIntent, 1);
        abreActivity.launch(signInIntent);
    }
    ActivityResultLauncher<Intent> abreActivity = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(),
            result -> {
                if(result.getResultCode() == Activity.RESULT_OK){
                    Intent intent = result.getData();

                    Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
                    try{
                        GoogleSignInAccount conta = task.getResult(ApiException.class);
                        loginComGoogle(conta.getIdToken());
                    } catch (ApiException exception){
                        Toast.makeText(this,
                                "Nenhum usuário Google logado!", Toast.LENGTH_SHORT).show();
                        Log.d("Erro", exception.toString());
                    }
                }
            }
    );


    private void loginComGoogle(String token){
        AuthCredential credencial = GoogleAuthProvider.getCredential(token, null);

        autenticacao.signInWithCredential(credencial).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                Toast.makeText(this,
                        "login com Google efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                abrirTelaPrincipal();
                finish();
            }else{
                Toast.makeText(this,
                        "Erro ao efetuar Login com Google!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    public void onActivityResult(int requestCode, int resultCode, Intent intent){
        super.onActivityResult(requestCode, resultCode, intent);

        if(requestCode == 1){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(intent);
            try{
                GoogleSignInAccount conta = task.getResult(ApiException.class);
                loginComGoogle(conta.getIdToken());
            } catch (ApiException exception){
                Toast.makeText(this,
                        "Nenhum usuário Google logado!", Toast.LENGTH_SHORT).show();
                Log.d("Erro", exception.toString());
            }
        }
        mCallbackManager.onActivityResult(requestCode, resultCode, intent);
    }


    private void handleFacebookAccessToken(AccessToken token) {
        Log.d(TAG, "handleFacebookAccessToken:" + token);

        AuthCredential credential = FacebookAuthProvider.getCredential(token.getToken());
        autenticacao.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInWithCredential:success");
                            FirebaseUser user = autenticacao.getCurrentUser();
                            updateUI(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            Log.w(TAG, "signInWithCredential:failure", task.getException());
                            Toast.makeText(MainActivity.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                            updateUI(null);
                        }
                    }
                });
    }

    private void updateUI(FirebaseUser user) {
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        if (users != null) {
            // User is signed in
            Intent i = new Intent(MainActivity.this, PrincipalActivity.class);
            startActivity(i);
            finish();
            Toast.makeText(MainActivity.this, "Login com Facebook efetuado com Sucesso", Toast.LENGTH_SHORT).show();
        } else {

            // No user is signed in
        }
    }


    //metodos de cadastro e login
    public void btEntra(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    public void btCadastra(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    //verificando se o usuario esta realmente logado
    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();
        //autenticacao.signOut();
        if(autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }
    //abri tela principal do app
    public void abrirTelaPrincipal(){
        startActivity(new Intent(this, PrincipalActivity.class));
    }

}
