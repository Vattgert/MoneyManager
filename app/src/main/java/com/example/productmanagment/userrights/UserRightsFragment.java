package com.example.productmanagment.userrights;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Adapter;
import android.widget.CheckBox;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.UserRight;

import java.util.ArrayList;
import java.util.List;

public class UserRightsFragment extends Fragment implements UserRightsContract.View{
    private UserRightsContract.Presenter presenter;
    private UserRightsAdapter adapter;


    public UserRightsFragment() {

    }


    public static UserRightsFragment newInstance() {
        UserRightsFragment fragment = new UserRightsFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new UserRightsAdapter(new ArrayList<>(0), listener);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_rights, container, false);
        setHasOptionsMenu(true);
        RecyclerView recyclerView = view.findViewById(R.id.userRightsRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.add_fragment_actions, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_add:
                presenter.setUserRights(adapter.getUserRights());
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    @Override
    public void showUserRights(List<UserRight> userRights) {
        adapter.setData(userRights);
    }

    @Override
    public void setUserRightsEnabled(String[] rights) {
        adapter.setUserRightsChecked(rights);
    }

    @Override
    public void showMessage(String text) {
        Toast.makeText(getContext(), text, Toast.LENGTH_LONG).show();
    }

    @Override
    public void setPresenter(UserRightsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    UserRightListener listener = new UserRightListener() {
        @Override
        public void checkUserRight(UserRight userRight) {
            userRight.setChecked(!userRight.isChecked());
        }
    };

    private static class UserRightsAdapter extends RecyclerView.Adapter<UserRightsAdapter.ViewHolder>{
        private List<UserRight> userRightList;
        private UserRightListener itemListener;

        public UserRightsAdapter(List<UserRight> userRightList, UserRightListener itemListener) {
            this.userRightList = userRightList;
            this.itemListener = itemListener;
        }

        @Override
        public UserRightsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_user_right, parent, false);
            return new UserRightsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(UserRightsAdapter.ViewHolder holder, int position) {
            UserRight userRight = userRightList.get(position);
            holder.bind(userRight);
        }

        @Override
        public int getItemCount() {
            return userRightList.size();
        }

        public void setData(List<UserRight> userRightList){
            this.userRightList = userRightList;
            notifyDataSetChanged();
        }

        public void setUserRightsChecked(String[] rights){
            for (String right : rights){
                int rightInt = Integer.valueOf(right);
                for(UserRight userRight : userRightList){
                    if(userRight.getId() == rightInt) {
                        Log.wtf("GroupLog", rightInt + " server = " + userRight.getId() + " client");
                        userRight.setChecked(true);
                    }
                }
            }
            notifyDataSetChanged();
        }

        public String getUserRights(){
            List<String> ur = new ArrayList<>(0);
            for (UserRight userRight : userRightList){
                if (userRight.isChecked())
                    ur.add(String.valueOf(userRight.getId()));
            }
            return TextUtils.join(",", ur);
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            UserRight userRight;
            TextView userRightTitleTextView, userRightDescriptionTextView;
            CheckBox userRightCheckBox;

            public ViewHolder(View view) {
                super(view);
                userRightTitleTextView = view.findViewById(R.id.userRightTitleTextView);
                userRightDescriptionTextView = view.findViewById(R.id.userRightDescriptionTextView);
                userRightCheckBox = view.findViewById(R.id.userRightCheckBox);

                //if(itemListener != null)
                    //view.setOnClickListener(__ -> itemListener.onPurchaseClick(purchase));

                userRightCheckBox.setOnClickListener(__ -> itemListener.checkUserRight(userRight));
            }

            public void bind(UserRight userRight){
                this.userRight = userRight;
                userRightTitleTextView.setText(userRight.getTitle());
                userRightDescriptionTextView.setText(userRight.getDescription());
                userRightCheckBox.setChecked(userRight.isChecked());
            }
        }
    }

    interface UserRightListener{
        void checkUserRight(UserRight userRight);
    }
}
