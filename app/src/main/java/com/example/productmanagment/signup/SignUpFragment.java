package com.example.productmanagment.signup;


import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.Toast;

import com.example.productmanagment.MainActivity;
import com.example.productmanagment.R;
import com.example.productmanagment.data.models.User;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.codec.digest.DigestUtils;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link SignUpFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SignUpFragment extends Fragment implements SignUpContract.View {
    SignUpContract.Presenter presenter;
    EditText loginEditText, emailEditText, passwordEditText;
    Button signUpButton;
    CheckBox authAfterSignUpCheckBox;

    public static SignUpFragment newInstance() {
        return new SignUpFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Реєстрація");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_sign_up, container, false);
        loginEditText = view.findViewById(R.id.loginSignUpEditText);
        emailEditText = view.findViewById(R.id.emailSignUpEditText);
        passwordEditText = view.findViewById(R.id.passwordSignUpEditText);
        signUpButton = view.findViewById(R.id.signUpToButton);
        signUpButton.setOnClickListener(__ -> getDataAndSignUp());
        authAfterSignUpCheckBox = view.findViewById(R.id.authAfterSignUpCheckBox);
        return view;
    }

    @Override
    public void setPresenter(SignUpContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void getDataAndSignUp(){
        String email = emailEditText.getText().toString();
        String password = passwordEditText.getText().toString();
        String login = loginEditText.getText().toString();
        if(!email.equals("") && !login.equals("") && !password.equals("")){
            User user = new User();
            user.setEmail(email);
            user.setLogin(login);
            user.setPassword(password);
            presenter.signUp(user);
        }
    }

    @Override
    public void finish() {
        getActivity().finish();
    }

    @Override
    public void showMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    @Override
    public boolean isAuthAfterSignUpChecked() {
        return authAfterSignUpCheckBox.isChecked();
    }

    @Override
    public void openMainActivity() {
        Intent intent = new Intent(getActivity(), MainActivity.class);
        startActivity(intent);
    }
}
