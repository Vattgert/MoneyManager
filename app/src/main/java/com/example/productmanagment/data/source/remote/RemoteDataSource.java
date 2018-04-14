package com.example.productmanagment.data.source.remote;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.Logger;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Observable;
import java.util.function.Consumer;

import io.ashdavies.rx.rxfirebase.RxFirebaseDatabase;
import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;

public class RemoteDataSource implements RemoteData {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference database;

    public RemoteDataSource() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        database = firebaseDatabase.getReference();
    }

    @Override
    public void signUpUserToDatabase(String email, String login) {
        database.child("users").child(login).child("email").setValue(email);
    }

    @Override
    public List<Expense> getExpensesByGroup(String group) {
        return null;
    }

    @Override
    public void createGroup(Group group) {
        database.child("groups").child(group.getTitle()).child("group_owner").setValue(group.getGroupOwner());
        database.child("users").child(group.getGroupOwner()).child("groups").child(group.getTitle()).setValue("group_owner");
    }

    @Override
    public void editGroupTitle(String groupTitle) {

    }

    @Override
    public void addUserToGroup(String group, String user) {
        database.child("users").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    if(snapshot.getKey().equals(user)){
                        database.child("group_members").child(group).child(user).setValue("true");
                    }
                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.wtf("RemoteDatabaseLog", databaseError.toException());
            }
        });
    }

    @Override
    public Flowable<List<Group>> getGroupsByGroupOwner(String owner){
        Log.wtf("RemoteDatabaseLog", "get groups by owner remote source");
        return Flowable.create( e -> database.child("users").child(owner).child("groups").addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Group> groups = new ArrayList<>(0);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    Log.wtf("RemoteDatabaseLog", snapshot.getKey());
                    Group group = new Group();
                    group.setTitle(snapshot.getKey());
                    groups.add(group);
                }
                e.onNext(groups);
                e.onComplete();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.wtf("RemoteDatabaseLog", databaseError.toException());
            }
        }), BackpressureStrategy.DROP);
    }

    @Override
    public Flowable<List<User>> getUsersListByGroup(String group) {
        return Flowable.create( e -> database.child("group_members").child(group).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<User> usersGroup = new ArrayList<>(0);
                for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                    User user = new User();
                    user.setEmail(snapshot.getKey());
                    usersGroup.add(user);
                }
                e.onNext(usersGroup);
                e.onComplete();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.wtf("RemoteDatabaseLog", databaseError.toException());
            }
        }), BackpressureStrategy.DROP);
    }
}
