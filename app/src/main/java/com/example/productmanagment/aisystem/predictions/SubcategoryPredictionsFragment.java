package com.example.productmanagment.aisystem.predictions;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;

import android.support.v4.content.ContextCompat;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.example.productmanagment.R;
import com.example.productmanagment.data.source.remote.remotemodels.ExpensePrediction;
import com.example.productmanagment.data.source.remote.remotemodels.Subcategory;

import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link SubcategoryPredictionsFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link SubcategoryPredictionsFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubcategoryPredictionsFragment extends Fragment implements SubcategoryPredictionsContract.View {

    SubcategoryPredictionsContract.Presenter presenter;
    Spinner spinner;
    CustomAdapter adapter;
    FloatingActionButton getForecastFab;
    EditText periodEditText;

    private OnFragmentInteractionListener mListener;

    public SubcategoryPredictionsFragment() {
        // Required empty public constructor
    }

    public static SubcategoryPredictionsFragment newInstance() {
        SubcategoryPredictionsFragment fragment = new SubcategoryPredictionsFragment();
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Прогнозування витрат");
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
        View view = inflater.inflate(R.layout.fragment_subcategory_predictions, container, false);
        spinner = view.findViewById(R.id.categoriesSpinner);
        periodEditText = view.findViewById(R.id.periodEditText);
        getForecastFab = view.findViewById(R.id.getForecastFab);
        getForecastFab.setOnClickListener(view1 -> {
            Subcategory subcategory = null; int period = 0;
            if(spinner != null && spinner.getSelectedItem() !=null && adapter != null) {
                subcategory = (Subcategory)spinner.getSelectedItem();
                period = Integer.valueOf(periodEditText.getText().toString());
                if(subcategory != null && period != 0){
                    presenter.getPredictions("", subcategory.getSubcategoryId(), String.valueOf(period));
                }
            }
        });

        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void showPredictionResults(String predictionResults) {

    }

    @Override
    public void setCategories(List<Subcategory> subcategoryList) {
        adapter = new CustomAdapter(this.getContext(), subcategoryList);
        spinner.setAdapter(adapter);
    }

    @Override
    public void setPredictionsResults(List<ExpensePrediction> predictionsResults) {
        setTable(predictionsResults);
    }

    @Override
    public void setPresenter(SubcategoryPredictionsContract.Presenter presenter) {
        this.presenter = presenter;
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    public void setTable(List<ExpensePrediction> predictions){
        CardView cardView = this.getView().findViewById(R.id.forecastCardView);
        TableLayout forecast = (TableLayout)this.getView().findViewById(R.id.forecastTable);
        TableRow header =  new TableRow(this.getContext());
        TextView h1 =  new TextView(this.getContext());
        h1.setText("Дата");
        TextView h2 =  new TextView(this.getContext());
        h2.setText("Прогноз витрат");
        forecast.setStretchAllColumns(true);
        forecast.bringToFront();
        header.addView(h1);
        header.addView(h2);
        forecast.addView(header);
        for(ExpensePrediction ex : predictions){
            TableRow tr =  new TableRow(this.getContext());
            TextView c1 = new TextView(this.getContext());
            c1.setText(ex.getDate());
            TextView c2 = new TextView(this.getContext());
            c2.setText(ex.getExpense());
            tr.addView(c1);
            tr.addView(c2);
            forecast.addView(tr);
        }
        cardView.setVisibility(View.VISIBLE);
    }


    public class CustomAdapter extends BaseAdapter {
        Context context;
        List<Subcategory> subcategoryArrayList;
        LayoutInflater inflter;

        public CustomAdapter(Context applicationContext, List<Subcategory> subcategories) {
            this.context = applicationContext;
            this.subcategoryArrayList = subcategories;
            inflter = (LayoutInflater.from(applicationContext));
        }

        @Override
        public int getCount() {
            return subcategoryArrayList.size();
        }

        @Override
        public Subcategory getItem(int i) {
            return subcategoryArrayList.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            view = inflter.inflate(android.R.layout.simple_spinner_item, null);
            TextView names = view.findViewById(android.R.id.text1);
            names.setText(subcategoryArrayList.get(i).getSubcategoryTitle());
            return view;
        }
    }
}
