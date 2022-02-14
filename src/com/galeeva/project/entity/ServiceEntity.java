package com.galeeva.project.entity;


import lombok.AllArgsConstructor;
import lombok.Data;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
public class ServiceEntity {

    Integer id;
    ServiceName name;
    String description;
    BigDecimal price;
}
