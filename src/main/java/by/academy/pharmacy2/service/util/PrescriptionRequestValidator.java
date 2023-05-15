package by.academy.pharmacy2.service.util;

import by.academy.pharmacy2.dto.PrescriptionRequestDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static by.academy.pharmacy2.entity.Constant.EMPTY;
import static by.academy.pharmacy2.entity.Constant.ERROR_PRESCRIPTION_SCAN_PATH;
import static by.academy.pharmacy2.entity.Constant.PRESCRIPTION_SCAN_PATH;

@Component
public class PrescriptionRequestValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return PrescriptionRequestDTO.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        PrescriptionRequestDTO prescriptionRequestDTO = (PrescriptionRequestDTO) target;
        if (prescriptionRequestDTO.getPrescriptionScanPath() == null) {
            errors.rejectValue(PRESCRIPTION_SCAN_PATH, EMPTY, ERROR_PRESCRIPTION_SCAN_PATH);
        }
    }
}
