package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.responses.GroupsResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface MoneyManagerApi {
    String BASE_URL = "https://moneymanagerandroid.000webhostapp.com";

    @FormUrlEncoded
    @POST("signUpUser")
    Single<User> signUpUser(User user);

    @FormUrlEncoded
    @POST("signInUser")
    Call<User> signInUser(User user);

    @GET("groupsByCreator.php")
    Single<GroupsResponse> getGroupsByCreator(@Query("groupCreator") String userId);

    @GET("groupById.php")
    Single<Group> getGroupById(@Query("groupId") String groupId);
}
