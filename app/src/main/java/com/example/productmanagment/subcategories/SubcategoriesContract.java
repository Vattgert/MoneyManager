package com.example.productmanagment.subcategories;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Subcategory;

import java.util.List;

/**
 * Created by Ivan on 10.02.2018.
 */

public interface SubcategoriesContract {
    public interface View extends BaseView<Presenter>{
        public void showSubcategories(List<Subcategory> subcategoryList);
    }

    public interface Presenter extends BasePresenter{
        public void showSubcategories(int categoryId);
    }
}
