package com.interviewallocation.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;
import java.time.LocalTime;

@ApiModel
public class InterviewerDto {

    @ApiModelProperty(required = true)
    @Getter
    @Pattern(regexp = "^[a-zA-Z]+$", message = "incorrect name (should contain only alphabets )")
    private String name;

    @Getter @Setter
    private LocalTime breakStartTime;

    @Getter @Setter
    private LocalTime breakEndTime;

    public InterviewerDto() {
    }

}
