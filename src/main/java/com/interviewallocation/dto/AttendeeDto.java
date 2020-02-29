package com.interviewallocation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@ApiModel
public class AttendeeDto {

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    @Pattern(regexp = "^[a-zA-Z]{3}$", message = "name should contain atleast 3 letters ")
    private String name;

    @Getter @Setter
    @NotNull
    private int noOfInterviews;

    public AttendeeDto() {
    }
}
