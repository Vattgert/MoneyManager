package com.example.productmanagment.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.productmanagment.data.models.MyCurrency;

import java.util.List;

public class MyCurrencySpinnerAdapter extends ArrayAdapter<MyCurrency> {
    private LayoutInflater mInflater;
    private Context context;
    private List<MyCurrency> items;
    private int resource, itemResource;

    public MyCurrencySpinnerAdapter(@NonNull Context context, int resource, @NonNull List<MyCurrency> objects, int itemResource) {
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

        MyCurrency currency = getItem(position);
        TextView currencyTitleTextView = view.findViewById(android.R.id.text1);
        currencyTitleTextView.setText(currency.getTitle());
        return view;
    }

    @Nullable
    @Override
    public MyCurrency getItem(int position) {
        return items.get(position);
    }

    public void setData(List<MyCurrency> currencies) {
        this.items.clear();
        this.items.addAll(currencies);
        notifyDataSetChanged();
    }

    public int getPositionByCurrency(MyCurrency currency){
        for(MyCurrency c : items){
            if(currency.getId() == c.getId())
                return items.indexOf(c);
        }
        return 0;
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
