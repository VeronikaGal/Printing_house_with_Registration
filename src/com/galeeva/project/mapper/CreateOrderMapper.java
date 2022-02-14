package com.galeeva.project.mapper;

import com.galeeva.project.dto.CreateOrderDto;
import com.galeeva.project.entity.OrderDataEntity;
import com.galeeva.project.entity.OrderDelivery;
import com.galeeva.project.entity.OrderPaperType;

public class CreateOrderMapper implements Mapper<CreateOrderDto, OrderDataEntity> {

    public static final CreateOrderMapper INSTANCE = new CreateOrderMapper();

    @Override
    public OrderDataEntity mapFrom(CreateOrderDto object) {
        return OrderDataEntity.builder()
                .file(object.getFile())
                .paperType(OrderPaperType.valueOf(object.getPaperType()))
                .quantity(object.getQuantity())
                .delivery(OrderDelivery.valueOf(object.getDelivery()))
                .build();
    }

    public static CreateOrderMapper getInstance() {
        return INSTANCE;
    }
}
