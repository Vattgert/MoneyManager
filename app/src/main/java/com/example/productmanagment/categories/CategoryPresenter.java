package com.example.productmanagment.categories;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.source.categories.CategoriesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 22.11.2017.
 */

public class CategoryPresenter implements CategoriesContract.Presenter {

    private CategoriesRepository categoriesRepository;
    private CategoriesContract.View view;
    private BaseSchedulerProvider schedulerProvider;
    private CompositeDisposable compositeDisposable;

    public CategoryPresenter(CategoriesRepository categoriesRepository, CategoriesContract.View view, BaseSchedulerProvider schedulerProvider) {
        this.view = view;
        this.categoriesRepository = categoriesRepository;
        this.schedulerProvider = schedulerProvider;
        compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        loadCategories();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }

    @Override
    public void loadCategories() {
        compositeDisposable.clear();
        Disposable disposable = categoriesRepository.getCategories()
                .subscribeOn(schedulerProvider.io())
                .observeOn(schedulerProvider.ui())
                .subscribe(this::processCategories, throwable -> Log.wtf("ErrorMsg", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void openSubcategories(int categoryId) {
        view.showSubcategories(categoryId);
    }

    @Override
    public void favouriteCategory(@NonNull Category completedTask) {

    }

    private void processCategories(List<Category> categoryList) {
        view.showCategories(categoryList);
    }

}
