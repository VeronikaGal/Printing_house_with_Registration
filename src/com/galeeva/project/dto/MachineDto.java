package com.galeeva.project.dto;

import lombok.Builder;
import lombok.Data;
import lombok.Value;

@Data
@Value
@Builder
public class MachineDto {

    Integer id;
    String description;
}
