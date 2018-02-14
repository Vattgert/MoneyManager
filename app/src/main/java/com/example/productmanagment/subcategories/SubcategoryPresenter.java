package com.example.productmanagment.subcategories;

import android.util.Log;

import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.source.categories.CategoriesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 10.02.2018.
 */

public class SubcategoryPresenter implements SubcategoriesContract.Presenter {
    private int categoryId;
    private CategoriesRepository categoriesRepository;
    private SubcategoriesContract.View view;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;

    public SubcategoryPresenter(int categoryId, CategoriesRepository categoriesRepository, SubcategoriesContract.View view, BaseSchedulerProvider schedulerProvider) {
        this.categoryId = categoryId;
        this.view = view;
        this.categoriesRepository = categoriesRepository;
        this.schedulerProvider = schedulerProvider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        showSubcategories(categoryId);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void showSubcategories(int categoryId) {
        Disposable disposable = categoriesRepository.getSubcategories(categoryId)
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processSubcategories, throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    private void processSubcategories(List<Subcategory> subcategories){
        view.showSubcategories(subcategories);
    }
}
