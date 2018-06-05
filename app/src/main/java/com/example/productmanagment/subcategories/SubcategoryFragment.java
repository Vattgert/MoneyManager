package com.example.productmanagment.subcategories;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Subcategory;
import com.example.productmanagment.utils.schedulers.interfaces.OnFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * to handle interaction events.
 * Use the {@link SubcategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class SubcategoryFragment extends Fragment implements SubcategoriesContract.View {
    SubcategoriesContract.Presenter presenter;
    SubcategoriesAdapter subcategoriesAdapter;
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_CATEGORY_ID = "categoryId";

    // TODO: Rename and change types of parameters
    private int categoryId;

    public SubcategoryFragment() {
        // Required empty public constructor
    }

    public static SubcategoryFragment newInstance(int categoryId) {
        SubcategoryFragment fragment = new SubcategoryFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_CATEGORY_ID, categoryId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Підкатегорії");
        if (getArguments() != null) {
            categoryId = getArguments().getInt(ARG_CATEGORY_ID);
        }
        subcategoriesAdapter = new SubcategoriesAdapter(new ArrayList<Subcategory>(0), subcategoriesItemListener);
    }

    @Override
    public void onResume() {
        super.onResume();
        presenter.subscribe();
    }

    @Override
    public void onPause() {
        super.onPause();
        presenter.subscribe();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_subcategory, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.subcategoryRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(subcategoriesAdapter);
        return view;
    }

    @Override
    public void onDetach() {
        super.onDetach();

    }

    SubcategoriesItemListener subcategoriesItemListener = clicked -> {
        Intent intent = new Intent();
        intent.putExtra("subcategoryId", clicked.getId());
        intent.putExtra("subcategoryTitle", clicked.getName());
        getActivity().setResult(Activity.RESULT_OK, intent);
        getActivity().finish();
    };

    @Override
    public void showSubcategories(List<Subcategory> subcategoryList) {
        subcategoriesAdapter.setData(subcategoryList);
    }

    @Override
    public void setPresenter(SubcategoriesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    private static class SubcategoriesAdapter extends RecyclerView.Adapter<SubcategoriesAdapter.ViewHolder>{
        private List<Subcategory> subcategoryList;
        SubcategoriesItemListener itemListener;

        public SubcategoriesAdapter(List<Subcategory> categoryList, SubcategoriesItemListener itemListener) {
            this.subcategoryList = categoryList;
            this.itemListener = itemListener;
        }

        @Override
        public SubcategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_subcategory, parent, false);
            return new SubcategoriesAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(SubcategoriesAdapter.ViewHolder holder, int position) {
            Subcategory subcategory = subcategoryList.get(position);
            holder.bind(subcategory);
        }

        @Override
        public int getItemCount() {
            return subcategoryList.size();
        }

        public void setData(List<Subcategory> categoryList){
            this.subcategoryList = categoryList;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            Subcategory subcategory;
            TextView nameTextView;

            public ViewHolder(View view) {
                super(view);
                nameTextView = view.findViewById(R.id.subcategoryNameTextView);
                view.setOnClickListener(__ -> itemListener.onSubcategoryClick(subcategory));
            }

            public void bind(Subcategory subcategory){
                this.subcategory = subcategory;
                nameTextView.setText(subcategory.getName());
            }
        }
    }

    public interface SubcategoriesItemListener {

        void onSubcategoryClick(Subcategory clicked);

    }

}
