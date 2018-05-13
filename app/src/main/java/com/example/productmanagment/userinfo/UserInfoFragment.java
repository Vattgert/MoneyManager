package com.example.productmanagment.userinfo;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;

import com.example.productmanagment.R;

public class UserInfoFragment extends Fragment implements UserInfoContract.View {
    UserInfoContract.Presenter presenter;
    EditText firstNameEditText, lastNameEditText, passwordEditText, confirtPasswordEditText;
    Button logoutButton, deleteUserButton;

    public UserInfoFragment() {

    }

    public static UserInfoFragment newInstance() {
        return new UserInfoFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_info, container, false);
        logoutButton = view.findViewById(R.id.logoutUserInfoButton);
        logoutButton.setOnClickListener(__ -> presenter.logout());
        return view;
    }

    @Override
    public void showUserFirstName(String firstName) {

    }

    @Override
    public void showUserLastName(String lastName) {

    }

    @Override
    public void showUserEmail(String email) {

    }

    @Override
    public void setPresenter(UserInfoContract.Presenter presenter) {
        this.presenter = presenter;
    }
}
