package com.project.projectnew.ordercreation;

import com.google.gson.annotations.SerializedName;

public class InfoItem {
    @SerializedName("title")
    private String title;

    @SerializedName("description")
    private String description;

    @SerializedName("time")
    private String time;

    public InfoItem(String title, String description, String time) {
        this.title = title;
        this.description = description;
        this.time = time;
    }

    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getTime() { return time; }
}
