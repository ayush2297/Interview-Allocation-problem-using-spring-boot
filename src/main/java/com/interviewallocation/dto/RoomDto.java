package com.interviewallocation.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import javax.validation.constraints.Pattern;

@ApiModel
public class RoomDto {

    @ApiModelProperty(required = true)
    @Getter
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String name;

}
