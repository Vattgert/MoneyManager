package com.example.productmanagment.diagrams;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.MenuItemCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Spinner;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Chart;
import com.anychart.anychart.Resource;
import com.example.productmanagment.R;
import com.leavjenn.smoothdaterangepicker.date.SmoothDateRangePickerFragment;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DiagramFragment extends Fragment implements DiagramContract.View{
    Spinner diagramSpinner;
    ArrayAdapter<String> diagramSpinnerAdapter;


    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";


    // TODO: Rename and change types of parameters
    private DiagramContract.Presenter presenter;
    private String diagramParameter = "";
    private AnyChartView anyChartView;

    public DiagramFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment DiagramFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static DiagramFragment newInstance() {
        return new DiagramFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Діаграми");
        diagramSpinnerAdapter = new ArrayAdapter<String>(getContext(), android.R.layout.simple_spinner_item, getResources().getStringArray(R.array.diagram_types));
        diagramSpinnerAdapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_diagram, container, false);
        setHasOptionsMenu(true);
        anyChartView = view.findViewById(R.id.diagramChartView);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.diagram_menu, menu);
        MenuItem item = menu.findItem(R.id.diagram_spinner);
        diagramSpinner = (Spinner) MenuItemCompat.getActionView(item);
        diagramSpinner.setAdapter(diagramSpinnerAdapter);
        diagramSpinner.setOnItemSelectedListener(listener);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.action_date_range:
                Calendar calendar = Calendar.getInstance();
                SmoothDateRangePickerFragment.newInstance(callBack, calendar.get(Calendar.YEAR), calendar.get(Calendar.MONTH), calendar.get(Calendar.DAY_OF_MONTH))
                        .show(getActivity().getFragmentManager(), "");
                break;
            default:
                return super.onOptionsItemSelected(item);
        }
        return true;
    }

    AdapterView.OnItemSelectedListener listener = new AdapterView.OnItemSelectedListener() {
        @Override
        public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
            presenter.showDiagram(i);
        }

        @Override
        public void onNothingSelected(AdapterView<?> adapterView) {

        }
    };

    SmoothDateRangePickerFragment.OnDateRangeSetListener callBack = (view, yearStart, monthStart, dayStart, yearEnd, monthEnd, dayEnd) -> {
        String fdate = String.format("%s-%s-%s", yearStart, monthStart, dayStart);
        String sdate = String.format("%s-%s-%s", yearStart, monthStart, dayStart);
    };

    @Override
    public void setPresenter(DiagramContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setChart(Chart anyChart) {
        anyChartView.setChart(anyChart);
    }
}
