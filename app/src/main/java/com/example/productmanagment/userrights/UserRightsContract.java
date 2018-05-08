package com.example.productmanagment.userrights;
import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.UserRight;

import java.util.List;

public interface UserRightsContract {
    interface Presenter extends BasePresenter{
        void setUserRights(String userRights);
        void loadUserWithRights(String userId, String groupId);
        void loadUserRights();
}

    interface View extends BaseView<Presenter>{
        void showUserRights(List<UserRight> userRights);
        void setUserRightsEnabled(String[] rights);
        void showMessage(String text);
    }
}
