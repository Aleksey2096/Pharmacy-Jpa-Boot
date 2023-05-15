package by.academy.pharmacy2.service.util;

import by.academy.pharmacy2.dto.PrescriptionDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static by.academy.pharmacy2.entity.Constant.EMPTY;
import static by.academy.pharmacy2.entity.Constant.ERROR_HEALTH_CARE_CARD_NUMBER;
import static by.academy.pharmacy2.entity.Constant.ERROR_MEDICINE_PRODUCT_DTO;
import static by.academy.pharmacy2.entity.Constant.INT_SIXTEEN;
import static by.academy.pharmacy2.entity.Constant.MEDICINE_PRODUCT_ID;
import static by.academy.pharmacy2.entity.Constant.USER_HEALTH_CARE_CARD_NUMBER;

@Component
public class PrescriptionValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return PrescriptionDTO.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        PrescriptionDTO prescriptionDTO = (PrescriptionDTO) target;
        Long healthCareCardNumber = prescriptionDTO.getUser().getHealthCareCardNumber();
        if (healthCareCardNumber == null
                || healthCareCardNumber.toString().length() != INT_SIXTEEN) {
            errors.rejectValue(USER_HEALTH_CARE_CARD_NUMBER, EMPTY, ERROR_HEALTH_CARE_CARD_NUMBER);
        }
        if (prescriptionDTO.getMedicineProduct().getId() == null) {
            errors.rejectValue(MEDICINE_PRODUCT_ID, EMPTY, ERROR_MEDICINE_PRODUCT_DTO);
        }
    }
}
