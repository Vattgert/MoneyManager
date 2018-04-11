package com.example.productmanagment.data.source.remote;

import android.util.Log;

import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.HashMap;
import java.util.List;
import java.util.function.Consumer;

public class RemoteDataSource implements RemoteData {
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference database;

    public RemoteDataSource() {
        firebaseDatabase = FirebaseDatabase.getInstance();
        database = firebaseDatabase.getReference();
    }

    @Override
    public void signUpUserToDatabase(String email, String login) {
        database.child("\"users\"").child(login).child("email").setValue(email);
    }

    @Override
    public List<Group> getGroupListByGroupOwner(String groupOwner) {
        String value = "";
        database.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for(DataSnapshot snapshot : dataSnapshot.child("\"users\"").child(groupOwner).child("groups").getChildren()){
                    Log.wtf("RemoteDatabaseLog", snapshot.getKey() + " : " + snapshot.getValue());
                }

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                Log.wtf("RemoteDatabaseLog", databaseError.toException());
            }
        });
        return null;
    }

    @Override
    public List<Expense> getExpensesByGroup(String group) {
        return null;
    }

    @Override
    public void createGroup() {

    }

    @Override
    public void addUserToGroup(Group group, User user) {

    }
}
