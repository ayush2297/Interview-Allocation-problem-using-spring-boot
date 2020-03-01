package com.interviewallocation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.Pattern;

@ApiModel
public class InterviewerDto {

    @ApiModelProperty(required = true)
    @Getter
    @Pattern(regexp = "^[a-zA-Z]+$", message = "incorrect name (should contain only alphabets - min length 3")
    private String name;

    @Getter
    @Pattern(regexp = "^[1][0-7]|[0]{0}$",message = "incorrect break end time")
    private String breakEndTime;

    @Getter
    @Pattern(regexp = "^09|[1][0-6]|[0]{2}$",message = "incorrect break start time")
    private String breakStartTime;

}
