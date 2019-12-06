package com.example.productmanagment.data.source.remote;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.Goal;
import com.example.productmanagment.data.models.Group;
import com.example.productmanagment.data.models.MyCurrency;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.remote.remotemodels.ExpensePredictionResponse;
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

import java.util.Currency;

import io.reactivex.Flowable;
import io.reactivex.Single;

public class RemoteDataRepository implements RemoteData {
    private RemoteDataSource remoteDataSource;

    public RemoteDataRepository() {
        this.remoteDataSource = new RemoteDataSource();
    }

    @Override
    public Single<SuccessResponse> signUpUser(User user) {
        return remoteDataSource.signUpUser(user);
    }

    @Override
    public Single<User> signInUser(String email, String password) {
        return remoteDataSource.signInUser(email, password);
    }

    @Override
    public Single<GroupsResponse> getGroupsByCreator(String groupCreator) {
        return remoteDataSource.getGroupsByCreator(groupCreator);
    }

    @Override
    public Single<GroupsResponse> getGroups(String userId) {
        return remoteDataSource.getGroups(userId);
    }

    @Override
    public Single<Group> getGroupById(String groupId) {
        return remoteDataSource.getGroupById(groupId);
    }

    @Override
    public Single<UsersResponse> getUsersByGroup(String groupId) {
        return remoteDataSource.getUsersByGroup(groupId);
    }

    @Override
    public Single<SuccessResponse> addGroup(Group group) {
        return remoteDataSource.addGroup(group);
    }

    @Override
    public Single<SuccessResponse> addUserToGroup(String userEmail, String groupId) {
        return remoteDataSource.addUserToGroup(userEmail, groupId);
    }

    @Override
    public Single<SuccessResponse> renameGroup(String groupTitle, String groupId) {
        return remoteDataSource.renameGroup(groupTitle, groupId);
    }

    @Override
    public Single<SuccessResponse> getUserRights(String groupId, String userId) {
        return remoteDataSource.getUserRights(groupId, userId);
    }

    @Override
    public Single<SuccessResponse> setUserRights(String groupId, String userId, String userRights) {
        return remoteDataSource.setUserRights(groupId, userId, userRights);
    }

    @Override
    public Single<AccountResponse> getAccountsByGroup(String groupId) {
        return remoteDataSource.getAccountsByGroup(groupId);
    }

    @Override
    public Single<Account> getAccountById(String accountId) {
        return remoteDataSource.getAccountById(accountId);
    }

    @Override
    public Single<SuccessResponse> addAccount(String groupId, Account account) {
        return remoteDataSource.addAccount(groupId, account);
    }

    @Override
    public Single<SuccessResponse> updateAccount(Account account) {
        return remoteDataSource.updateAccount(account);
    }

    @Override
    public Single<SuccessResponse> deleteAccount(String accountId) {
        return remoteDataSource.deleteAccount(accountId);
    }

    @Override
    public Single<ExpensesResponse> getExpensesByAccount(String accountId) {
        return remoteDataSource.getExpensesByAccount(accountId);
    }

    @Override
    public Single<Expense> getExpenseById(String expenseId) {
        return remoteDataSource.getExpenseById(expenseId);
    }

    @Override
    public Single<SuccessResponse> addExpense(Expense expense) {
        return remoteDataSource.addExpense(expense);
    }

    @Override
    public Single<SuccessResponse> updateExpense(Expense expense) {
        return remoteDataSource.updateExpense(expense);
    }

    @Override
    public Single<SuccessResponse> deleteExpense(String idExpense) {
        return null;
    }

    @Override
    public Single<ExpensesResponse> getExpensesByCategory(String category, String groupId) {
        return remoteDataSource.getExpensesByCategory(category, groupId);
    }

    @Override
    public Single<DiagramResponse> getExpensesByCategoryDiagram(String groupId) {
        return remoteDataSource.getExpensesByCategoryDiagram(groupId);
    }

    @Override
    public Single<DiagramResponse> getIncomesByCategoryDiagram(String groupId) {
        return remoteDataSource.getIncomesByCategoryDiagram(groupId);
    }

    @Override
    public Single<DiagramResponse> getExpensesByUserDiagram(String groupId) {
        return remoteDataSource.getExpensesByUserDiagram(groupId);
    }

    @Override
    public Single<CurrencyResponse> getCurrencies(String groupId) {
        return remoteDataSource.getCurrencies(groupId);
    }

    @Override
    public Single<MyCurrency> getCurrencyById(String currencyId) {
        return remoteDataSource.getCurrencyById(currencyId);
    }

    @Override
    public Single<SuccessResponse> addCurrency(MyCurrency currency) {
        return remoteDataSource.addCurrency(currency);
    }

    @Override
    public Single<SuccessResponse> updateCurrency(MyCurrency currency) {
        return remoteDataSource.updateCurrency(currency);
    }

