package com.example.productmanagment.data.source.remote.responses;

import com.example.productmanagment.data.models.report.ReportBalance;
import com.example.productmanagment.data.models.report.SubcategoryReport;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ReportResponse {
    @SerializedName("report")
    public List<SubcategoryReport> reportBalances;
}
