package com.example.productmanagment.data.source.users;

import android.provider.BaseColumns;

public class UserPersistenceContract {

    public UserPersistenceContract() {}

    public abstract class CategoryEntry implements BaseColumns {
        public static final String TABLE_NAME = "user";
        public static final String COLUMN_NAME_ENTRY_ID = "id_user";
        public static final String COLUMN_NAME_EMAIL = "email";
        public static final String COLUMN_NAME_PASSWORD = "password";
        public static final String COLUMN_NAME_LOGIN = "login";
        public static final String COLUMN_NAME_ICON = "icon";
    }
}
