package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.source.remote.responses.GroupsResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RemoteDataSource implements RemoteData{
    private MoneyManagerApi moneyManagerApi;
    private Retrofit retrofit;

    public RemoteDataSource() {
        retrofit = new Retrofit.Builder()
                .baseUrl(MoneyManagerApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        moneyManagerApi = retrofit.create(MoneyManagerApi.class);
    }


    @Override
    public Single<GroupsResponse> getGroupsByCreator(String groupCreator) {
        return moneyManagerApi.getGroupsByCreator(groupCreator);
    }

    @Override
    public Single<Group> getGroupById(String groupId) {
        return moneyManagerApi.getGroupById(groupId);
    }
}
