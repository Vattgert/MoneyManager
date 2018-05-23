package com.example.productmanagment.categoryexpenses;

import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;

public class CategoryExpensesPresenter implements CategoryExpensesContract.Presenter {
    private int groupId;
    private String category;
    private CategoryExpensesContract.View view;
    private ExpensesRepository repository;
    private RemoteDataRepository remoteDataRepository;
    private BaseSchedulerProvider provider;
    private CompositeDisposable compositeDisposable;

    public CategoryExpensesPresenter(int groupId, String category, CategoryExpensesContract.View view,
                                     ExpensesRepository repository, BaseSchedulerProvider provider) {
        this.groupId = groupId;
        this.category = category;
        this.view = view;
        this.repository = repository;
        this.provider = provider;
        this.remoteDataRepository = new RemoteDataRepository();
        this.compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void loadByCategory(String category) {
        if(groupId == -1)
            compositeDisposable.clear();
        else
            remoteDataRepository.getExpensesByCategory(category, String.valueOf(groupId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(expensesResponse -> view.showExpenses(expensesResponse.expenses));
    }

    @Override
    public void subscribe() {
        loadByCategory(this.category);
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
