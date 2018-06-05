package com.example.productmanagment.templatedetailandedit;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.util.Log;

import com.example.productmanagment.addexpenses.AddExpenseActivity;
import com.example.productmanagment.categories.CategoryActivity;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.models.Template;
import com.example.productmanagment.data.models.User;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

public class TemplateDetailAndEditPresenter implements TemplateDetailAndEditContract.Presenter {
    String templateId;
    TemplateDetailAndEditContract.View view;
    ExpensesRepository repository;
    BaseSchedulerProvider provider;
    Category chosenCategory;
    Template template;


    public TemplateDetailAndEditPresenter(String templateId, TemplateDetailAndEditContract.View view, ExpensesRepository repository, BaseSchedulerProvider provider) {
        this.templateId = templateId;
        this.view = view;
        this.repository = repository;
        this.provider = provider;
        this.view.setPresenter(this);
    }

    @Override
    public void editTemplate(Template template) {
        repository.updateTemplate(this.templateId, template);
    }

    @Override
    public Category getChosenCategory() {
        if (chosenCategory != null)
            return chosenCategory;
        return template.getCategory();
    }

    @Override
    public User getCurrentUser() {
        return null;
    }

    @Override
    public void deleteTemplate() {
        repository.deleteTemplate(this.templateId);
    }

    @Override
    public void createExpenseByTemplate() {

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

    @Override
    public void subscribe() {
        repository.getTemplateById(templateId)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(this::showTemplate, throwable -> Log.wtf("MyLog", throwable.getMessage()));
    }

    private void showTemplate(@NonNull Template expense){
        this.template = expense;
        view.showTitle(expense.getTitle());
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

    @Override
    public void unsubscribe() {

    }
}
