package com.example.productmanagment.diagrams;

import android.util.Log;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.Cartesian;
import com.anychart.anychart.Chart;
import com.anychart.anychart.DataEntry;
import com.anychart.anychart.EnumsAlign;
import com.anychart.anychart.LegendLayout;
import com.anychart.anychart.Mapping;
import com.anychart.anychart.Pie;
import com.anychart.anychart.SeriesBar;
import com.anychart.anychart.Set;
import com.anychart.anychart.ValueDataEntry;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.utils.schedulers.BaseSchedulerProvider;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.disposables.Disposable;

/**
 * Created by Ivan on 09.03.2018.
 */

public class DiagramPresenter implements DiagramContract.Presenter {
    private String param;
    private ExpensesRepository repository;
    private DiagramContract.View view;
    private BaseSchedulerProvider provider;
    CompositeDisposable compositeDisposable;
    Chart chart = null;

    public DiagramPresenter(String param, ExpensesRepository repository, DiagramContract.View view, BaseSchedulerProvider provider) {
        this.param = param;
        this.repository = repository;
        this.view = view;
        this.provider = provider;
        this.compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {
        switch (param){
            case DiagramFragment.EXPENSE_STRUCTURE_DIAGRAM:
                chart = AnyChart.pie();
                loadExpenseStructureData((Pie)chart, "1");
                break;
            case DiagramFragment.INCOME_STRUCTURE_DIAGRAM:
                chart = AnyChart.pie();
                loadExpenseStructureData((Pie)chart, "2");
                break;
            case DiagramFragment.EXPENSES_BY_CATEGORY:
                chart = AnyChart.vertical();
                loadExpenseByCategoryData((Cartesian)chart);
                break;
            default:

                break;
        }
        if(chart != null)
            view.setChart(chart);
    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void setDiagramType(String param) {
        this.param = param;
    }

    @Override
    public void loadExpenseStructureData(Pie chart, String type) {
        Disposable disposable = repository.getExpensesStructureData(type)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(stringIntegerHashMap -> {chart.setData(processExpenseStructureData(stringIntegerHashMap));
                            chart.getLegend()
                                    .setPosition("center-bottom")
                                    .setItemsLayout(LegendLayout.HORIZONTAL)
                                    .setAlign(EnumsAlign.CENTER);;},
                        throwable -> Log.wtf("myLog", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    @Override
    public void loadExpenseByCategoryData(Cartesian chart) {
        Disposable disposable = repository.getExpensesStructureData("1")
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(stringIntegerHashMap ->
                        {
                            Set set = new Set(processExpenseStructureData(stringIntegerHashMap));
                            Mapping barData = set.mapAs("{ x: 'x', value: 'value' }");
                            SeriesBar bar = chart.bar(barData);
                            bar.getLabels().setFormat("{%Value}");
                            chart.setLabels(true);
                            chart.setTooltip(false);
                            chart.getYAxis().getLabels().setFormat("{%Value}");
                            chart.setYAxis(true);
                        },
                        throwable -> Log.wtf("myLog", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    private List<DataEntry> processExpenseStructureData(HashMap<String, Integer> map){
        List<DataEntry> data = new ArrayList<>();
        for(String key : map.keySet()){
            data.add(new ValueDataEntry(key, map.get(key)));
            Log.wtf("myLog", key);
            Log.wtf("myLog", map.get(key) + "");
        }
        return data;
    }
}
