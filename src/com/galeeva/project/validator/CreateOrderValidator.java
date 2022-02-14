package com.galeeva.project.validator;

import com.galeeva.project.dto.CreateOrderDto;

public class CreateOrderValidator implements Validator<CreateOrderDto> {

    public static final CreateOrderValidator INSTANCE = new CreateOrderValidator();

    @Override
    public ValidationResult isValid(CreateOrderDto object) {
        ValidationResult validationResult = new ValidationResult();
        if (object.getFile() == null) {
            validationResult.add(Error.of("invalid.file", "File is invalid"));
        }
        if (object.getPaperType() == null) {
            validationResult.add(Error.of("invalid.paperType", "PaperType is invalid"));
        }
        if (object.getQuantity() == null) {
            validationResult.add(Error.of("invalid.quantity", "Quantity is invalid"));
        }
        if (object.getDelivery() == null) {
            validationResult.add(Error.of("invalid.delivery", "Delivery is invalid"));
        }
        return validationResult;
    }

    public static CreateOrderValidator getInstance() {
        return INSTANCE;
    }
}
