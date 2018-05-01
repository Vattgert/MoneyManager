package com.example.productmanagment.groupeditanddetail;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import com.example.productmanagment.Injection;
import com.example.productmanagment.R;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;

public class GroupDetailAndEditActivity extends AppCompatActivity {
    GroupDetailAndEditPresenter presenter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_group_detail_and_edit);

        int groupId = getIntent().getExtras().getInt("group_id");
        setTitle("Деталі групи");
        Log.wtf("GroupLog", "wanted group id " + groupId);

        GroupDetailAndEditFragment fragment = GroupDetailAndEditFragment.newInstance();
        android.support.v4.app.FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
        ft.add(R.id.groupDetailAndEditContent, fragment).commit();
        presenter = new GroupDetailAndEditPresenter(groupId, fragment, new RemoteDataRepository(),
                Injection.provideSchedulerProvider());
    }
}
