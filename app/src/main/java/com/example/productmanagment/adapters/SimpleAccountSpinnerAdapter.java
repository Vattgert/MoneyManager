package com.example.productmanagment.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Account;

import java.util.List;

public class SimpleAccountSpinnerAdapter extends ArrayAdapter<Account> {
    private LayoutInflater mInflater;
    private Context context;
    private List<Account> items;
    private int resource, itemResource;

    public SimpleAccountSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Account> objects, int itemResource) {
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

        Account account = getItem(position);
        TextView accountTextView = view.findViewById(android.R.id.text1);
        accountTextView.setText(account.getName());
        return view;
    }

    @Nullable
    @Override
    public Account getItem(int position) {
        return items.get(position);
    }

    public void setData(List<Account> accounts) {
        this.items.clear();
        this.items.addAll(accounts);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }

    public int getAccountPositionById(int accountId){
        for (Account account : items){
            if(accountId == account.getId())
                return items.indexOf(account);
        }
        return - 1;
    }
}
