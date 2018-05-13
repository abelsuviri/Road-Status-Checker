package com.abelsuviri.data.model;

import com.google.gson.annotations.SerializedName;

/**
 * @author Abel Suviri
 */

public class Road {
    @SerializedName("displayName")
    public String name;

    @SerializedName("statusSeverity")
    public String status;

    @SerializedName("statusSeverityDescription")
    public String statusDescription;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getStatusDescription() {
        return statusDescription;
    }

    public void setStatusDescription(String statusDescription) {
        this.statusDescription = statusDescription;
    }
}
