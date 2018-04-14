package com.example.productmanagment.groupeditanddetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.example.productmanagment.R;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;

public class GroupDetailAndEditActivity extends AppCompatActivity {
    GroupDetailAndEditPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail_and_edit);

        String group = getIntent().getExtras().getString("group_title");
        setTitle(group);

        GroupDetailAndEditFragment fragment = GroupDetailAndEditFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.groupDetailAndEditContent, fragment).commit();
        presenter = new GroupDetailAndEditPresenter(group, fragment, new RemoteDataRepository());
    }
}
