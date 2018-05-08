package com.example.productmanagment.groupeditanddetail;

import android.util.Log;

import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.remote.responses.SuccessResponse;
import com.example.productmanagment.data.source.remote.responses.UsersResponse;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class GroupDetailAndEditPresenter implements GroupDetailAndEditContract.Presenter {
    private int groupId;
    private GroupDetailAndEditContract.View view;
    private RemoteDataRepository repository;
    private BaseSchedulerProvider provider;
    private CompositeDisposable compositeDisposable;

    public GroupDetailAndEditPresenter(int groupId, GroupDetailAndEditContract.View view,
                                       RemoteDataRepository repository, BaseSchedulerProvider provider) {
        this.groupId = groupId;
        this.view = view;
        this.repository = repository;
        this.provider = provider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void addUser(String user) {
        Disposable disposable = repository.addUserToGroup(user, String.valueOf(this.groupId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processAddGroupToUser, throwable -> Log.wtf("GroupLog", throwable.getMessage() + " " + throwable.getCause()));
    }

    @Override
    public void openAddUserDialog() {
        view.showNewParticipantDialog();
    }

    @Override
    public void openRenameGroupDialog() {
        view.showNewGroupNameDialog();
    }

    @Override
    public void loadUsersByGroup(String groupId) {
        repository.getUsersByGroup(groupId)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processGroupUsers, throwable -> Log.wtf("GroupLog", throwable.getMessage() + " " + throwable.getCause()));
    }

    @Override
    public void editGroupTitle(String groupTitle) {
        repository.renameGroup(groupTitle, String.valueOf(this.groupId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processRenameGroup, throwable -> Log.wtf("GroupLog", throwable.getMessage() + " " + throwable.getCause()));
    }

    @Override
    public void openUserRights(String userId) {
        view.showUserRights(userId, String.valueOf(this.groupId));
    }

    @Override
    public void subscribe() {
        Disposable disposable = repository.getGroupById(String.valueOf(this.groupId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processGroup, throwable -> Log.wtf("GroupLog", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private void processGroup(Group group){
        view.setGroupTitleEdit(group.getTitle());
        view.setUsersData(group.getGroupUsers());
        view.setGroupParticipantCount();
    }

    private void processAddGroupToUser(SuccessResponse response){
        loadUsersByGroup(String.valueOf(this.groupId));
        if(response.response.equals("success")) {
            view.showUserAddMessage(response.data);
        }
        else
            view.showUserAddMessage(response.errorData);
    }

    private void processGroupUsers(UsersResponse response){
        List<User> userList = response.userList;
        view.setUsersData(userList);
    }

    private void processRenameGroup(SuccessResponse response){
        if (response.response.equals("success")){
            view.showUserAddMessage(response.data);
            view.setGroupTitleEdit(response.additionalData);
        }
        else
        {
            view.showUserAddMessage(response.errorData);
        }
    }
}
