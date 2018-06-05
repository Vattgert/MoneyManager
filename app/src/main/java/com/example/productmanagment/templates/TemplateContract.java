package com.example.productmanagment.templates;

import android.support.annotation.NonNull;

import com.example.productmanagment.BasePresenter;
import com.example.productmanagment.BaseView;
import com.example.productmanagment.data.models.Template;

import java.util.List;

public interface TemplateContract {
    interface View extends BaseView<Presenter> {
        void showTemplates(List<Template> templateList);
        void showAddTemplate();
        void showTemplateDetail(String templateId);
    }

    interface Presenter extends BasePresenter {
        void loadTemplates();
        void openTemplateDetails(@NonNull Template requestedTask);
        void addTemplate();
        void result(int requestCode, int resultCode);
    }
}
