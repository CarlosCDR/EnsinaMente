package com.example.ensinamente.activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
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

        //recupera o token do cliente Auth2.0 recebido do googlecloud
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken("84364552072-410g05ao1gafs4spq1cm66hi0561tigl.apps.googleusercontent.com")
                .requestEmail()
                .build();

        //instanciando a autenticação Firebase e o Cliente Google
        mGoogleSignInClient = GoogleSignIn.getClient(this, gso);
        autenticacao = FirebaseAuth.getInstance();

        //chama o metodo logar com o google
        findViewById(R.id.signInButton).setOnClickListener(view -> {
           signIn();
        });

        //recebe e retorna o token para logar com o facebook
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

    //Verifica se o usuário esta logado
    public void onStart(){
        super.onStart();
        verificarUsuarioLogado();
        FirebaseUser currentUser = autenticacao.getCurrentUser();
        updateUI(currentUser);
    }

    //METODOS DE LOGIN COM GOOGLE
    //abri o google intent para o usuário conectar sua conta google
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

    //efetua o login com o google se o token for valido
    private void loginComGoogle(String token){
        AuthCredential credencial = GoogleAuthProvider.getCredential(token, null);

        autenticacao.signInWithCredential(credencial).addOnCompleteListener(this, task -> {
            if(task.isSuccessful()){
                Toast.makeText(this,
                        "login com Google efetuado com sucesso!", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(this, IntroducaoActivity.class));
                finish();
            }else{
                Toast.makeText(this,
                        "Erro ao efetuar Login com Google!", Toast.LENGTH_SHORT).show();

            }
        });
    }

    //verifica se o token foi recebido com sucesso
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

    //METODOS DE LOGIN COM FACEBOOK
    //recebe o token e retorna ele
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

    //verifica se o token do usuário e valido e logar ele no app
    private void updateUI(FirebaseUser user) {
        FirebaseUser users = FirebaseAuth.getInstance().getCurrentUser();
        if (users != null) {
            // User is signed in
            Intent i = new Intent(MainActivity.this, IntroducaoActivity.class);
            startActivity(i);
            finish();
            Toast.makeText(MainActivity.this, "Login efetuado com Sucesso", Toast.LENGTH_SHORT).show();
        } else {

            // No user is signed in
        }
    }

    //chama activity de login
    public void btEntra(View view){
        startActivity(new Intent(this, LoginActivity.class));
    }

    //chama  a activity para poder cadastrar o usuário
    public void btCadastra(View view){
        startActivity(new Intent(this, CadastroActivity.class));
    }

    //verificando se o usuario esta realmente logado
    public void verificarUsuarioLogado(){
        autenticacao = ConfiguracaoFireBase.getFireBaseAutenticacao();

        if(autenticacao.getCurrentUser() != null){
            abrirTelaPrincipal();
        }
    }

    //abri tela principal do app
    public void abrirTelaPrincipal(){
        startActivity(new Intent(this, PrincipalActivity.class));
    }

}
