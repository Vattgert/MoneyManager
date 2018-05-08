package com.example.productmanagment.data.source.remote.responses;

import com.google.gson.annotations.SerializedName;

public class SuccessResponse {
    @SerializedName("success_response")
    public String response;
    @SerializedName("data")
    public String data;
    @SerializedName("error_data")
    public String errorData;
    @SerializedName("additionalData")
    public String additionalData;
}
