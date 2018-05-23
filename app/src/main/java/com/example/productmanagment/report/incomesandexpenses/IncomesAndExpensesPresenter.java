package com.example.productmanagment.report.incomesandexpenses;

import android.util.Log;

import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.models.report.CategoryReport;
import com.example.productmanagment.data.source.categories.CategoriesRepository;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class IncomesAndExpensesPresenter implements IncomesAndExpensesContract.Presenter {
    int groupId;
    IncomesAndExpensesContract.View view;
    ExpensesRepository repository;
    RemoteDataRepository remoteDataRepository;
    BaseSchedulerProvider provider;
    CompositeDisposable compositeDisposable;

    public IncomesAndExpensesPresenter(int groupId, IncomesAndExpensesContract.View view,
                                       ExpensesRepository repository,
                                       BaseSchedulerProvider provider) {
        this.groupId = groupId;
        this.view = view;
        this.repository = repository;
        this.remoteDataRepository = new RemoteDataRepository();
        this.provider = provider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void loadCategories() {
        Disposable disposable = repository.getCategoryReport()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processListHeaders, throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadSubcategories() {

    }

    @Override
    public void loadSubcategoriesWithData() {

    }

    @Override
    public void subscribe() {
        loadCategories();
        loadSubcategories();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    private void processListHeaders(List<CategoryReport> categoryList){
        view.setHeadersListData(categoryList);
        for(CategoryReport report : categoryList){
            Log.wtf("ReportLog", report.getCategory().getName() + " " +report.getAmount() + "\n");
        }
        for (CategoryReport c :  categoryList) {
            Category category = c.getCategory();
            if(groupId == -1) {
                Disposable disposable = repository.getSubcategoryReport(String.valueOf(category.getId())).subscribeOn(provider.io()).observeOn(provider.ui()).subscribe(subcategories -> {
                    view.setSubItemsListData(c, subcategories);
                });
            }
            else{
                Disposable disposable = remoteDataRepository.getReport(String.valueOf(category.getId()), String.valueOf(this.groupId))
                        .subscribeOn(provider.io())
                        .observeOn(provider.ui())
                        .subscribe(reportResponse -> view.setSubItemsListData(c, reportResponse.reportBalances));
            }
        }
    }
}
