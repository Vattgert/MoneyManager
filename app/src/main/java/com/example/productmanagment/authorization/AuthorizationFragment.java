package com.example.productmanagment.authorization;


import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.productmanagment.MainActivity;
import com.example.productmanagment.R;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link AuthorizationFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class AuthorizationFragment extends Fragment implements AuthorizationContract.View {
    AuthorizationContract.Presenter presenter;
    EditText emailEditText, passwordEditText;
    Button signInButton, signUpButton, signInWithGoogle, signInWithFacebook;
    OnSignUpClickListener listener;

    // TODO: Rename and change types and number of parameters
    public static AuthorizationFragment newInstance() {
        return new AuthorizationFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Вхід");
        presenter.subscribe();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnSignUpClickListener) {
            listener = (OnSignUpClickListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_authorization, container, false);
        emailEditText = view.findViewById(R.id.emailAuthorizationEditText);
        passwordEditText = view.findViewById(R.id.passwordAuthorizationEditText);
        signInButton = view.findViewById(R.id.signInButton);
        signUpButton = view.findViewById(R.id.signUpButton);
        signInButton.setOnClickListener(__ -> getDataAndSignIn());
        signUpButton.setOnClickListener(__-> presenter.openSignUp());
        return view;
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

    }

    @Override
    public void setPresenter(AuthorizationContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setAuthUserUi(String email) {

    }

    @Override
    public void showAuthorizationFailedMessage() {
        Toast.makeText(getContext(), "Не вдалося увійти", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showMainActivityIfAuth() {
        Intent intent = new Intent(getContext(), MainActivity.class);
        startActivity(intent);
        getActivity().finish();
    }

    @Override
    public void showGoogleSignInActivity(Intent intent) {

    }

    @Override
    public void showSignUp() {
        listener.onSignUpClick();
    }

    private void getDataAndSignIn(){
        String email, password;
        if(emailEditText != null && passwordEditText != null){
            email = emailEditText.getText().toString();
            password = passwordEditText.getText().toString();
            presenter.signInWithEmailAndPassword(email, password);
        }
    }

    public interface OnSignUpClickListener{
        void onSignUpClick();
    }
}
