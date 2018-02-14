package com.example.productmanagment.data.source.categories;

import android.support.annotation.NonNull;

import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Subcategory;

import java.util.List;
import java.util.Optional;

import io.reactivex.Flowable;

/**
 * Created by Ivan on 12.11.2017.
 */

public interface CategoriesDataSource {
    Flowable<List<Category>> getCategories();

    Flowable<List<Subcategory>> getSubcategories(@NonNull int categoryId);

    Flowable<Optional<Category>> getCategory(@NonNull String categoryId);

    void saveCategory(@NonNull Category category);

    void refreshCategories();

    void deleteAllCategories();

    void deleteCategory(@NonNull String categoryId);
}
