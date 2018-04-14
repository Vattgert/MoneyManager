package com.example.productmanagment.groups;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Group;

import java.util.List;

public interface GroupsContract {
    interface Presenter extends BasePresenter{
        void loadGroupsByOwner(String groupOwner);
        void createNewGroup(Group group);
        void openEditAndDetailGroup(Group group);
        void openCreateNewGroup();
    }

    interface View extends BaseView<Presenter>{
        void setGroupsData(List<Group> groupsData);
        void showCreateNewGroup();
        void showEditAndDetailGroup(String groupTitle);
    }
}
