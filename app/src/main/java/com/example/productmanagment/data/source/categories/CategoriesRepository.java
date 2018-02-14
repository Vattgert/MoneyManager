package com.example.productmanagment.data.source.categories;

import android.support.annotation.NonNull;

import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Subcategory;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import io.reactivex.Flowable;

/**
 * Created by Ivan on 24.01.2018.
 */

public class CategoriesRepository implements CategoriesDataSource {
    private CategoriesDataSource localCategoriesDataSource;
    private static CategoriesRepository INSTANCE = null;

    private List<Category> categories = new ArrayList<>();

    private CategoriesRepository(@NonNull CategoriesDataSource localCategoriesDataSource) {
        this.localCategoriesDataSource = localCategoriesDataSource;
    }

    public static CategoriesRepository getInstance(@NonNull CategoriesDataSource localCategoriesDataSource) {
        if (INSTANCE == null) {
            INSTANCE = new CategoriesRepository(localCategoriesDataSource);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @Override
    public Flowable<List<Category>> getCategories() {
        return localCategoriesDataSource.getCategories();

    }

    @Override
    public Flowable<List<Subcategory>> getSubcategories(@NonNull int categoryId) {
        return localCategoriesDataSource.getSubcategories(categoryId);
    }

    @Override
    public Flowable<Optional<Category>> getCategory(@NonNull String categoryId) {
        return null;
    }

    @Override
    public void saveCategory(@NonNull Category category) {

    }

    @Override
    public void refreshCategories() {

    }

    @Override
    public void deleteAllCategories() {

    }

    @Override
    public void deleteCategory(@NonNull String categoryId) {

    }

}
