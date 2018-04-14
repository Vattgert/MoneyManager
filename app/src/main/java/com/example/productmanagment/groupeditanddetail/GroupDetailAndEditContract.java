package com.example.productmanagment.groupeditanddetail;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.User;

import java.util.List;

public interface GroupDetailAndEditContract {
    interface Presenter extends BasePresenter{
        void addUser(String user);
        void openAddUserDialog();
        void loadUsersByGroup(String groupTitle);
        void editGroupTitle(String groupTitle);
        void addUserToGroup(String group, String user);
    }

    interface View extends BaseView<Presenter>{
        void showNewParticipantDialog(String group);
        void setGroupTitleEdit(String groupTitle);
        void setGroupParticipantCount();
        void setUsersData(List<User> usersData);
        void showNoSuchUserMessage();
    }
}
