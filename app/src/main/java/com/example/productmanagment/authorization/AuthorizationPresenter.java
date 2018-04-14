package com.example.productmanagment.authorization;

import android.content.Context;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;
import android.widget.Toast;

//import com.google.android.gms.auth.api.Auth;
import com.example.productmanagment.R;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;

import java.util.concurrent.Executor;

/**
 * Created by Ivan on 26.02.2018.
 */

public class AuthorizationPresenter implements AuthorizationContract.Presenter {
    public static final int SIGN_IN_GOOGLE_REQUEST = 1;
    private FirebaseAuth auth;
    private GoogleSignInClient googleSignInClient;
    private AuthorizationContract.View view;
    private Context context;
    private RemoteDataRepository repository;

    public AuthorizationPresenter(AuthorizationContract.View view, Context context, RemoteDataRepository repository) {
        this.view = view;
        this.view.setPresenter(this);
        this.repository = repository;
        this.context = context;
    }

    @Override
    public void subscribe() {
        auth = FirebaseAuth.getInstance();
        /*GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(context.getString(R.string.google_server_id))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(context, gso);*/
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void signInWithEmailAndPassword(String email, String password) {
        if(auth != null){
            auth.signInWithEmailAndPassword(email, password).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                @Override
                public void onComplete(@NonNull Task<AuthResult> task) {
                    if(task.isSuccessful()){
                        view.showMainActivityIfAuth();
                    }
                    else{
                        Log.wtf("AuthLog", task.getException());
                        view.showAuthorizationFailedMessage();
                    }
                }
            });
        }
    }

    @Override
    public void signInWithGoggle() {
        Intent intent = googleSignInClient.getSignInIntent();
        view.showGoogleSignInActivity(intent);
    }

    @Override
    public void signInWithFacebook() {

    }

    @Override
    public void signInWithPhone() {

    }

    @Override
    public void openSignUp() {
        view.showSignUp();
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if(requestCode == AuthorizationPresenter.SIGN_IN_GOOGLE_REQUEST){
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                // Google Sign In was successful, authenticate with Firebase
                GoogleSignInAccount account = task.getResult(ApiException.class);
                firebaseAuthWithGoogle(account);
            } catch (ApiException e) {
                // Google Sign In failed, update UI appropriately
                Log.wtf("AuthLog", "Google sign in failed", e);
                // ...
            }
        }
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount acct) {
        Log.d("AuthLog", "firebaseAuthWithGoogle:" + acct.getId());

        AuthCredential credential = GoogleAuthProvider.getCredential(acct.getIdToken(), null);
        auth.signInWithCredential(credential).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    // Sign in success, update UI with the signed-in user's information
                    Log.wtf("AuthLog", "signInWithCredential:success");
                    FirebaseUser user = auth.getCurrentUser();
                } else {
                    // If sign in fails, display a message to the user.
                    Log.wtf("AuthLog", "signInWithCredential:failure", task.getException());
                }
            }
        });
    }

}
