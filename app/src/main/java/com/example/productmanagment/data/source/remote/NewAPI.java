package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.remotemodels.ExpensePredictionResponse;
import com.example.productmanagment.data.source.remote.remotemodels.RecommendationsResponse;
import com.example.productmanagment.data.source.remote.remotemodels.SubcategoryResponse;
import com.example.productmanagment.data.source.remote.responses.AccountResponse;
import com.example.productmanagment.data.source.remote.responses.ExpensesResponse;
import com.example.productmanagment.data.source.remote.responses.GoalResponse;
import com.example.productmanagment.data.source.remote.responses.GroupsResponse;
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

    @FormUrlEncoded
    @POST("goal_investment")
    Single<GoalResponse> addGoalAmount(@Field("goal_id") String goal_id,
                                       @Field("user_id") String user_id,
                                       @Field("goal_investment_amount") double amount);

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

    @GET("recommendations/current_state/{household_id}")
    Single<RecommendationsResponse> getRecommendations(@Path("household_id") String household_id);

    //Транзакції
    @GET("transaction")
    Single<ExpensesResponse> getTransactions(@Query("account_id") String account_id, @Query("household_id") String household_id);

    @GET("transaction/{transaction_id}")
    Single<Expense> getTransactionById(@Path("transaction_id") String transaction_id);

    @FormUrlEncoded
    @POST("transaction")
    Single<ExpensesResponse> createTransaction(
            @Field("transaction_title") String transactionTitle,
            @Field("transaction_ts")String transactionTs,
            @Field("receiver")String receiver,
            @Field("money_amount")String moneyAmount,
            @Field("transaction_place")String transactionPlace,
            @Field("payment_type") String paymentType,
            @Field("transaction_type") String transactionType,
            @Field("account_id") String accountId,
            @Field("category_id") String categoryId,
            @Field("user_id") String userId);

    @FormUrlEncoded
    @PUT("transaction")
    Single<ExpensesResponse> updateTransaction(
            @Field("transaction_title") String transactionTitle,
            @Field("transaction_ts")String transactionTs,
            @Field("receiver")String receiver,
            @Field("money_amount")String moneyAmount,
            @Field("transaction_place")String transactionPlace,
            @Field("payment_type") String paymentType,
            @Field("transaction_type") String transactionType,
            @Field("account_id") String accountId,
            @Field("category_id") String categoryId,
            @Field("userId") String userId);

    @DELETE("transaction")
    Single<ExpensesResponse> deleteTransaction(@Query("transaction_id") String transactionId);

    /*----------------------------------------------------------------------------------*/

    @GET("user/household/{household_id}")
    Single<ExpensesResponse> getHouseholdUsers(@Path("household_id") String household_id);
}
