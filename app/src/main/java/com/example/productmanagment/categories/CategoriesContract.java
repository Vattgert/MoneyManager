package com.example.productmanagment.categories;

import android.support.annotation.NonNull;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Subcategory;

import java.util.List;

/**
 * Created by Ivan on 22.01.2018.
 */

public interface CategoriesContract {
    interface View extends BaseView<Presenter>{
        void setLoadingIndicator(boolean active);
        void showCategories(List<Category> categoryList);
        void showLoadingCategoriesError();
        void showNoCategories();
        void showSubcategories(int categoryId);
    }

    interface Presenter extends BasePresenter{
        void loadCategories();
        void openSubcategories(int categoryId);
        void favouriteCategory(@NonNull Category completedTask);
    }
}
