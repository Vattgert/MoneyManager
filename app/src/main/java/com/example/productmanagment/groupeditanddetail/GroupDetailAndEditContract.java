package com.example.productmanagment.groupeditanddetail;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.User;

import java.util.List;

public interface GroupDetailAndEditContract {
    interface Presenter extends BasePresenter{
        void addUser(String user);
        void openAddUserDialog();
        void openRenameGroupDialog();
        void loadUsersByGroup(String groupTitle);
        void editGroupTitle(String groupTitle);
        void openUserRights(String userId);
    }

    interface View extends BaseView<Presenter>{
        void showNewParticipantDialog();
        void showNewGroupNameDialog();
        void setGroupTitleEdit(String groupTitle);
        void setGroupParticipantCount();
        void setUsersData(List<User> usersData);
        void showUserAddMessage(String message);
        void showUserRights(String userId, String groupId);
    }
}
