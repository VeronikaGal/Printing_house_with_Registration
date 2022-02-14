package com.galeeva.project.service;

import com.galeeva.project.dao.OrderDataDao;
import com.galeeva.project.dto.CreateOrderDto;
import com.galeeva.project.dto.OrderDataDto;
import com.galeeva.project.exeption.ValidationException;
import com.galeeva.project.mapper.CreateOrderMapper;
import com.galeeva.project.validator.CreateOrderValidator;

import java.util.List;
import java.util.stream.Collectors;

public class OrderDataService {

    private static final OrderDataService INSTANCE = new OrderDataService();

    private final CreateOrderValidator createOrderValidator = CreateOrderValidator.getInstance();
    private final CreateOrderMapper createOrderMapper = CreateOrderMapper.getInstance();
    private final OrderDataDao orderDataDao = OrderDataDao.getInstance();

    private OrderDataService() {
    }

    public List<OrderDataDto> findAllByServiceid(Long serviceId) {
        return orderDataDao.findAllByServiceId(serviceId).stream()
                .map(orderData -> new OrderDataDto(
                        orderData.getId(),
                        orderData.getServiceId(),
                        orderData.getFile(),
                        orderData.getStatus(),
                        orderData.getDelivery()
                ))
                .collect(Collectors.toList());
    }

    public Long create(CreateOrderDto orderDto) {
        var validationResult = createOrderValidator.isValid(orderDto);
        if (!validationResult.isValid()) {
            throw new ValidationException(validationResult.getErrors());
        }
        var orderEntity = createOrderMapper.mapFrom(orderDto);
        orderDataDao.save(orderEntity);
        return orderEntity.getId();
    }

    public static OrderDataService getInstance() {
        return INSTANCE;
    }
}
