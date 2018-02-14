package com.example.productmanagment;

/**
 * Created by Ivan on 22.11.2017.
 */

public interface BaseView<T extends BasePresenter> {
    void setPresenter(T presenter);
}
