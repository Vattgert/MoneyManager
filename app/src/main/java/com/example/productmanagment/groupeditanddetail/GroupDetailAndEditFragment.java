package com.example.productmanagment.groupeditanddetail;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.User;
import com.github.ivbaranov.mli.MaterialLetterIcon;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * to handle interaction events.
 * Use the {@link GroupDetailAndEditFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class GroupDetailAndEditFragment extends Fragment implements GroupDetailAndEditContract.View {
    private GroupDetailAndEditContract.Presenter presenter;
    private GroupParticipantAdapter adapter;
    EditText groupTitleEditText;
    TextView groupParticipantCountTextView;
    Button addGroupParticipantButton;
    ImageButton editTitleImageButton;

    public GroupDetailAndEditFragment() {
        // Required empty public constructor
    }

    public static GroupDetailAndEditFragment newInstance() {
        return new GroupDetailAndEditFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new GroupParticipantAdapter(new ArrayList<User>(0), null, getContext());
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_group_detail_and_edit, container, false);
        groupTitleEditText = view.findViewById(R.id.groupTitleEditText);
        groupParticipantCountTextView = view.findViewById(R.id.groupUserCountTextView);

        addGroupParticipantButton = view.findViewById(R.id.addGroupParticipantButton);
        addGroupParticipantButton.setOnClickListener(__ -> presenter.openAddUserDialog());

        editTitleImageButton = view.findViewById(R.id.editTitleImageButton);
        editTitleImageButton.setOnClickListener(__ -> presenter.editGroupTitle(groupTitleEditText.getText().toString()));

        RecyclerView recyclerView = view.findViewById(R.id.groupParticipantsRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void setPresenter(GroupDetailAndEditContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private void showEnterNewGroupParticipantDialog(String group){
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_enter_new_group_participant, null);
        builder.setTitle(getResources().getString(R.string.group_new_participant_dialog))
                .setView(view)
                .setPositiveButton(R.string.group_participant_email_ok, (dialog, id) -> {
                    EditText sumEditText = view.findViewById(R.id.groupNewParticipantEmailEditText);
                    String email = sumEditText.getText().toString();
                    presenter.addUser(email);
                })
                .setNegativeButton(R.string.group_participant_email_reject, (dialog, id) -> {

                });
        builder.create().show();
    }

    @Override
    public void showNewParticipantDialog(String group) {
        showEnterNewGroupParticipantDialog(group);
    }

    @Override
    public void setGroupTitleEdit(String groupTitle) {
        groupTitleEditText.setText(groupTitle);
    }

    @Override
    public void setGroupParticipantCount() {
        groupParticipantCountTextView.setText(getContext().getResources().getString(R.string.group_user_count, adapter.getItemCount()));
    }

    @Override
    public void setUsersData(List<User> usersData) {
        adapter.setData(usersData);
    }

    @Override
    public void showNoSuchUserMessage() {
        Toast.makeText(getContext(), "Такого користувача немає", Toast.LENGTH_LONG).show();
    }

    public static class GroupParticipantAdapter extends RecyclerView.Adapter<GroupParticipantAdapter.ViewHolder>{
        private List<User> userList;
        ParticipantItemListener itemListener;
        Context context;

        public GroupParticipantAdapter(List<User> userList, ParticipantItemListener itemListener, Context context) {
            this.userList = userList;
            this.itemListener = itemListener;
            this.context = context;
        }

        @Override
        public GroupParticipantAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_group_participant, parent, false);
            return new GroupParticipantAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(GroupParticipantAdapter.ViewHolder holder, int position) {
            User user = userList.get(position);
            holder.bind(user);
        }

        @Override
        public int getItemCount() {
            return userList.size();
        }

        public void setData(List<User> userList){
            this.userList = userList;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            User user;
            TextView groupParticipantEmailTextView;
            MaterialLetterIcon letterIcon;

            public ViewHolder(View view) {
                super(view);
                groupParticipantEmailTextView = view.findViewById(R.id.groupParticipantEmailTextView);
                letterIcon = view.findViewById(R.id.groupParticipantEmailIcon);


                if(itemListener != null)
                    view.setOnClickListener(__ -> itemListener.onParticipantClick(user));
            }

            public void bind(User user){
                this.user = user;
                groupParticipantEmailTextView.setText(user.getEmail());
                letterIcon.setLetter(user.getEmail().substring(0,3));
            }

            private MaterialLetterIcon.Builder createLetterBuilder(){
                return new MaterialLetterIcon.Builder(context)
                        .shapeColor(context.getResources().getColor(R.color.colorAccent))
                        .letterColor(context.getResources().getColor(R.color.white))
                        .letterSize(30)
                        .lettersNumber(2);
            }
        }
    }

    public interface ParticipantItemListener {
        void onParticipantClick(User clicked);
    }

}
