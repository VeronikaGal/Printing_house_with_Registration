package com.galeeva.project.dto;

import lombok.Builder;
import lombok.Value;

@Value
@Builder
public class CreateOrderDto {
    String file;
    String paperType;
    Integer quantity;
    String delivery;
}
