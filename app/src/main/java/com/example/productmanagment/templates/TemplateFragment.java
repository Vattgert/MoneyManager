package com.example.productmanagment.templates;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v7.widget.DividerItemDecoration;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.productmanagment.R;
import com.example.productmanagment.addtemplate.AddTemplateActivity;
import com.example.productmanagment.data.models.Template;
import com.example.productmanagment.templatedetailandedit.TemplateDetailAndEditActivity;

import java.util.ArrayList;
import java.util.List;

public class TemplateFragment extends Fragment implements TemplateContract.View{

    TemplateContract.Presenter presenter;
    TemplateAdapter adapter;

    public static TemplateFragment newInstance() {
        TemplateFragment fragment = new TemplateFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getActivity().setTitle("Шаблони");
        adapter = new TemplateAdapter(new ArrayList<>(0), templateItemListener);
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
        View view = inflater.inflate(R.layout.fragment_template, container, false);
        FloatingActionButton actionButton = view.findViewById(R.id.addTemplateButton);
        actionButton.setOnClickListener(__ -> presenter.addTemplate());
        RecyclerView recyclerView = view.findViewById(R.id.templatesRecyclerView);
        RecyclerView.LayoutManager mLayoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        recyclerView.addItemDecoration(new DividerItemDecoration(getContext(), LinearLayoutManager.VERTICAL));
        recyclerView.setLayoutManager(mLayoutManager);
        recyclerView.setAdapter(adapter);
        return view;
    }

    @Override
    public void showTemplates(List<Template> templateList) {
        for (Template t : templateList)
            Log.wtf("MyLog", t.getTitle());
        adapter.setData(templateList);
    }

    @Override
    public void showAddTemplate() {
        Intent intent = new Intent(getContext(), AddTemplateActivity.class);
        startActivity(intent);
    }

    @Override
    public void showTemplateDetail(String templateId) {
        Intent intent = new Intent(getContext(), TemplateDetailAndEditActivity.class);
        intent.putExtra("template_id", templateId);
        startActivity(intent);
    }

    @Override
    public void setPresenter(TemplateContract.Presenter presenter) {
        this.presenter = presenter;
    }

    TemplateItemListener templateItemListener = new TemplateItemListener() {
        @Override
        public void onTemplateClick(Template clicked) {
            presenter.openTemplateDetails(clicked);
        }
    };

    public static class TemplateAdapter extends RecyclerView.Adapter<TemplateAdapter.ViewHolder>{
        private List<Template> templates;
        TemplateItemListener itemListener;

        public TemplateAdapter(List<Template> templates, TemplateItemListener itemListener) {
            this.templates = templates;
            this.itemListener = itemListener;
        }

        @Override
        public TemplateAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_item_template, parent, false);
            return new TemplateAdapter.ViewHolder(view);
        }

        @Override
        public void onBindViewHolder(TemplateAdapter.ViewHolder holder, int position) {
            Template template = templates.get(position);
            holder.bind(template);
        }

        @Override
        public int getItemCount() {
            return templates.size();
        }

        public void setData(List<Template> templates){
            this.templates = templates;
            notifyDataSetChanged();
        }

        public class ViewHolder extends RecyclerView.ViewHolder{
            Template template;
            TextView templateTitleTextView;

            public ViewHolder(View view) {
                super(view);
                templateTitleTextView = view.findViewById(R.id.templateTitleTextView);

                if(itemListener != null)
                    view.setOnClickListener(__ -> itemListener.onTemplateClick(template));
            }

            public void bind(Template template){
                this.template = template;
                templateTitleTextView.setText(template.getTitle());
            }
        }
    }

    public interface TemplateItemListener {
        void onTemplateClick(Template clicked);
    }
}
