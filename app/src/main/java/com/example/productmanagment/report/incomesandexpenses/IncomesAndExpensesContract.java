package com.example.productmanagment.report.incomesandexpenses;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.models.report.CategoryReport;
import com.example.productmanagment.data.models.report.SubcategoryReport;

import java.util.HashMap;
import java.util.List;

public interface IncomesAndExpensesContract {
    interface Presenter extends BasePresenter{
        void loadCategories();
        void loadSubcategories();
        void loadSubcategoriesWithData();

    }

    interface View extends BaseView<Presenter>{
        void setHeadersListData(List<CategoryReport> categoryList);
        void setSubItemsListData(CategoryReport category, List<SubcategoryReport> subcategories);
        List<CategoryReport> getHeaders();
    }
}

