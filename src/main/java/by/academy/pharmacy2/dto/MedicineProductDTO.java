package by.academy.pharmacy2.dto;

import by.academy.pharmacy2.entity.Form;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;

import static by.academy.pharmacy2.entity.Constant.ERROR_AMOUNT;
import static by.academy.pharmacy2.entity.Constant.ERROR_DOSAGE;
import static by.academy.pharmacy2.entity.Constant.ERROR_PRICE;
import static by.academy.pharmacy2.entity.Constant.INT_SIX;
import static by.academy.pharmacy2.entity.Constant.INT_TWO;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MedicineProductDTO implements Serializable {
    /**
     * Contains identification number of medicine storage.
     */
    private Long id;
    /**
     * Reference to the object containing information about medicine.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private MedicineDTO medicine;
    /**
     * Contains the size or frequency of a dose of a medicine or drug.
     */
    @NotNull(message = ERROR_DOSAGE)
    @Positive(message = ERROR_DOSAGE)
    private Short dosage;
    /**
     * Contains physical representation of medicine.
     */
    private Form form;
    /**
     * Contains price of the medicine.
     */
    @NotNull(message = ERROR_PRICE)
    @Positive(message = ERROR_PRICE)
    @Digits(fraction = INT_TWO, integer = INT_SIX, message = ERROR_PRICE)
    private BigDecimal price;
    /**
     * Contains amount of concrete medicine available in the storage.
     */
    @NotNull(message = ERROR_AMOUNT)
    @PositiveOrZero(message = ERROR_AMOUNT)
    private Integer amount;
}
