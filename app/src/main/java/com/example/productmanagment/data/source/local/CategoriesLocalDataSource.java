package com.example.productmanagment.data.source.local;

import android.content.Context;
import android.database.Cursor;
import android.support.annotation.NonNull;
import android.text.TextUtils;

import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.source.categories.CategoriesDataSource;
import com.example.productmanagment.data.source.categories.CategoryPersistenceContract;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;
import com.squareup.sqlbrite2.BriteDatabase;
import com.squareup.sqlbrite2.SqlBrite;

import java.util.List;
import java.util.Optional;


import io.reactivex.BackpressureStrategy;
import io.reactivex.Flowable;
import io.reactivex.functions.Function;

/**
 * Created by Ivan on 23.01.2018.
 */

public class CategoriesLocalDataSource implements CategoriesDataSource {
    private static CategoriesLocalDataSource INSTANCE;
    private final BriteDatabase databaseHelper;

    private Function<Cursor, Category> categoryMapperFunction;
    private Function<Cursor, Subcategory> subcategoryMapperFunction;

    private CategoriesLocalDataSource(Context context, BaseSchedulerProvider schedulerProvider) {
        SqlBrite sqlBrite = new SqlBrite.Builder().build();
        ExpenseManagerDatabaseHelper helper = new ExpenseManagerDatabaseHelper(context);
        databaseHelper = sqlBrite.wrapDatabaseHelper(helper, schedulerProvider.io());
        categoryMapperFunction = this::getCategory;
        subcategoryMapperFunction = this::getSubcategory;
    }

    public static CategoriesLocalDataSource getInstance(@NonNull Context context,
            @NonNull BaseSchedulerProvider schedulerProvider) {
        if (INSTANCE == null) {
            INSTANCE = new CategoriesLocalDataSource(context, schedulerProvider);
        }
        return INSTANCE;
    }

    public static void destroyInstance() {
        INSTANCE = null;
    }

    @NonNull
    private Category getCategory(@NonNull Cursor c) {
        int itemId = c.getInt(c.getColumnIndexOrThrow(CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_ENTRY_ID));
        String title = c.getString(c.getColumnIndexOrThrow(CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_TITLE));
        String icon = c.getString(c.getColumnIndexOrThrow(CategoryPersistenceContract.CategoryEntry.COLUMN_ICON));
        return new Category(itemId, title, icon);
    }

    @NonNull
    private Subcategory getSubcategory(@NonNull Cursor c){
        int itemId = c.getInt(c.getColumnIndexOrThrow(CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID));
        String title = c.getString(c.getColumnIndexOrThrow(CategoryPersistenceContract.SubcategoryEntry.COLUMN_TITLE));
        int categoryId = c.getInt(c.getColumnIndexOrThrow(CategoryPersistenceContract.SubcategoryEntry.COLUMN_CATEGORY_ID));
        int isFavourite = c.getInt(c.getColumnIndexOrThrow(CategoryPersistenceContract.SubcategoryEntry.COLUMN_IS_FAVOURITE));
        return new Subcategory(itemId, title, categoryId, isFavourite);
    }

    @Override
    public Flowable<List<Category>> getCategories() {
        String[] projection = { CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_ENTRY_ID,
                CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_TITLE,
                CategoryPersistenceContract.CategoryEntry.COLUMN_ICON};
        String sql = String.format("SELECT %s FROM %s ",
                TextUtils.join(",", projection),
                CategoryPersistenceContract.CategoryEntry.TABLE_NAME);
        return databaseHelper.createQuery(CategoryPersistenceContract.CategoryEntry.TABLE_NAME, sql)
                .mapToList(categoryMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
    public Flowable<List<Subcategory>> getSubcategories(@NonNull int categoryId){
        String[] projection = { CategoryPersistenceContract.SubcategoryEntry.COLUMN_ID,
                CategoryPersistenceContract.SubcategoryEntry.COLUMN_CATEGORY_ID,
                CategoryPersistenceContract.SubcategoryEntry.COLUMN_IS_FAVOURITE,
                CategoryPersistenceContract.SubcategoryEntry.COLUMN_TITLE};
        String sql = String.format("SELECT %s FROM %s WHERE %s = %s",
                TextUtils.join(",", projection),
                CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME,
                CategoryPersistenceContract.SubcategoryEntry.COLUMN_CATEGORY_ID,
                String.valueOf(categoryId));
        return databaseHelper.createQuery(CategoryPersistenceContract.SubcategoryEntry.TABLE_NAME, sql)
                .mapToList(subcategoryMapperFunction)
                .toFlowable(BackpressureStrategy.BUFFER);
    }

    @Override
   public Flowable<Optional<Category>> getCategory(@NonNull String categoryId) {
       /*  String[] projection = { CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_ENTRY_ID,
                CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_TITLE,
                CategoryPersistenceContract.CategoryEntry.COLUMN_ICON};
        String sql = String.format("SELECT %s FROM %s WHERE %s LIKE ?",
                TextUtils.join(",", projection),
                CategoryPersistenceContract.CategoryEntry.TABLE_NAME,
                CategoryPersistenceContract.CategoryEntry.COLUMN_NAME_ENTRY_ID);
        return databaseHelper.createQuery(CategoryPersistenceContract.CategoryEntry.TABLE_NAME, sql,
                                            categoryId)
                .mapToOneOrDefault(cursor -> categoryMapperFunction.apply(cursor), null)
                .toFlowable(BackpressureStrategy.BUFFER);*/
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
