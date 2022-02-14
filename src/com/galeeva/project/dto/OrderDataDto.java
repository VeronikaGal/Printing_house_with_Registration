package com.galeeva.project.dto;

import com.galeeva.project.entity.OrderDelivery;
import com.galeeva.project.entity.OrderStatus;
import com.galeeva.project.entity.ServiceEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Value;

@Value
@Builder
@AllArgsConstructor
public class OrderDataDto {

    Long id;
    ServiceEntity serviceId;
    String file;
    OrderStatus status;
    OrderDelivery delivery;
}
