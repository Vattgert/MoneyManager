package com.example.productmanagment.plannedpayment;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.PlannedPayment;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link PlannedPaymentFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class PlannedPaymentFragment extends Fragment implements PlannedPaymentContract.View {
    private PlannedPaymentContract.Presenter presenter;
    private PlannedPaymentAdapter adapter;

    public PlannedPaymentFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment PlannedPaymentFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static PlannedPaymentFragment newInstance() {
        PlannedPaymentFragment fragment = new PlannedPaymentFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PlannedPaymentAdapter(new ArrayList<PlannedPayment>(0), itemListener);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_planned_payment, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.plannedPaymentRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.unsubscribe();
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void setPresenter(PlannedPaymentContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showPlannedPayments(List<PlannedPayment> plannedPayments) {
        adapter.setData(plannedPayments);
        Log.wtf("resultTag", String.valueOf(adapter.getItemCount()));
    }

    @Override
    public void showAddPlannedPayment() {

    }

    @Override
    public void showLoadingPlannedPaymentsError() {

    }

    @Override
    public void showNoPlannedPayments() {

    }

    @Override
    public void showPlannedPaymentSuccessfullySavedMessage() {

    }

    PlannedPaymentItemListener itemListener = new PlannedPaymentItemListener() {
        @Override
        public void onExpenseClick(PlannedPayment clicked) {

        }
    };

    private static class PlannedPaymentAdapter extends RecyclerView.Adapter<PlannedPaymentFragment.PlannedPaymentAdapter.ViewHolder>{
        private List<PlannedPayment> plannedPaymentList;
        PlannedPaymentFragment.PlannedPaymentItemListener itemListener;

        public PlannedPaymentAdapter(List<PlannedPayment> plannedPaymentList, PlannedPaymentFragment.PlannedPaymentItemListener itemListener) {
            this.plannedPaymentList = plannedPaymentList;
            this.itemListener = itemListener;
        }

        @Override
        public PlannedPaymentFragment.PlannedPaymentAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_planned_payment, parent, false);
            return new PlannedPaymentFragment.PlannedPaymentAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(PlannedPaymentFragment.PlannedPaymentAdapter.ViewHolder holder, int position) {
            PlannedPayment plannedPayment = plannedPaymentList.get(position);
            holder.bind(plannedPayment);
        }

        @Override
        public int getItemCount() {
            return plannedPaymentList.size();
        }

        public void setData(List<PlannedPayment> plannedPaymentList){
            this.plannedPaymentList = plannedPaymentList;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            PlannedPayment plannedPayment;
            TextView plannedPaymentNameTextView, paymentInfoTextView, paymentCostTextView,
                    paymentFrequencyTextView, paymentDateTextView;

            public ViewHolder(View view) {
                super(view);
                plannedPaymentNameTextView = view.findViewById(R.id.plannedPaymentNameTextView);
                paymentInfoTextView = view.findViewById(R.id.paymentInfoTextView);
                paymentCostTextView = view.findViewById(R.id.paymentCostTextView);
                paymentFrequencyTextView = view.findViewById(R.id.paymentFrequencyTextView);
                paymentDateTextView = view.findViewById(R.id.paymentDateTextView);
                view.setOnClickListener(__ -> itemListener.onExpenseClick(plannedPayment));
            }

            public void bind(PlannedPayment plannedPayment){
                this.plannedPayment = plannedPayment;
                plannedPaymentNameTextView.setText(plannedPayment.getTitle());
                paymentInfoTextView.setText(String.format("%s, %s", plannedPayment.getCategory().getName(), "Наличные"));
                paymentCostTextView.setText(String.valueOf(plannedPayment.getCost()));
                paymentFrequencyTextView.setText(plannedPayment.getPaymentFrequency());
                paymentDateTextView.setText(plannedPayment.getStartDate());

            }
        }
    }

    public interface PlannedPaymentItemListener {

        void onExpenseClick(PlannedPayment clicked);

    }
}
