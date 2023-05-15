package by.academy.pharmacy2.service.util;

import by.academy.pharmacy2.dto.MedicineDTO;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static by.academy.pharmacy2.entity.Constant.EMPTY;
import static by.academy.pharmacy2.entity.Constant.ERROR_MEDICINE_IMAGE_PATH;
import static by.academy.pharmacy2.entity.Constant.MEDICINE_IMAGE_PATH;

@Component
public class MedicineValidator implements Validator {

    @Override
    public boolean supports(final Class<?> clazz) {
        return MedicineDTO.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        MedicineDTO medicineDTO = (MedicineDTO) target;
        if (medicineDTO.getMedicineImagePath() == null) {
            errors.rejectValue(MEDICINE_IMAGE_PATH, EMPTY, ERROR_MEDICINE_IMAGE_PATH);
        }
    }
}
