package com.example.productmanagment.templates;

import android.support.annotation.NonNull;
import android.util.Log;

import com.example.productmanagment.data.models.Template;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

public class TemplatePresenter implements TemplateContract.Presenter {
    TemplateContract.View view;
    ExpensesRepository repository;
    BaseSchedulerProvider provider;
    CompositeDisposable compositeDisposable;

    public TemplatePresenter(TemplateContract.View view, ExpensesRepository repository, BaseSchedulerProvider provider) {
        this.view = view;
        this.repository = repository;
        this.provider = provider;
        this.compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void loadTemplates() {
        Disposable disposable = repository.getTemplates()
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(view::showTemplates, throwable -> Log.wtf("MyLog", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void openTemplateDetails(@NonNull Template requestedTask) {
        view.showTemplateDetail(String.valueOf(requestedTask.getId()));
    }

    @Override
    public void addTemplate() {
        view.showAddTemplate();
    }

    @Override
    public void result(int requestCode, int resultCode) {

    }

    @Override
    public void subscribe() {
        loadTemplates();
    }

    @Override
    public void unsubscribe() {
        compositeDisposable.clear();
    }
}
