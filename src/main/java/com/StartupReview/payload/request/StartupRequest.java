package com.StartupReview.payload.request;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class StartupRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String launchDate;

    @NotBlank
    private String tags;



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

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }
}
