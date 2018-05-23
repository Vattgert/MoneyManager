package com.example.productmanagment.currency;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.productmanagment.R;
import com.example.productmanagment.addcurrency.AddCurrencyActivity;
import com.example.productmanagment.currencydetailandedit.CurrencyDetailAndEditActivity;
import com.example.productmanagment.data.models.MyCurrency;

import java.util.ArrayList;
import java.util.Currency;
import java.util.List;


public class CurrencyFragment extends Fragment implements CurrencyContract.View{
    CurrencyContract.Presenter presenter;
    CurrenciesAdapter adapter;

    public CurrencyFragment() {
        // Required empty public constructor
    }
    public static CurrencyFragment newInstance() {
        CurrencyFragment fragment = new CurrencyFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Валюти");
        adapter = new CurrenciesAdapter(new ArrayList<>(0), listener);
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_currency, container, false);
        FloatingActionButton actionButton = view.findViewById(R.id.addCurrencyButton);
        actionButton.setOnClickListener(__ -> presenter.openAddCurrency());
        RecyclerView recyclerView = view.findViewById(R.id.currenciesRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }


    @Override
    public void showCurrencies(List<MyCurrency> currencies) {
        adapter.setData(currencies);
    }

    @Override
    public void showAddCurrency(int groupId) {
        Intent intent = new Intent(getContext(), AddCurrencyActivity.class);
        intent.putExtra("group_id", groupId);
        startActivity(intent);
    }

    @Override
    public void showDetailAndEditCurrency(int groupId, String currencyId) {
        Intent intent = new Intent(getContext(), CurrencyDetailAndEditActivity.class);
        intent.putExtra("groupId", groupId);
        intent.putExtra("currencyId", currencyId);
        startActivity(intent);
    }

    @Override
    public void setPresenter(CurrencyContract.Presenter presenter) {
        this.presenter = presenter;
    }

    CurrencyItemListener listener = new CurrencyItemListener() {
        @Override
        public void onCurrencyClick(MyCurrency clicked) {
            presenter.openDetailAndEditCurrency(String.valueOf(clicked.getId()));
        }
    };

    public static class CurrenciesAdapter extends RecyclerView.Adapter<CurrenciesAdapter.ViewHolder>{
        private List<MyCurrency> currencies;
        CurrencyItemListener itemListener;

        public CurrenciesAdapter(List<MyCurrency> currencies, CurrencyItemListener itemListener) {
            this.currencies = currencies;
            this.itemListener = itemListener;
        }

        @Override
        public CurrenciesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_currency, parent, false);
            return new CurrenciesAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CurrenciesAdapter.ViewHolder holder, int position) {
            MyCurrency currency = currencies.get(position);
            holder.bind(currency);
        }

        @Override
        public int getItemCount() {
            return currencies.size();
        }

        public void setData(List<MyCurrency> currencies){
            this.currencies = currencies;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            MyCurrency currency;
            TextView currencyCodeTextView, currencyNameTextView;

            public ViewHolder(View view) {
                super(view);
                currencyCodeTextView = view.findViewById(R.id.currencyCodeTextView);
                currencyNameTextView = view.findViewById(R.id.currencyNameTextView);

                if(itemListener != null)
                    view.setOnClickListener(__ -> itemListener.onCurrencyClick(currency));
            }

            public void bind(MyCurrency currency){
                this.currency = currency;
                currencyCodeTextView.setText(currency.getCode());
                currencyNameTextView.setText(currency.getTitle());
            }
        }
    }

    public interface CurrencyItemListener {

        void onCurrencyClick(MyCurrency clicked);

    }
}
