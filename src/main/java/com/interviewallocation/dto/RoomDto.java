package com.interviewallocation.dto;

import io.swagger.annotations.ApiModel;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.Pattern;

@ApiModel
public class RoomDto {

    @Getter
    @Setter
    @Pattern(regexp = "^[a-zA-Z]+$")
    private String name;
}
