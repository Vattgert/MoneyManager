package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.models.MyCurrency;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.remotemodels.ExpensePredictionResponse;
import com.example.productmanagment.data.source.remote.remotemodels.ExpenseStructureRemote;
import com.example.productmanagment.data.source.remote.remotemodels.RecommendationsResponse;
import com.example.productmanagment.data.source.remote.remotemodels.SubcategoryResponse;
import com.example.productmanagment.data.source.remote.responses.AccountResponse;
import com.example.productmanagment.data.source.remote.responses.CurrencyResponse;
import com.example.productmanagment.data.source.remote.responses.DiagramResponse;
import com.example.productmanagment.data.source.remote.responses.ExpensesResponse;
import com.example.productmanagment.data.source.remote.responses.GoalResponse;
import com.example.productmanagment.data.source.remote.responses.GroupsResponse;
import com.example.productmanagment.data.source.remote.responses.ReportResponse;
import com.example.productmanagment.data.source.remote.responses.SuccessResponse;
import com.example.productmanagment.data.source.remote.responses.TimeSeriesForecast;
import com.example.productmanagment.data.source.remote.responses.UsersResponse;
import com.jakewharton.retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;

import io.reactivex.Single;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.Field;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class RemoteDataSource implements RemoteData{
    private MoneyManagerApi moneyManagerApi;
    private NewAPI newAPI;

    private Retrofit retrofit;
    private Retrofit retrofit2;

    private HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
    private OkHttpClient.Builder httpClient = new OkHttpClient.Builder();


    public RemoteDataSource() {
        logging.setLevel(HttpLoggingInterceptor.Level.BODY);
        httpClient.addInterceptor(logging);  // <-- this is the important line!

        retrofit = new Retrofit.Builder()
                .baseUrl(MoneyManagerApi.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();
        retrofit2 = new Retrofit.Builder().baseUrl(NewAPI.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(httpClient.build())
                .build();

        moneyManagerApi = retrofit.create(MoneyManagerApi.class);
        newAPI = retrofit2.create(NewAPI.class);
    }


    @Override
    public Single<SuccessResponse> signUpUser(User user) {
        return moneyManagerApi.signUpUser(user.getEmail(), user.getLogin(), user.getPassword());
    }

    //////////

    @Override
    public Single<GroupsResponse> getGroupsByCreator(String groupCreator) {
        return moneyManagerApi.getGroupsByCreator(groupCreator);
    }

    @Override
    public Single<GroupsResponse> getGroups(String userId) {
        return moneyManagerApi.getGroups(userId);
    }

    @Override
    public Single<Group> getGroupById(String groupId) {
        return moneyManagerApi.getGroupById(groupId);
    }

    @Override
    public Single<UsersResponse> getUsersByGroup(String groupId) {
        return moneyManagerApi.getUsersByGroup(groupId);
    }

    @Override
    public Single<SuccessResponse> addGroup(Group group) {
        return moneyManagerApi.addGroup(group.getTitle(), group.getGroupOwner());
    }

    @Override
    public Single<SuccessResponse> addUserToGroup(String userEmail, String groupId) {
        return moneyManagerApi.addUserToGroup(userEmail, groupId);
    }

    @Override
    public Single<SuccessResponse> renameGroup(String groupTitle, String groupId) {
        return moneyManagerApi.renameGroup(groupTitle, groupId);
    }

    @Override
    public Single<SuccessResponse> getUserRights(String groupId, String userId) {
        return moneyManagerApi.getUsersRights(groupId, userId);
    }

    @Override
    public Single<SuccessResponse> setUserRights(String groupId, String userId, String userRights) {
        return moneyManagerApi.setUserRights(groupId, userId, userRights);
    }

    @Override
    public Single<AccountResponse> getAccountsByGroup(String groupId) {
        return moneyManagerApi.getAccountsByGroup(groupId);
    }

    @Override
    public Single<Account> getAccountById(String accountId) {
        return moneyManagerApi.getAccountById(accountId);
    }

    @Override
    public Single<SuccessResponse> addAccount(String groupId, Account account) {
        return moneyManagerApi.addAccount(groupId, account.getName(), account.getValue().doubleValue(), account.getColor());
    }

    @Override
    public Single<SuccessResponse> updateAccount(Account account) {
        return moneyManagerApi.updateAccount(account);
    }

    @Override
    public Single<SuccessResponse> deleteAccount(String accountId) {
        return moneyManagerApi.deleteAccount(accountId);
    }

    @Override
    public Single<ExpensesResponse> getExpensesByAccount(String accountId) {
        return moneyManagerApi.getExpensesByAccount(accountId);
    }

    @Override
    public Single<Expense> getExpenseById(String expenseId) {
        return moneyManagerApi.getExpenseById(expenseId);
    }

    @Override
    public Single<SuccessResponse> addExpense(Expense expense) {
        return moneyManagerApi.addExpense(expense);
    }

    @Override
    public Single<SuccessResponse> updateExpense(Expense expense) {
        return moneyManagerApi.updateExpense(expense);
    }

    @Override
    public Single<SuccessResponse> deleteExpense(String idExpense) {
        return null;
    }

    @Override
    public Single<ExpensesResponse> getExpensesByCategory(String category, String groupId) {
        return moneyManagerApi.getExpensesByCategory(category, groupId);
    }

    @Override
    public Single<DiagramResponse> getExpensesByCategoryDiagram(String groupId) {
        return moneyManagerApi.getExpensesByCategoryDiagram(groupId);
    }

    @Override
    public Single<DiagramResponse> getIncomesByCategoryDiagram(String groupId) {
        return moneyManagerApi.getIncomesByCategoryDiagram(groupId);
    }

    @Override
    public Single<DiagramResponse> getExpensesByUserDiagram(String groupId) {
        return moneyManagerApi.getExpensesByUserDiagram(groupId);
    }

    @Override
    public Single<CurrencyResponse> getCurrencies(String groupId) {
        return moneyManagerApi.getCurrencies(groupId);
    }

    @Override
    public Single<MyCurrency> getCurrencyById(String currencyId) {
        return moneyManagerApi.getCurrencyById(currencyId);
    }

    @Override
    public Single<SuccessResponse> addCurrency(MyCurrency currency) {
        return moneyManagerApi.addCurrency(currency);
    }

    @Override
    public Single<SuccessResponse> updateCurrency(MyCurrency currency) {
        return moneyManagerApi.editCurrency(currency);
    }

    @Override
    public Single<SuccessResponse> deleteCurrency(String currencyId) {
        return moneyManagerApi.deleteCurrency(currencyId);
    }

    @Override
    public Single<ReportResponse> getReport(String categoryId, String groupId) {
        return moneyManagerApi.getReport(categoryId, groupId);
    }


    //New API

    @Override
    public Single<User> signInUser(String  email, String password) {
        //return moneyManagerApi.signInUser(email, password);
        return newAPI.signInUser(email, password);
    }

    public Single<GoalResponse> getGoals(String household_id) {
        return newAPI.getGoals(household_id);
    }

    public Single<Goal> getGoalById(String goalId) {
        return newAPI.getGoalById(goalId);
    }

    public Single<GoalResponse> createGoal(String goalTitle, String goalStartDate,
                                           String goalStartAmount, String goalWantetDate, String goalWantedAmount, String currencyId, String householdId) {
        return newAPI.createGoal(goalTitle, goalStartDate, goalStartAmount, goalWantetDate, goalWantedAmount, currencyId, householdId);
    }

    public Single<GoalResponse> updateGoal( String goal_id, String goalTitle, String goalStartDate,
                                           String goalStartAmount, String goalWantetDate, String goalWantedAmount) {
        return newAPI.updateGoal(goal_id, goalTitle, goalStartDate, goalStartAmount, goalWantetDate, goalWantedAmount);
    }

    public Single<GoalResponse> deleteGoal(String goalId){
        return newAPI.deleteGoal(goalId);
    }

    Single<GoalResponse> addGoalAmount( String goal_id, String user_id, double amount){
        return newAPI.addGoalAmount(goal_id, user_id, amount);
    }

    /*---------------------------------------------------------------------------------------*/

    public Single<AccountResponse> getAccounts(String household_id) {
        return newAPI.getAccounts(household_id);
    }

    public Single<Account> getAccountByIdRemote(String goalId) {
        return newAPI.getAccountById(goalId);
    }

    public Single<AccountResponse> createAccountRemote(String goalTitle, String goalStartDate,
                                           String goalStartAmount, String goalWantetDate, String goalWantedAmount, String currencyId, String householdId) {
        return newAPI.createAccount(goalTitle, goalStartDate, goalStartAmount, goalWantetDate, goalWantedAmount, currencyId, householdId);
    }

    public Single<AccountResponse> updateAccountRemote( String goal_id, String goalTitle, String goalStartDate,
                                            String goalStartAmount, String goalWantetDate, String goalWantedAmount) {
        return newAPI.updateAccount(goal_id, goalTitle, goalStartDate, goalStartAmount, goalWantetDate, goalWantedAmount);
    }

    public Single<AccountResponse> deleteAccountRemote(String accountId){
        return newAPI.deleteAccount(accountId);
    }

    /*---------------------------------------------------------------------------------------*/

    public Single<GroupsResponse> getHouseholds(String userId){
        return newAPI.getHouseholds(userId);
    }

    /*---------------------------------------------------------------------------------------*/

    public Single<ExpensesResponse> getTransactions(String account_id, String household_id) {
        return newAPI.getTransactions(account_id, household_id);
    }

    public Single<Expense> getTransactionById(String transactionId) {
        return newAPI.getTransactionById(transactionId);
    }

    public Single<ExpensesResponse> createTransaction(String transactionTitle,
                                                        String transactionTs,
                                                        String receiver,
                                                        String moneyAmount,
                                                        String transactionPlace,
                                                        String paymentType,
                                                        String transactionType,
                                                        String accountId,
                                                        String categoryId,
                                                        String userId) {
        return newAPI.createTransaction(transactionTitle, transactionTs, receiver, moneyAmount, transactionPlace, paymentType, transactionType, accountId, categoryId, userId);
    }

    public Single<ExpensesResponse> updateTransaction(String transactionTitle,
                                                      String transactionTs,
                                                      String receiver,
                                                      String moneyAmount,
                                                      String transactionPlace,
                                                      String paymentType,
                                                      String transactionType,
                                                      String accountId,
                                                      String categoryId,
                                                      String userId,
                                                      String transactionId) {
        return newAPI.updateTransaction(transactionTitle, transactionTs, receiver, moneyAmount, transactionPlace, paymentType, transactionType, accountId, categoryId, userId, transactionId);
    }

    public Single<ExpensesResponse> deleteTransaction(String transactionId){
        return newAPI.deleteTransaction(transactionId);
    }

    /*---------------------------------------------------------------------------------------*/

    public Single<ExpensePredictionResponse> getSubcategoryForecast(String household_id, String subcategoryId, String period) {
        return newAPI.getSubcategoryForecast(household_id, subcategoryId, period);
    }

    public Single<SubcategoryResponse> getSubcategories() {
        return newAPI.getSubcategories();
    }

    /*------------------------------------------------------------------------------*/


    Single<RecommendationsResponse> getRecommendations(String household_id){
        return newAPI.getRecommendations(household_id);
    }

    public Single<ExpenseStructureRemote> getExpensesStructure(String householdId){
        return newAPI.getExpensesStructure(householdId);
    }

    public Single<ExpenseStructureRemote> getIncomesStructure(String householdId){
        return newAPI.getIncomesStructure(householdId);
    }

    public Single<ExpenseStructureRemote> getUserExpenses(String householdId){
        return newAPI.getUserExpenses(householdId);
    }

    public Single<ExpenseStructureRemote> getUserIncomes(String householdId){
        return newAPI.getUserIncomes(householdId);
    }

    Single<CurrencyResponse> getCurrenciesRemote(String householdId){
        return newAPI.getCurrencies(householdId);
    }
}
