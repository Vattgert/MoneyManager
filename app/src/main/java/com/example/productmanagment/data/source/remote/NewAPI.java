package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.remotemodels.ExpensePredictionResponse;
import com.example.productmanagment.data.source.remote.remotemodels.SubcategoryResponse;
import com.example.productmanagment.data.source.remote.responses.AccountResponse;
import com.example.productmanagment.data.source.remote.responses.GoalResponse;
import com.example.productmanagment.data.source.remote.responses.GroupsResponse;
import com.example.productmanagment.data.source.remote.responses.RecommendationsResponse;
import com.example.productmanagment.data.source.remote.responses.TimeSeriesForecast;

import io.reactivex.Single;
import retrofit2.http.DELETE;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface NewAPI {

    String BASE_URL = "https://onlooker-api.herokuapp.com";

    @FormUrlEncoded
    @POST("login")
    Single<User> signInUser(@Field("user_email") String userEmail,
                            @Field("user_password") String userPassword);

    //Домогосподарства
    @POST("/household/user/{user_id}")
    Single<GroupsResponse> getHouseholds(@Path("user_id") String userId);

    //Категорії
    @GET("/subcategories")
    Single<SubcategoryResponse> getSubcategories();

    //Цілі
    @GET("goal")
    Single<GoalResponse> getGoals(@Query("household_id") String household_id);

    @GET("goal/{goal_id}")
    Single<Goal> getGoalById(@Path("goal_id") String goalId);

    @FormUrlEncoded
    @POST("goal")
    Single<GoalResponse> createGoal(
                                    @Field("goal_title") String goalTitle,
                                    @Field("goal_start_date")String goalStartDate,
                                    @Field("goal_start_amount")String goalStartAmount,
                                    @Field("goal_terms")String goalWantedDate,
                                    @Field("goal_desired_amount")String goalWantedAmount,
                                    @Field("currency_id") String currencyId,
                                    @Field("household_id") String household_id);

    @FormUrlEncoded
    @PUT("goal")
    Single<GoalResponse> updateGoal(
            @Field("goal_id") String goal_id,
            @Field("goal_title") String goalTitle,
            @Field("goal_start_date")String goalStartDate,
            @Field("goal_start_amount")String goalStartAmount,
            @Field("goal_terms")String goalWantedDate,
            @Field("goal_desired_amount")String goalWantedAmount);


    @DELETE("goal")
    Single<GoalResponse> deleteGoal(@Query("goal_id") String goalId);

    //Рахунки
    @GET("account")
    Single<AccountResponse> getAccounts(@Query("household_id") String household_id);

    @GET("account/{account_id}")
    Single<Account> getAccountById(@Path("account_id") String goalId);

    @FormUrlEncoded
    @POST("account")
    Single<AccountResponse> createAccount(
            @Field("goal_title") String goalTitle,
            @Field("goal_start_date")String goalStartDate,
            @Field("goal_start_amount")String goalStartAmount,
            @Field("goal_terms")String goalWantedDate,
            @Field("goal_desired_amount")String goalWantedAmount,
            @Field("currency_id") String currencyId,
            @Field("household_id") String household_id);

    @FormUrlEncoded
    @PUT("account")
    Single<AccountResponse> updateAccount(
            @Field("goal_id") String goal_id,
            @Field("goal_title") String goalTitle,
            @Field("goal_start_date")String goalStartDate,
            @Field("goal_start_amount")String goalStartAmount,
            @Field("goal_terms")String goalWantedDate,
            @Field("goal_desired_amount")String goalWantedAmount);


    @DELETE("account")
    Single<AccountResponse> deleteAccount(@Query("account_id") String accountId);

    /* Модель оптимізації */
    @FormUrlEncoded
    @POST("prediction/subcategories_expenses")
    Single<ExpensePredictionResponse> getSubcategoryForecast(@Field("household_id") String household_id, @Field("subcategory_id") String subcategory_id,
                                                             @Field("period") String period);

    @FormUrlEncoded
    @POST("prediction/subcategories_expenses")
    Single<RecommendationsResponse> getRecommendations(@Field("household_id") String household_id);
}
