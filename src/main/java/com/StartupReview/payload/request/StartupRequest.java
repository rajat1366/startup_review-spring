package com.StartupReview.payload.request;

import javax.validation.constraints.NotBlank;

public class StartupRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    private String launchDate;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getLaunchDate() {
        return launchDate;
    }

    public void setLaunchDate(String launchDate) {
        this.launchDate = launchDate;
    }
}
