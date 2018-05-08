package com.example.productmanagment.groups;


import android.app.AlertDialog;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.groupeditanddetail.GroupDetailAndEditActivity;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link GroupsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupsFragment extends Fragment implements GroupsContract.View{
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private GroupsContract.Presenter presenter;
    private GroupsAdapter adapter;

    public static GroupsFragment newInstance() {
        return new GroupsFragment();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new GroupsAdapter(new ArrayList<>(0), listener);
        getActivity().setTitle("Спільні групи");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_groups, container, false);
        FloatingActionButton addGroupButton = view.findViewById(R.id.addGroupButton);
        addGroupButton.setOnClickListener(__ -> presenter.openCreateNewGroup());

        RecyclerView recyclerView = view.findViewById(R.id.groupsRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void setPresenter(GroupsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setGroupsData(List<Group> groupsData) {
        adapter.setData(groupsData);
    }

    @Override
    public void showCreateNewGroup() {
        showCreateNewGroupDialog();
    }

    @Override
    public void showEditAndDetailGroup(int groupId) {
        Intent intent = new Intent(getContext(), GroupDetailAndEditActivity.class);
        intent.putExtra("group_id", groupId);
        startActivity(intent);
    }

    @Override
    public void showCreateGroupMessage(String message) {
        Toast.makeText(getContext(), message, Toast.LENGTH_LONG).show();
    }

    GroupsItemListener listener = new GroupsItemListener() {
        @Override
        public void onGroupClick(Group clicked) {
            Toast.makeText(getContext(), clicked.getTitle(), Toast.LENGTH_LONG).show();
            presenter.openEditAndDetailGroup(clicked);
        }
    };

    private void showCreateNewGroupDialog() {
        //TODO: Сделать владельца группы авторизированого человека
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_enter_new_group_participant, null);
        TextView textView = view.findViewById(R.id.newParticipantTextView);
        textView.setText(getContext().getResources().getString(R.string.new_group_enter));
        builder.setTitle(getResources().getString(R.string.new_group_dialog))
                .setView(view)
                .setPositiveButton(R.string.group_participant_email_ok, (dialog, id) -> {
                    Group newGroup = new Group();
                    EditText groupTitleEditText = view.findViewById(R.id.groupNewParticipantEmailEditText);
                    String title = groupTitleEditText.getText().toString();
                    String owner = "1";
                    newGroup.setTitle(title);
                    newGroup.setGroupOwner(owner);
                    presenter.createNewGroup(newGroup);
                })
                .setNegativeButton(R.string.group_participant_email_reject, (dialog, id) -> {

                });
        builder.create().show();
    }

    public static class GroupsAdapter extends RecyclerView.Adapter<GroupsAdapter.ViewHolder>{
        private List<Group> groupList;
        GroupsItemListener itemListener;

        public GroupsAdapter(List<Group> groupList, GroupsItemListener itemListener) {
            this.groupList = groupList;
            this.itemListener = itemListener;
        }

        @Override
        public GroupsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_group, parent, false);
            return new GroupsAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GroupsAdapter.ViewHolder holder, int position) {
            Group group = groupList.get(position);
            holder.bind(group);
        }

        @Override
        public int getItemCount() {
            return groupList.size();
        }

        public void setData(List<Group> groupList){
            this.groupList = groupList;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            Group group;
            TextView groupTitleTextView, groupMemberCountTextView;
            Resources resources;

            public ViewHolder(View view) {
                super(view);
                groupTitleTextView = view.findViewById(R.id.groupTitleTextView);
                groupMemberCountTextView = view.findViewById(R.id.groupMemberCountTextView);
                resources = view.getResources();

                if(itemListener != null)
                    view.setOnClickListener(__ -> itemListener.onGroupClick(group));
            }

            public void bind(Group group){
                this.group = group;
                groupTitleTextView.setText(group.getTitle());
                groupMemberCountTextView.setText(resources.getString(R.string.group_user_count, group.getMembersCount()));
            }
        }
    }

    public interface GroupsItemListener {
        void onGroupClick(Group clicked);
    }
}
