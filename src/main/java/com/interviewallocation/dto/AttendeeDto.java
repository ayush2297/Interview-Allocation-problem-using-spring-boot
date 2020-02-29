package com.interviewallocation.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

@ApiModel
public class AttendeeDto {

    @ApiModelProperty(required = true)
    @Getter
    @Setter
    private String name;

    @Getter @Setter
    private int noOfInterviews;

    public AttendeeDto() {
    }
}
