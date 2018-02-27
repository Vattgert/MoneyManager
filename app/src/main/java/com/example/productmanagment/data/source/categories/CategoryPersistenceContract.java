package com.example.productmanagment.data.source.categories;

import android.provider.BaseColumns;

/**
 * Created by Ivan on 08.11.2017.
 */

public class CategoryPersistenceContract {

    public CategoryPersistenceContract() {}

    public abstract class CategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "category";
        public static final String COLUMN_NAME_ENTRY_ID = "categoryId";
        public static final String COLUMN_NAME_TITLE = "title";
        public static final String COLUMN_ICON = "icon";
    }

    public abstract class SubcategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "subcategory";
        public static final String COLUMN_CATEGORY_ID = "category_id";
        public static final String COLUMN_ID = "id_subcategory";
        public static final String COLUMN_TITLE = "title";
        public static final String COLUMN_IS_FAVOURITE = "isFavourite";
    }
}
