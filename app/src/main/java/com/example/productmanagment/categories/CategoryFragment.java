package com.example.productmanagment.categories;


import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.productmanagment.R;
import com.example.productmanagment.data.models.Category;
import com.example.productmanagment.utils.schedulers.interfaces.OnFragmentInteractionListener;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link CategoryFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class CategoryFragment extends Fragment implements CategoriesContract.View {
    protected CategoriesContract.Presenter presenter;
    private CategoriesAdapter categoriesAdapter;

    private OnFragmentInteractionListener mListener;

    public CategoryFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment CategoryFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static CategoryFragment newInstance() {
        CategoryFragment fragment = new CategoryFragment();
        return fragment;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Категорії");
        categoriesAdapter = new CategoriesAdapter(new ArrayList<Category>(0), itemListener);
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
        View view = inflater.inflate(R.layout.fragment_category, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.categoryRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(categoriesAdapter);
        //Log.wtf("Expense", categoriesAdapter.getItemCount() + "");
        return view;
    }

    @Override
    public void setPresenter(CategoriesContract.Presenter presenter) {
        this.presenter = presenter;
    }

    @Override
    public void setLoadingIndicator(boolean active) {

    }

    @Override
    public void showCategories(List<Category> categoryList) {
        categoriesAdapter.setData(categoryList);
    }

    @Override
    public void showLoadingCategoriesError() {

    }

    @Override
    public void showNoCategories() {

    }

    @Override
    public void showSubcategories(int categoryId) {
        mListener.onFragmentInteraction(categoryId);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    CategoriesItemListener itemListener = new CategoriesItemListener() {
        @Override
        public void onCategoryClick(Category clicked) {
            Toast.makeText(getContext(), clicked.getName(), Toast.LENGTH_LONG).show();
            presenter.openSubcategories(clicked.getId());
        }
    };

    private static class CategoriesAdapter extends RecyclerView.Adapter<CategoriesAdapter.ViewHolder>{
        private List<Category> categoryList;
        CategoriesItemListener itemListener;

        public CategoriesAdapter(List<Category> categoryList, CategoriesItemListener itemListener) {
            this.categoryList = categoryList;
            this.itemListener = itemListener;
        }

        @Override
        public CategoriesAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_category, parent, false);
            return new CategoriesAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(CategoriesAdapter.ViewHolder holder, int position) {
            Category category = categoryList.get(position);
            holder.bind(category);
        }

        @Override
        public int getItemCount() {
            return categoryList.size();
        }

        public void setData(List<Category> categoryList){
            this.categoryList = categoryList;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            Category category;
            TextView nameTextView;
            ImageView categoryIconImageView;
            Resources resources;
            String mPackage;
            public ViewHolder(View view) {
                super(view);
                resources = view.getResources();
                mPackage = view.getContext().getPackageName();
                nameTextView = view.findViewById(R.id.categoryNameTextView);
                categoryIconImageView = view.findViewById(R.id.categoryIconImageView);
                view.setOnClickListener(__ -> itemListener.onCategoryClick(category));
            }

            public void bind(Category category){
                this.category = category;
                nameTextView.setText(category.getName());
                /*if(category.getIcon() != null && !category.getIcon().equals(""))
                    categoryIconImageView.setImageBitmap(BitmapFactory.decodeStream(resources.openRawResource(resources.getIdentifier(category.getIcon(), "raw", mPackage))));*/
            }
        }
    }

    public interface CategoriesItemListener {

        void onCategoryClick(Category clicked);

    }
}
