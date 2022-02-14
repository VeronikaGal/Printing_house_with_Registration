package com.galeeva.project.entity;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class MachineEntity {

    Integer id;
    MachineModel model;
    MachineType type;
}
