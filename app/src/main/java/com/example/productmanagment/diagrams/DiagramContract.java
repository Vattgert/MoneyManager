package com.example.productmanagment.diagrams;

import com.anychart.anychart.Cartesian;
import com.anychart.anychart.Chart;
import com.anychart.anychart.Pie;
import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;

/**
 * Created by Ivan on 09.03.2018.
 */

public interface DiagramContract {
    interface View extends BaseView<Presenter>{
        void setChart(Chart anyChart);
        void showMessage(String message);
        void showCategoryExpenses(int groupId, String category);
    }

    interface Presenter extends BasePresenter{
        void showDiagram(int type);
        void showDiagramByDate(int type, String fdate, String sdate);

    }
}
