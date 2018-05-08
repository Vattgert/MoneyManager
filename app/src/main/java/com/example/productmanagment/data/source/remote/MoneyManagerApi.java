package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.responses.GroupsResponse;
import com.example.productmanagment.data.source.remote.responses.SuccessResponse;
import com.example.productmanagment.data.source.remote.responses.UsersResponse;

import java.util.List;

import io.reactivex.Flowable;
import io.reactivex.Single;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
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

    @GET("loadUsersByGroup.php")
    Single<UsersResponse> getUsersByGroup(@Query("groupId") String groupId);

    @GET("getUsersRights.php")
    Single<SuccessResponse> getUsersRights(@Query("groupId") String groupId, @Query("userId") String userId);

    @GET("setUserRights.php")
    Single<SuccessResponse> setUserRights(@Query("groupId") String groupId,
                                          @Query("userId") String userId,
                                          @Query("userRights") String userRights);

    @FormUrlEncoded
    @POST("addGroup.php")
    Single<SuccessResponse> addGroup(@Field("group_title") String groupTitle,
                                     @Field("id_group_creator") String groupOwner);

    @FormUrlEncoded
    @POST("addUserToGroup.php")
    Single<SuccessResponse> addUserToGroup(@Field("user_email") String userEmail, @Field("id_group") String groupId);

    @FormUrlEncoded
    @POST("renameGroupById.php")
    Single<SuccessResponse> renameGroup(@Field("group_title") String groupTitle,
                                        @Field("id_group") String groupId);
}
