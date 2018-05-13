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
import com.example.productmanagment.data.models.diagram.ExpensesByCategory;
import com.example.productmanagment.data.models.diagram.ExpensesByUser;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.remote.responses.DiagramResponse;
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
    int groupId;
    private ExpensesRepository repository;
    private RemoteDataRepository remoteDataRepository;
    private DiagramContract.View view;
    private BaseSchedulerProvider provider;
    CompositeDisposable compositeDisposable;
    Chart chart = null;

    public DiagramPresenter(int groupId, ExpensesRepository repository,
                            DiagramContract.View view, BaseSchedulerProvider provider) {
        this.groupId = groupId;
        this.repository = repository;
        this.remoteDataRepository = new RemoteDataRepository();
        this.view = view;
        this.provider = provider;
        this.compositeDisposable = new CompositeDisposable();
        this.view.setPresenter(this);
    }

    @Override
    public void subscribe() {

    }

    @Override
    public void unsubscribe() {

    }

    @Override
    public void showDiagram(int type) {
        switch (type){
            case 0:
                chart = AnyChart.pie();
                if(groupId == -1)
                    loadExpenseStructureData((Pie)chart, "Витрата");
                else
                    loadExpenseStructureRemoteData((Pie)chart);
                break;
            case 1:
                break;
            case 2:
                chart = AnyChart.vertical();
                if(groupId == -1)
                    loadExpenseByCategoryData((Cartesian)chart);
                else
                    loadExpenseByCategoryRemoteData((Cartesian)chart);
                break;
            case 3:
                break;
            case 4:
                if(groupId != -1)
                    loadExpenseStructureByUserRemoteData((Pie)chart);
                break;
            default:
                break;
        }
        if(chart != null)
            view.setChart(chart);
    }

    public void loadExpenseStructureData(Pie chart, String type) {
        Disposable disposable = repository.getExpensesStructureData(type)
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(stringIntegerHashMap -> {
                    chart.setData(processExpenseStructureData(stringIntegerHashMap));
                            chart.getLegend()
                                    .setPosition("center-bottom")
                                    .setItemsLayout(LegendLayout.HORIZONTAL)
                                    .setAlign(EnumsAlign.CENTER);;},
                        throwable -> Log.wtf("myLog", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

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

    public void loadExpenseByCategoryRemoteData(Cartesian chart) {
        Disposable disposable = remoteDataRepository.getExpensesByCategoryDiagram(String.valueOf(groupId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(diagramResponse ->
                    {
                        Set set = new Set(processResponse(diagramResponse));
                        Mapping barData = set.mapAs("{ x: 'x', value: 'value' }");
                        SeriesBar bar = chart.bar(barData);
                        bar.getLabels().setFormat("{%Value}");
                        chart.setLabels(true);
                        chart.setTooltip(false);
                        chart.getYAxis().getLabels().setFormat("{%Value}");
                        chart.setYAxis(true);
                    });
        compositeDisposable.add(disposable);
    }

    public void loadExpenseStructureRemoteData(Pie chart) {
        Disposable disposable = remoteDataRepository.getExpensesByCategoryDiagram(String.valueOf(groupId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(diagramResponse -> {
                            chart.setData(processResponse(diagramResponse));
                            chart.getLegend()
                                    .setPosition("center-bottom")
                                    .setItemsLayout(LegendLayout.HORIZONTAL)
                                    .setAlign(EnumsAlign.CENTER);},
                        throwable -> Log.wtf("myLog", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    public void loadExpenseStructureByUserRemoteData(Pie chart) {
        Disposable disposable = remoteDataRepository.getExpensesByUserDiagram(String.valueOf(groupId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(diagramResponse -> {
                            chart.setData(processUserResponse(diagramResponse));
                            chart.getLegend()
                                    .setPosition("center-bottom")
                                    .setItemsLayout(LegendLayout.HORIZONTAL)
                                    .setAlign(EnumsAlign.CENTER);},
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

    private List<DataEntry> processResponse(DiagramResponse diagramResponse){
        List<DataEntry> data = new ArrayList<>();
        List<ExpensesByCategory> diagram = diagramResponse.expensesByCategoryList;
        for(ExpensesByCategory d : diagram){
            data.add(new ValueDataEntry(d.getCategory().getName(), d.getBalance()));
        }
        return data;
    }

    private List<DataEntry> processUserResponse(DiagramResponse diagramResponse){
        List<DataEntry> data = new ArrayList<>();
        List<ExpensesByUser> diagram = diagramResponse.expensesByUserList;
        for(ExpensesByUser d : diagram){
            data.add(new ValueDataEntry(d.getUser().getEmail(), d.getBalance()));
        }
        return data;
    }
}