    @Override
    public Single<SuccessResponse> deleteCurrency(String currencyId) {
        return remoteDataSource.deleteCurrency(currencyId);
    }

    public Single<GroupsResponse> getHouseholds(String userId){
        return remoteDataSource.getHouseholds(userId);
    }

    @Override
    public Single<ReportResponse> getReport(String categoryId, String groupId) {
        return remoteDataSource.getReport(categoryId, groupId);
    }

    public Single<ExpensePredictionResponse> getSubcategoryForecast(String household_id, String subcategotyId, String period) {
        return remoteDataSource.getSubcategoryForecast(household_id, subcategotyId, period);
    }

    public Single<SubcategoryResponse> getSubcategories() {
        return remoteDataSource.getSubcategories();
    }

    public Single<GoalResponse> getGoals(String household_id) {
        return remoteDataSource.getGoals(household_id);
    }

    public Single<Goal> getGoalById(String goalId) {
        return remoteDataSource.getGoalById(goalId);
    }

    public Single<GoalResponse> createGoal(Goal goal, String householdId) {
        return remoteDataSource.createGoal(goal.getTitle(),
                goal.getStartDate(), "0.00", goal.getWantedDate(), String.valueOf(goal.getNeededAmount()),
                String.valueOf(goal.getCurrency().getId()),
                householdId);
    }

    public Single<GoalResponse> updateGoal(Goal goal, String goalId) {
        return remoteDataSource.updateGoal(goalId, goal.getTitle(),
                goal.getStartDate(), String.valueOf(goal.getGoalStartAmount()), goal.getWantedDate(), String.valueOf(goal.getNeededAmount()));
    }

    public Single<GoalResponse> deleteGoal(String goalId){
        return remoteDataSource.deleteGoal(goalId);
    }

    public Single<GoalResponse> addGoalAmount(String goal_id, String user_id, double amount){
        return remoteDataSource.addGoalAmount(goal_id, user_id, amount);
    }

    /*---------------------------------------------------------------------------------------*/

    public Single<AccountResponse> getAccounts(String household_id) {
        return remoteDataSource.getAccounts(household_id);
    }

    public Single<Account> getAccountByIdRemote(String goalId) {
        return remoteDataSource.getAccountByIdRemote(goalId);
    }

    public Single<AccountResponse> createAccountRemote(String goalTitle, String goalStartDate,
                                                       String goalStartAmount, String goalWantetDate, String goalWantedAmount, String currencyId, String householdId) {
        return remoteDataSource.createAccountRemote(goalTitle, goalStartDate, goalStartAmount, goalWantetDate, goalWantedAmount, currencyId, householdId);
    }

    public Single<AccountResponse> updateAccountRemote( String goal_id, String goalTitle, String goalStartDate,
                                                        String goalStartAmount, String goalWantetDate, String goalWantedAmount) {
        return remoteDataSource.updateAccountRemote(goal_id, goalTitle, goalStartDate, goalStartAmount, goalWantetDate, goalWantedAmount);
    }

    public Single<AccountResponse> deleteAccountRemote(String accountId){
        return remoteDataSource.deleteAccountRemote(accountId);
    }

    /*---------------------------------------------------------------------------------------*/

    public Single<ExpensesResponse> getTransactions(String account_id, String household_id) {
        return remoteDataSource.getTransactions(account_id, household_id);
    }

    public Single<Expense> getTransactionById(String transactionId) {
        return remoteDataSource.getTransactionById(transactionId);
    }

    public Single<ExpensesResponse> createTransaction(Expense expense) {
        return remoteDataSource.createTransaction(expense.getNote(), expense.getDatetime(),
                expense.getReceiver(), String.valueOf(expense.getCost()),
                expense.getPlace(), expense.getTypeOfPayment(), expense.getExpenseType(),
                String.valueOf(expense.getAccount().getId()), String.valueOf(expense.getCategory().getId()),
                String.valueOf(expense.getUser().getUserId()));
    }

    public Single<ExpensesResponse> updateTransaction(Expense expense) {
        return remoteDataSource.updateTransaction(expense.getNote(), expense.getDatetime(),
                expense.getReceiver(), String.valueOf(expense.getCost()),
                expense.getPlace(), expense.getTypeOfPayment(), expense.getExpenseType(),
                String.valueOf(expense.getAccount().getId()), String.valueOf(expense.getCategory().getId()),
                String.valueOf(expense.getUser().getUserId()));
    }

    public Single<ExpensesResponse> deleteTransaction(String transactionId){
        return remoteDataSource.deleteTransaction(transactionId);
    }

    public Single<RecommendationsResponse> getRecommendations(String household_id){
        return remoteDataSource.getRecommendations(household_id);
    }
}
