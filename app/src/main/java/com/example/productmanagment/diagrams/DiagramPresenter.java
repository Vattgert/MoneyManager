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
import com.anychart.anychart.chart.common.Event;
import com.anychart.anychart.chart.common.ListenersInterface;
import com.example.productmanagment.data.models.diagram.ExpensesByCategory;
import com.example.productmanagment.data.models.diagram.ExpensesByUser;
import com.example.productmanagment.data.source.expenses.ExpensesRepository;
import com.example.productmanagment.data.source.remote.RemoteDataRepository;
import com.example.productmanagment.data.source.remote.remotemodels.ExpenseStructureRemote;
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
        Chart chart = null;
        switch (type){
            case 0:
                chart = AnyChart.pie();
                if(groupId == -1)
                    loadExpenseStructureData((Pie)chart, "Витрата");
                else
                    loadExpenseStructureRemoteData((Pie)chart);
                break;
            case 1:
                chart = AnyChart.pie();
                if(groupId == -1)
                    loadExpenseStructureData((Pie)chart, "Дохід");
                else
                    loadIncomesStructureRemoteData((Pie)chart);
                break;
            case 2:
                chart = AnyChart.vertical();
                if(groupId == -1)
                    loadExpenseByCategoryData((Cartesian)chart);
               // else
                    //loadExpenseByCategoryRemoteData((Cartesian)chart);
                break;
            case 3:
                chart = AnyChart.pie();
                if(groupId != -1)
                    loadExpenseStructureByUserRemoteData((Pie)chart);
                break;
            case 4:
                chart = AnyChart.pie();
                if(groupId != -1)
                    loadUserIncomesStructure((Pie)chart);
                break;
            default:
                break;
        }
        if(chart != null)
            view.setChart(chart);
    }

    @Override
    public void showDiagramByDate(int type, String fdate, String sdate) {
        Chart chart = null;
        switch (type){
            case 0:
                chart = AnyChart.pie();
                if(groupId == -1)
                    loadExpenseStructureData((Pie)chart, "Витрата");
                else
                    loadExpenseStructureRemoteData((Pie)chart);
                break;
            case 1:
                chart = AnyChart.pie();
                if(groupId == -1)
                    loadExpenseStructureData((Pie)chart, "Дохід");
                else
                    loadIncomesStructureRemoteData((Pie)chart);
                break;
            case 2:
                chart = AnyChart.vertical();
                if(groupId == -1)
                    loadExpenseByCategoryData((Cartesian)chart);
                else
                    loadExpenseByCategoryRemoteData((Cartesian)chart);
                break;
            case 3:
                chart = AnyChart.pie();
                if(groupId != -1)
                    loadExpenseStructureByUserRemoteData((Pie)chart);
                break;
            case 4:
                chart = AnyChart.pie();
                if(groupId != -1)
                    loadUserIncomesStructure((Pie)chart);
                break;
            default:
                break;
        }
        if(chart != null)
            view.setChart(chart);
    }

    public void loadUserIncomesStructure(Pie chart) {
        Disposable disposable = remoteDataRepository.getUserIncomes(String.valueOf(groupId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(diagramResponse -> {
                            chart.setData(processExpenseUserDataRemote(diagramResponse.getIncomesByUser()));
                            chart.getLegend()
                                    .setPosition("center-bottom")
                                    .setItemsLayout(LegendLayout.HORIZONTAL)
                                    .setAlign(EnumsAlign.CENTER);},
                        throwable -> Log.wtf("myLog", throwable.getMessage()));
        compositeDisposable.add(disposable);
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
                                    .setAlign(EnumsAlign.CENTER);},
                        throwable -> Log.wtf("myLog", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    public void loadExpenseStructureData(Pie chart, String type, String fdate, String sdate) {
        Disposable disposable = repository.getExpensesStructureDataByDate(type, fdate, sdate)
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
        Disposable disposable = repository.getExpensesStructureData("Витрата")
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(stringIntegerHashMap ->
                        {
                            Set set = new Set(processExpenseStructureData(stringIntegerHashMap));
                            Mapping barData = set.mapAs("{ x: 'x', value: 'value' }");
                            SeriesBar bar = chart.bar(barData);
                            bar.getLabels().setFormat("{%Value}₴");
                            chart.setLabels(true);
                            chart.setTooltip(false);
                            chart.getYAxis().getLabels().setFormat("{%Value}₴");
                            chart.setYAxis(true);
                            chart.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
                                @Override
                                public void onClick(Event event) {
                                    view.showCategoryExpenses(groupId, event.getData().get("x"));
                                }
                            });
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
                        bar.getLabels().setFormat("{%Value}₴");
                        chart.setLabels(true);
                        chart.setTooltip(false);
                        chart.getYAxis().getLabels().setFormat("{%Value}₴");
                        chart.setYAxis(true);
                        chart.setOnClickListener(new ListenersInterface.OnClickListener(new String[]{"x", "value"}) {
                            @Override
                            public void onClick(Event event) {
                                view.showCategoryExpenses(groupId, event.getData().get("x"));
                            }
                        });
                    });
        compositeDisposable.add(disposable);
    }

    public void loadExpenseStructureRemoteData(Pie chart) {
        Disposable disposable = remoteDataRepository.getExpensesStructure(String.valueOf(groupId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(diagramResponse -> {
                            chart.setData(processExpenseStructureDataRemote(diagramResponse.getExpensesStructure()));
                            chart.getLegend()
                                    .setPosition("center-bottom")
                                    .setItemsLayout(LegendLayout.HORIZONTAL)
                                    .setAlign(EnumsAlign.CENTER);}/*,
                        throwable -> Log.wtf("myLog", throwable.getMessage())*/);
        compositeDisposable.add(disposable);
    }

    public void loadIncomesStructureRemoteData(Pie chart) {
        Disposable disposable = remoteDataRepository.getIncomesStructure(String.valueOf(groupId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(diagramResponse -> {
                            chart.setData(processExpenseStructureDataRemote(diagramResponse.getIncomesStructure()));
                            chart.getLegend()
                                    .setPosition("center-bottom")
                                    .setItemsLayout(LegendLayout.HORIZONTAL)
                                    .setAlign(EnumsAlign.CENTER);},
                        throwable -> Log.wtf("myLog", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    public void loadExpenseStructureByUserRemoteData(Pie chart) {
        Disposable disposable = remoteDataRepository.getUserExpenses(String.valueOf(groupId))
                .subscribeOn(provider.io())
                .observeOn(provider.ui())
                .subscribe(diagramResponse -> {
                            chart.setData(processExpenseUserDataRemote(diagramResponse.getExpensesByUsers()));
                            chart.getLegend()
                                    .setPosition("center-bottom")
                                    .setItemsLayout(LegendLayout.HORIZONTAL)
                                    .setAlign(EnumsAlign.CENTER);},
                        throwable -> Log.wtf("myLog", throwable.getMessage()));
        compositeDisposable.add(disposable);
    }

    private List<DataEntry> processExpenseStructureData(List<ExpensesByCategory> list){
        List<DataEntry> data = new ArrayList<>();
        for(ExpensesByCategory key : list){
            data.add(new ValueDataEntry(key.getCategory().getName(), key.getBalance()));
        }
        return data;
    }

    private List<DataEntry> processExpenseStructureDataRemote(List<ExpenseStructureRemote.ExpenseStructure> list){
        List<DataEntry> data = new ArrayList<>();
        for(ExpenseStructureRemote.ExpenseStructure key : list){
            data.add(new ValueDataEntry(key.getCategory(), key.getBalance()));
        }
        return data;
    }

    private List<DataEntry> processExpenseUserDataRemote(List<ExpenseStructureRemote.ExpenseUser> list){
        List<DataEntry> data = new ArrayList<>();
        for(ExpenseStructureRemote.ExpenseUser key : list){
            data.add(new ValueDataEntry(key.getUser(), key.getBalance()));
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

    private List<DataEntry> processResponseIncomes(DiagramResponse diagramResponse){
        List<DataEntry> data = new ArrayList<>();
        List<ExpensesByCategory> diagram = diagramResponse.incomesByCategoryList;
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
