package com.galeeva.project.service;

import com.galeeva.project.dao.ServiceDao;
import com.galeeva.project.dto.ServiceDto;

import java.util.List;
import java.util.stream.Collectors;

public class ServiceService {

    private static final ServiceService INSTANCE = new ServiceService();

    private final ServiceDao serviceDao = ServiceDao.getInstance();

    private ServiceService() {
    }

    public List<ServiceDto> findAll() {
        return serviceDao.findAll().stream()
                .map(service -> ServiceDto.builder()
                        .id(service.getId())
                        .description(
                                """
                                        %s - %s - %f
                                        """.formatted(service.getName(), service.getDescription(),
                                        service.getPrice()))
                        .build())
                .collect(Collectors.toList());
    }

    public static ServiceService getInstance() {
        return INSTANCE;
    }
}

