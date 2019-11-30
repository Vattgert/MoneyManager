package com.example.productmanagment.aisystem.recommendations;

import android.content.Context;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.example.productmanagment.R;
import com.example.productmanagment.data.source.remote.remotemodels.Recommendation;

import java.util.ArrayList;
import java.util.List;


public class RecommendationsFragment extends Fragment implements RecommendationsContract.View {
    private OnFragmentInteractionListener mListener;
    private RecommendationsContract.Presenter presenter;
    RecommendationAdapter adapter;
    Button getRecommendationButton;

    public RecommendationsFragment() {
        // Required empty public constructor
    }


    public static RecommendationsFragment newInstance() {
        return new RecommendationsFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        adapter = new RecommendationAdapter(new ArrayList<>(0));
        getActivity().setTitle("Рекомендації");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recommendations, container, false);
        RecyclerView recyclerView = view.findViewById(R.id.recommendationsRecyclerView);
        getRecommendationButton = view.findViewById(R.id.getRecommendationsButton);
        getRecommendationButton.setOnClickListener(view1 -> {
            presenter.loadCurrentStateRecommendations();
        });
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
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
    public void onAttach(Context context) {
        super.onAttach(context);
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void showMessage(String message) {

    }

    @Override
    public void setRecommendations(List<Recommendation> recommendations) {
        adapter.setData(recommendations);
    }

    @Override
    public void setPresenter(RecommendationsContract.Presenter presenter) {
        this.presenter = presenter;
    }

    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }


    private static class RecommendationAdapter extends RecyclerView.Adapter<RecommendationAdapter.ViewHolder>{
        private List<Recommendation> recommendationList;

        public RecommendationAdapter(List<Recommendation> recommendationList) {
            this.recommendationList = recommendationList;
        }

        @Override
        public RecommendationAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_recommendations, parent, false);
            return new RecommendationAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(RecommendationAdapter.ViewHolder holder, int position) {
            Recommendation recommendation = recommendationList.get(position);
            holder.bind(recommendation);
        }

        @Override
        public int getItemCount() {
            return recommendationList.size();
        }

        public void setData(List<Recommendation> recommendationList){
            this.recommendationList = recommendationList;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            Recommendation recommendation;
            TextView goalTitleTextView;
            Resources resources;

            public ViewHolder(View view) {
                super(view);
                resources = view.getContext().getResources();
                goalTitleTextView = view.findViewById(R.id.recommendationTextView);
            }

            public void bind(Recommendation recommendation){
                this.recommendation = recommendation;
                goalTitleTextView.setText(recommendation.getAdvice());
            }
        }
    }
}
