package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.remotemodels.ExpensePredictionResponse;
import com.example.productmanagment.data.source.remote.remotemodels.SubcategoryResponse;
import com.example.productmanagment.data.source.remote.responses.GroupsResponse;
import com.example.productmanagment.data.source.remote.responses.RecommendationsResponse;
import com.example.productmanagment.data.source.remote.responses.TimeSeriesForecast;

import io.reactivex.Single;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewAPI {

    String BASE_URL = "https://onlooker-api.herokuapp.com";



    @FormUrlEncoded
    @POST("login")
    Single<User> signInUser(@Field("user_email") String userEmail,
                            @Field("user_password") String userPassword);


    @POST("/household/user/{user_id}")
    Single<GroupsResponse> getHouseholds(@Path("user_id") String userId);

    @GET("/subcategories")
    Single<SubcategoryResponse> getSubcategories();


    /* Модель оптимізації */

    @FormUrlEncoded
    @POST("prediction/subcategories_expenses")
    Single<ExpensePredictionResponse> getSubcategoryForecast(@Field("household_id") String household_id, @Field("subcategory_id") String subcategory_id,
                                                             @Field("period") String period);

    @FormUrlEncoded
    @POST("prediction/subcategories_expenses")
    Single<RecommendationsResponse> getRecommendations(@Field("household_id") String household_id);
}
