package com.example.productmanagment.purchaseslist;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Purchase;
import com.example.productmanagment.data.models.PurchaseList;

import java.util.ArrayList;
import java.util.List;

public class PurchaseListFragment extends Fragment implements PurchaseListContract.View {
    PurchaseListContract.Presenter presenter;
    PurchaseListAdapter adapter;
    Spinner spinner;

    public PurchaseListFragment() {
        // Required empty public constructor
    }

    // TODO: Rename and change types and number of parameters
    public static PurchaseListFragment newInstance() {
        return new PurchaseListFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new PurchaseListAdapter(getContext(), new ArrayList<>(0));
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_purchase_list, container, false);
        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.purchase_list_menu, menu);
        MenuItem item = menu.findItem(R.id.purchaseListSpinner);
        spinner = (Spinner) item.getActionView();
        spinner.setAdapter(adapter);
    }

    @Override
    public void setPresenter(PurchaseListContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void showPurchasesList(List<PurchaseList> purchaseList) {
        adapter.setData(purchaseList);
    }

    @Override
    public void showPurchases(List<Purchase> purchases) {

    }

    private static class PurchaseListAdapter extends BaseAdapter{
        List<PurchaseList> purchaseLists;
        Context context;
        LayoutInflater inflater;

        PurchaseListAdapter(Context context, List<PurchaseList> purchaseList) {
            this.context = context;
            this.purchaseLists = purchaseList;
            inflater = (LayoutInflater.from(context));
        }

        @Override
        public int getCount() {
            return purchaseLists.size();
        }

        @Override
        public PurchaseList getItem(int i) {
            return purchaseLists.get(i);
        }

        @Override
        public long getItemId(int i) {
            return i;
        }

        @Override
        public View getView(int i, View view, ViewGroup viewGroup) {
            PurchaseList list = getItem(i);
            View root = inflater.inflate(android.R.layout.simple_spinner_item, null);
            TextView textView = root.findViewById(android.R.id.text1);
            textView.setText(list.getTitle());
            return null;
        }

        public void setData(List<PurchaseList> list){
            if(purchaseLists.size() != 0){
                purchaseLists.clear();
                purchaseLists = list;
                notifyDataSetChanged();
            }
        }
    }
}
