package com.interviewallocation.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@ApiModel
public class InterviewerDto {

    @Getter
    @Setter
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String name;

}
