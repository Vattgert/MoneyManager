package com.example.productmanagment.adapters;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

import com.example.productmanagment.data.models.Account;
import com.example.productmanagment.data.models.Group;

import java.util.List;

public class SimpleGroupSpinnerAdapter extends ArrayAdapter<Group> {
    private LayoutInflater mInflater;
    private Context context;
    private List<Group> items;
    private int resource, itemResource;

    public SimpleGroupSpinnerAdapter(@NonNull Context context, int resource, @NonNull List<Group> objects, int itemResource) {
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

        Group group = getItem(position);
        TextView groupTextView = view.findViewById(android.R.id.text1);
        groupTextView.setText(group.getTitle());
        return view;
    }

    @Nullable
    @Override
    public Group getItem(int position) {
            return items.get(position);
    }

    public void setData(List<Group> groups) {
            this.items.clear();
            groups.add(0, new Group(-1, "Мій рахунок"));
            this.items.addAll(groups);
            notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return items.size();
    }
}
