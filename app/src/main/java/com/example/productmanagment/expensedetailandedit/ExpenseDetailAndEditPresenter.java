package com.example.productmanagment.expensedetailandedit;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.productmanagment.addexpenses.AddExpenseActivity;
import com.example.productmanagment.categories.CategoryActivity;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.remote.responses.SuccessResponse;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.example.productmanagment.utils.schedulers.SchedulerProvider;
import com.google.android.gms.location.places.Place;
import com.google.android.gms.location.places.ui.PlacePicker;

import java.util.Optional;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 12.02.2018.
 */

public class ExpenseDetailAndEditPresenter implements ExpenseDetailAndEditContract.Presenter {
    int groupId;
    String expenseId;
    ExpenseDetailAndEditContract.View view;
    ExpensesRepository repository;
    RemoteDataRepository remoteDataRepository;
    CompositeDisposable compositeDisposable;
    BaseSchedulerProvider provider;
    Category chosenCategory;
    private Expense currentExpense;

    public ExpenseDetailAndEditPresenter(int groupId, String expenseId, ExpenseDetailAndEditContract.View view,
                                         ExpensesRepository repository,
                                         BaseSchedulerProvider provider) {
        this.groupId = groupId;
        this.expenseId = expenseId;
        this.view = view;
        this.repository = repository;
        this.remoteDataRepository = new RemoteDataRepository();
        this.compositeDisposable = new CompositeDisposable();
        this.provider = provider;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        if(groupId == -1) {
            repository.getAccounts()
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
                    .subscribe(view::setAccounts);
            openExpense();
        }
        else{
            remoteDataRepository.getAccounts(String.valueOf(groupId))
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
                    .subscribe(accountResponse -> view.setAccounts(accountResponse.getAccounts()));
            remoteDataRepository.getSubcategories()
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
                    .subscribe(subcategoryResponse -> {
                        view.setCategories(subcategoryResponse.subcategoriesList);
                    });
            remoteDataRepository.getTransactionById(expenseId)
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
                    .subscribe(this::showExpense);
        }
    }

    private void openExpense(){
        if(expenseId == null || expenseId.equals("")){
            view.showNoExpense();
            return;
        }
        Disposable disposable = repository.getExpenseById(expenseId)
                .subscribeOn(provider.computation())
                .observeOn(provider.ui())
                .subscribe(this::showExpense, throwable -> {Log.wtf("tag", throwable.getMessage()); });
        compositeDisposable.add(disposable);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public Category getChosenCategory() {
        if (chosenCategory != null)
            return chosenCategory;
        return currentExpense.getCategory();
    }

    @Override
    public User getCurrentUser() {
        return currentExpense.getUser();
    }

    @Override
    public void editExpense(Expense expense) {
        if(groupId == -1) {
            repository.updateExpense(expenseId, expense);
            view.finish();
        }
        else {
            expense.setId(Integer.valueOf(expenseId));
            remoteDataRepository.updateTransaction(expense)
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
                    .subscribe(expensesResponse -> {
                        if(expensesResponse.getSuccess().equals("0"))
                            view.showMessage("Інформація про транзакцію була змінена");
                    });
        }

    }

    @Override
    public void deleteTask() {
        if(groupId == -1)
            repository.deleteExpense(expenseId);
        else
            remoteDataRepository.deleteTransaction(expenseId)
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
                    .subscribe(expensesResponse -> {
                        if(expensesResponse.getSuccess().equals("0"))
                            view.showMessage("Інформація про транзакцію була видалена");
                    });
        view.finish();
    }

    private void showExpense(@NonNull Expense expense){
        this.currentExpense = expense;
        view.showCost(expense.getCost());
        view.showNote(expense.getNote());
        view.showReceiver(expense.getReceiver());
        view.showDate(expense.getDate());
        view.showTime(expense.getTime());
        view.showCategory(expense.getCategory().getName());
        view.showTypeOfPayment(expense.getTypeOfPayment());
        view.showAccount(expense.getAccount().getId());
        view.showExpenseType(expense.getExpenseType());
    }

    private void processSuccessResponse(SuccessResponse response){
        if(response.response.equals("success")){
            view.showMessage(response.data);
        }
        else{
            view.showMessage(response.errorData);
        }
        view.finish();
    }

    @Override
    public void result(int requestCode, int resultCode, Intent data) {
        if( resultCode == Activity.RESULT_OK){
            switch (requestCode){
                case CategoryActivity.GET_CATEGORY_REQUEST:
                    view.showCategory(data.getStringExtra("subcategoryTitle"));
                    chosenCategory = new Subcategory(data.getIntExtra("subcategoryId",0),
                            data.getStringExtra("subcategoryTitle"));
                    break;
                case AddExpenseActivity.REQUEST_PLACE_PICKER:
                    /*Place place = PlacePicker.getPlace(context,data);
                    view.setChosenPlace(String.format("%s", place.getAddress()));
                    view.setAddress(place);
                    break;*/
            }
        }
    }
}
