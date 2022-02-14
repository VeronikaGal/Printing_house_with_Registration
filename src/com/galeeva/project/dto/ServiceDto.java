package com.galeeva.project.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class ServiceDto {

    Integer id;
    String description;
}
