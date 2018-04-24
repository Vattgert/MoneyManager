package com.example.productmanagment.diagrams;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.anychart.anychart.AnyChart;
import com.anychart.anychart.AnyChartView;
import com.anychart.anychart.Chart;
import com.example.productmanagment.R;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link DiagramFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link DiagramFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class DiagramFragment extends Fragment implements DiagramContract.View{
    public static final String EXPENSE_STRUCTURE_DIAGRAM = "expense_structure_diagram";
    public static final String INCOME_STRUCTURE_DIAGRAM = "income_structure_diagram";
    public static final String EXPENSES_BY_CATEGORY = "expenses_by_category";
    public static final String EXPENSES_BY_MARKS = "expenses_by_marks";



    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM = "param";


    // TODO: Rename and change types of parameters
    private DiagramContract.Presenter presenter;
    private String diagramParameter = "";
    private AnyChartView anyChartView;

    private OnFragmentInteractionListener mListener;

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
    public static DiagramFragment newInstance(String param) {
        DiagramFragment fragment = new DiagramFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM, param);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Діаграми");
        if (getArguments() != null) {
            diagramParameter = getArguments().getString(ARG_PARAM);
        }
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
        anyChartView = view.findViewById(R.id.diagramChartView);
        return view;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void setPresenter(DiagramContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setChart(Chart anyChart) {
        anyChartView.setChart(anyChart);
    }


    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }
}
