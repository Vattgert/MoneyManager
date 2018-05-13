package com.example.productmanagment.debts;

import android.content.ContentValues;
import android.content.res.Resources;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Debt;
import com.example.productmanagment.data.models.Expense;
import com.example.productmanagment.data.models.ExpenseInformation;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 10.03.2018.
 */

public class DebtsPresenter implements DebtsContract.Presenter {
    private ExpensesRepository expensesRepository;
    private DebtsContract.View view;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;
    private Resources resources;

    public DebtsPresenter(ExpensesRepository expensesRepository, DebtsContract.View view, BaseSchedulerProvider schedulerProvider, Resources resources) {
        this.expensesRepository = expensesRepository;
        this.view = view;
        this.schedulerProvider = schedulerProvider;
        compositeDisposable = new CompositeDisposable();
        this.resources = resources;
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadDebts();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void loadDebts() {
        Disposable disposable = expensesRepository.getDebts()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processDebts, throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void openDebtDetails(@NonNull Debt debt) {
        view.showDebtDetailAndEdit(debt.getId());
    }

    @Override
    public void addNewDebt() {
        view.showAddDebt();
    }

    @Override
    public void payDebt(Debt debt) {
        view.showPayDebt(debt);
    }

    @Override
    public void enterDebtPartSum(Debt debt) {
        view.showEnterDebtSum(debt);
    }

    @Override
    public void closeDebtPart(Debt debt, String sum) {
        Expense expense = closingDebtExpense(debt);
        expense.setCost(Double.valueOf(sum));
        expensesRepository.saveDebtPayment(expense);
        loadDebts();
    }

    @Override
    public void closeDebt(Debt debt) {
        expensesRepository.saveDebtPayment(closingDebtExpense(debt));
        loadDebts();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void debtIsPayedMessage() {
        view.showDebtIsPayedMessage(resources.getString(R.string.debt_is_payed_message));
    }

    @Override
    public void openDebtPayments(int debtId) {
        view.showDebtPayments(debtId);
    }

    private void processDebts(List<Debt> debtList){
        view.showDebts(debtList);
    }

    private String getCurrentDate(){
        DateFormat dateFormat = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
        return dateFormat.format(date);
    }

    private Expense closingDebtExpense(Debt debt){
        Expense debtClose = new Expense();
        debtClose.setDebtId(debt.getId());
        debtClose.setCost(Double.valueOf(debt.getRemain()));
        debtClose.setCategory(new Subcategory(82, null));
        Account account = new Account();
        account.setId(debt.getAccountId());
        debtClose.setAccount(account);
        if(debt.getDebtType() == 1) {
            debtClose.setNote(resources.getString(R.string.borrowed, debt.getBorrower()));
            debtClose.setExpenseType("Витрата");
        }
        else {
            debtClose.setNote(resources.getString(R.string.lent, debt.getBorrower()));
            debtClose.setExpenseType("Дохід");
        }
        debtClose.setDate(getCurrentDate());
        debtClose.setReceiver(debt.getBorrower());
        return debtClose;
    }

}
