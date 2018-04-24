package com.example.productmanagment.adapters;

import android.content.Context;
import android.graphics.Color;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import java.util.Currency;
import java.util.List;

public class CurrencySpinnerAdapter extends ArrayAdapter<Currency> {
    private LayoutInflater mInflater;
    private Context context;
    private List<Currency> items;
    private int resource, itemResource;

    public CurrencySpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Currency> objects, int itemResource) {
        super(context, resource, objects);
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.items = objects;
        this.resource = resource;
        this.itemResource = itemResource;
    }

    @Override
    public View getDropDownView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(itemResource, position, convertView, parent);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
        return createItemView(resource, position, convertView, parent);
    }

    private View createItemView(int resource, int position, View convertView, ViewGroup parent) {
        final View view = mInflater.inflate(resource, parent, false);

        Currency currency = getItem(position);
        TextView currencyTitleTextView = view.findViewById(android.R.id.text1);
        currencyTitleTextView.setText(currency.getDisplayName());
        return view;
    }

    @Nullable
    @Override
    public Currency getItem(int position) {
        return items.get(position);
    }

    public void setData(List<Currency> currencies) {
        this.items.clear();
        this.items.addAll(currencies);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }
}