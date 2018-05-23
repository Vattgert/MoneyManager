package com.example.productmanagment.report.incomesandexpenses;


import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseExpandableListAdapter;
import android.widget.ExpandableListView;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.data.models.report.CategoryReport;
import com.example.productmanagment.data.models.report.SubcategoryReport;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class IncomesAndExpensesFragment extends Fragment implements IncomesAndExpensesContract.View {
    IncomesAndExpensesContract.Presenter presenter;
    ExpandableListAdapter adapter;
    ExpandableListView expandableListView;
    public IncomesAndExpensesFragment() {
        // Required empty public constructor
    }

    public static IncomesAndExpensesFragment newInstance() {
        return new IncomesAndExpensesFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Доходи та розходи");
        adapter = new ExpandableListAdapter(getContext(), new ArrayList<>(0),
                new HashMap<>(0));
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
        View view = inflater.inflate(R.layout.fragment_incomes_and_expenses, container, false);
        expandableListView = view.findViewById(R.id.incomesAndExpensesExpandableListView);
        expandableListView.setAdapter(adapter);
        return view;
    }

    @Override
    public void setHeadersListData(List<CategoryReport> categoryList) {
        adapter.setGroupData(categoryList);
    }

    @Override
    public void setSubItemsListData(CategoryReport category, List<SubcategoryReport> subcategories) {
        adapter.setChildData(category, subcategories);
    }

    @Override
    public List<CategoryReport> getHeaders() {
        return adapter.getHeaders();
    }


    @Override
    public void setPresenter(IncomesAndExpensesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public static class ExpandableListAdapter extends BaseExpandableListAdapter{
        private Context context;
        private List<CategoryReport> headers;
        private HashMap<CategoryReport, List<SubcategoryReport>> subItems;

        public ExpandableListAdapter(Context context, List<CategoryReport> headers, HashMap<CategoryReport, List<SubcategoryReport>> subItems) {
            this.context = context;
            this.headers = headers;
            this.subItems = subItems;
        }

        @Override
        public int getGroupCount() {
            return headers.size();
        }

        @Override
        public int getChildrenCount(int i) {
            Log.wtf("ReportLog", "click " + headers.get(i).getCategory().getName() + " " + headers.get(i).hashCode());
            return subItems.get(headers.get(i)).size();
        }

        @Override
        public CategoryReport getGroup(int i) {
            return headers.get(i);
        }

        @Override
        public SubcategoryReport getChild(int i, int i1) {
            return subItems.get(headers.get(i)).get(i1);
        }

        @Override
        public long getGroupId(int i) {
            return i;
        }

        @Override
        public long getChildId(int i, int i1) {
            return i1;
        }

        @Override
        public boolean hasStableIds() {
            return false;
        }

        @Override
        public View getGroupView(int i, boolean b, View view, ViewGroup viewGroup) {
            if(view == null){
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_item_report_group_expandable, null);
            }
            CategoryReport report = getGroup(i);
            ImageView categoryIcon = view.findViewById(R.id.reportCategoryIconImageView);
            TextView categoryTitleTextView = view.findViewById(R.id.reportCategoryTitleTextView);
            //TextView categorySumTextView = view.findViewById(R.id.categorySumTextView);
            categoryTitleTextView.setText(getGroup(i).getCategory().getName());
            /*if(getGroup(i).getCategory().getId() != 10) {
                categorySumTextView.setTextColor(Color.RED);
                categorySumTextView.setText(view.getResources().getString(R.string.report_item_expense_amount, String.valueOf(getGroup(i).getAmount())));
            }
            else {
                categorySumTextView.setTextColor(Color.GREEN);
                categorySumTextView.setText(view.getResources().getString(R.string.report_item_expense_amount, String.valueOf(getGroup(i).getAmount())));
                categorySumTextView.setText(view.getResources().getString(R.string.report_item_income_amount, String.valueOf(getGroup(i).getAmount())));
            }*/
            return view;
        }

        @Override
        public View getChildView(int i, int i1, boolean b, View view, ViewGroup viewGroup) {
            if(view == null){
                LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
                view = inflater.inflate(R.layout.list_item_report_child_expandable, null);
            }
            TextView subcategoryTitleTextView = view.findViewById(R.id.reportSubategoryTitleTextView);
            TextView subcategorySumTextView = view.findViewById(R.id.subcategorySumTextView);
            subcategoryTitleTextView.setText(getChild(i, i1).getSubcategory().getName());
            subcategorySumTextView.setText(String.valueOf(getChild(i, i1).getBalance()));
            return view;
        }

        @Override
        public boolean isChildSelectable(int i, int i1) {
            return false;
        }

        public void setGroupData(List<CategoryReport> categories){
            headers.clear();
            headers.addAll(categories);
            notifyDataSetChanged();
            for (CategoryReport c : headers){
                Log.wtf("ReportLog", "header " + c.hashCode() + "");
            }
        }

        public void setChildData(CategoryReport category, List<SubcategoryReport> subcategories){
            subItems.put(category, subcategories);
            notifyDataSetChanged();
        }

        public List<CategoryReport> getHeaders(){
            return headers;
        }

        public void setChildSum(double childSum){

        }
    }
}
