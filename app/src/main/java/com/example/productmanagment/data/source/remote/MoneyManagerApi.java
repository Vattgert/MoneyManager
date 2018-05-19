package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.responses.AccountResponse;
import com.example.productmanagment.data.source.remote.responses.CurrencyResponse;
import com.example.productmanagment.data.source.remote.responses.DiagramResponse;
import com.example.productmanagment.data.source.remote.responses.ExpensesResponse;
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
    @POST("signUpUser.php")
    Single<SuccessResponse> signUpUser(@Field("user_email") String userEmail,
                            @Field("user_login") String userLogin,
                            @Field("user_password") String userPassword);

    @FormUrlEncoded
    @POST("signInUser.php")
    Single<User> signInUser(@Field("user_email") String userEmail,
                            @Field("user_password") String userPassword);

    @FormUrlEncoded
    @POST("signInUser")
    Call<User> signInUser(User user);

    @GET("groupsByCreator.php")
    Single<GroupsResponse> getGroupsByCreator(@Query("groupCreator") String userId);

    @GET("getGroups.php")
    Single<GroupsResponse> getGroups(@Query("userId") String userId);

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


    /* Рахунки */

    @GET("accounts/getAccounts.php")
    Single<AccountResponse> getAccountsByGroup(@Query("groupId") String groupId);

    @GET("accounts/getAccountById.php")
    Single<Account> getAccountById(@Query("id_account") String accountId);

    @FormUrlEncoded
    @POST("accounts/addAccountToGroup.php")
    Single<SuccessResponse> addAccount(@Field("groupId") String groupId,
                                       @Field("account_title") String accountTitle,
                                       @Field("start_amount") double startAmount,
                                       @Field("color") String color);

    @POST("accounts/updateAccount.php")
    Single<SuccessResponse> updateAccount(@Body Account account);

    @FormUrlEncoded
    @POST("accounts/deleteAccount.php")
    Single<SuccessResponse> deleteAccount(@Field("id_account") String idAccount);

    /* Витрати та доходи */

    @GET("expenses/getExpensesByAccount.php")
    Single<ExpensesResponse> getExpensesByAccount(@Query("accountId") String accountId);

    @GET("expenses/getExpenseById.php")
    Single<Expense> getExpenseById(@Query("expenseId") String expenseId);

    @GET("expenses/getExpensesByGroup.php")
    Single<ExpensesResponse> getExpensesByGroup(@Query("groupId") String groupId);

    @POST("expenses/addExpense.php")
    Single<SuccessResponse> addExpense(@Body Expense expense);

    @POST("expenses/updateExpense.php")
    Single<SuccessResponse> updateExpense(@Body Expense expense);

    /* Діаграми */
    @GET("diagramdata/expensesByCategoryDiagram.php")
    Single<DiagramResponse> getExpensesByCategoryDiagram(@Query("groupId") String groupId);

    @GET("diagramdata/expensesByUserDiagram.php")
    Single<DiagramResponse> getExpensesByUserDiagram(@Query("groupId") String groupId);

    /* Валюти */
    @GET("currencies/getCurrencies.php")
    Single<CurrencyResponse> getCurrencies(@Query("groupId") String groupId);
}
