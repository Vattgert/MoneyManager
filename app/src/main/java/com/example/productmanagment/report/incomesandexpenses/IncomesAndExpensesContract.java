package com.example.productmanagment.report.incomesandexpenses;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Subcategory;

import java.util.HashMap;
import java.util.List;

public interface IncomesAndExpensesContract {
    interface Presenter extends BasePresenter{
        void loadCategories();
        void loadSubcategories();
        void loadSubcategoriesWithData();

    }

    interface View extends BaseView<Presenter>{
        void setHeadersListData(List<Category> categoryList);
        void setSubItemsListData(Category category, List<Subcategory> subcategories);
        List<Category> getHeaders();
    }
}

