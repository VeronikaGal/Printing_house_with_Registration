package com.galeeva.project.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDataEntity {

    Long id;
    UserEntity usersId;
    ServiceEntity serviceId;
    String file;
    OrderPaperType paperType;
    Integer quantity;
    MachineEntity machineId;
    OrderStatus status;
    BigDecimal totalPrice;
    LocalDateTime createdAt;
    Optional<LocalDateTime> deliveredAt;
    OrderDelivery delivery;
}
