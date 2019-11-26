package com.example.productmanagment.data.source.remote.responses;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.models.MyCurrency;
import com.example.productmanagment.data.models.User;

import io.reactivex.Single;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Query;

public interface NewAPI {

    String BASE_URL = "https://onlooker-api.herokuapp.com";

    /*  Sign up and authorization */
    @FormUrlEncoded
    @POST("signup")
    Single<SuccessResponse> signUpUser(@Field("user_email") String userEmail,
                                       @Field("user_login") String userLogin,
                                       @Field("user_password") String userPassword);

    @FormUrlEncoded
    @POST("login")
    Single<User> signInUser(@Field("user_email") String userEmail,
                            @Field("user_password") String userPassword);

    /*  Groups */

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


    /*  Accounts  */

    @GET("accounts/getAccounts.php")
    Single<AccountResponse> getAccountsByHousehold(@Query("household_id") String groupId);

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

    @GET("expenses/getExpensesByCategory.php")
    Single<ExpensesResponse> getExpensesByCategory(@Query("category") String category, @Query("groupId") String groupId);

    @GET("expenses/getExpensesByGroup.php")
    Single<ExpensesResponse> getExpensesByGroup(@Query("groupId") String groupId);

    @POST("expenses/addExpense.php")
    Single<SuccessResponse> addExpense(@Body Expense expense);

    @POST("expenses/updateExpense.php")
    Single<SuccessResponse> updateExpense(@Body Expense expense);

    /* Діаграми */
    @GET("diagramdata/expensesByCategoryDiagram.php")
    Single<DiagramResponse> getExpensesByCategoryDiagram(@Query("groupId") String groupId);

    @GET("diagramdata/incomesByCategoryDiagram.php")
    Single<DiagramResponse> getIncomesByCategoryDiagram(@Query("groupId") String groupId);

    @GET("diagramdata/expensesByUserDiagram.php")
    Single<DiagramResponse> getExpensesByUserDiagram(@Query("groupId") String groupId);

    /* Валюти */
    @GET("currencies/getCurrencies.php")
    Single<CurrencyResponse> getCurrencies(@Query("groupId") String groupId);

    @GET("currencies/getCurrencyById.php")
    Single<MyCurrency> getCurrencyById(@Query("id_currency") String currencyId);

    @POST("currencies/addCurrency.php")
    Single<SuccessResponse> addCurrency(@Body MyCurrency currency);

    @POST("currencies/editCurrency.php")
    Single<SuccessResponse> editCurrency(@Body MyCurrency currency);

    @FormUrlEncoded
    @POST("currencies/deleteCurrency.php")
    Single<SuccessResponse> deleteCurrency(@Field("id_currency") String id_currency);

    /* Звіти */
    @GET("report/getReport.php")
    Single<ReportResponse> getReport(@Query("categoryId") String categoryId, @Query("groupId") String groupId);
}
