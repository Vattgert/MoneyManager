package com.example.productmanagment.places;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;

import java.util.List;

public interface PlacesContract {
    interface Presenter extends BasePresenter {
        void loadAddresses();
        void loadAddressesByDate(String fdate, String sdate);
    }

    interface View extends BaseView<Presenter>{
        void showAddresses(List<String> addresses);
        void getMap();
    }
}
