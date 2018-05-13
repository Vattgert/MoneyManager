package com.example.productmanagment.userinfo;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;

public class UserInfoActivity extends AppCompatActivity {
    UserInfoPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        UserInfoFragment fragment = UserInfoFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.userInfoContent, fragment).commit();
        presenter = new UserInfoPresenter(this, fragment, new RemoteDataRepository(), Injection.provideSchedulerProvider());
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter = null;
    }
}
