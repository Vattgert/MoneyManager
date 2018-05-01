package com.example.productmanagment.report.incomesandexpenses;

import android.util.Log;

import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.source.categories.CategoriesRepository;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class IncomesAndExpensesPresenter implements IncomesAndExpensesContract.Presenter {
    IncomesAndExpensesContract.View view;
    CategoriesRepository repository;
    BaseSchedulerProvider provider;
    CompositeDisposable compositeDisposable;

    public IncomesAndExpensesPresenter(IncomesAndExpensesContract.View view,
                                       CategoriesRepository repository,
                                       BaseSchedulerProvider provider) {
        this.view = view;
        this.repository = repository;
        this.provider = provider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void loadCategories() {
        Disposable disposable = repository.getCategories()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::processListHeaders);
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

    private void processListHeaders(List<Category> categoryList){
        view.setHeadersListData(categoryList);
        for (Category c :  categoryList) {
            Disposable disposable = repository.getSubcategories(c.getId())
                    .subscribeOn(provider.io())
                    .observeOn(provider.ui())
                    .subscribe(subcategories -> {
                        view.setSubItemsListData(c, subcategories);
                    });
        }
    }
}
