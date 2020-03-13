package com.interviewallocation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel
public class AttendeeDto {

    @ApiModelProperty(required = true)
    @Getter
    @Pattern(regexp = "^[a-zA-Z]{3,}$", message = "name should contain atleast 3 letters ")
    private String name;

    @Getter
    @NotNull
    private int noOfInterviews;

    public AttendeeDto() {
    }

    public AttendeeDto(String name, int noOfInterviews) {
        this.name = name;
        this.noOfInterviews = noOfInterviews;
    }
}
