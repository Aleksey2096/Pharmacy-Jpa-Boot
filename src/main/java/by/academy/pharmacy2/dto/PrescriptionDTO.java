package by.academy.pharmacy2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import static by.academy.pharmacy2.entity.Constant.DATE_FORMAT;
import static by.academy.pharmacy2.entity.Constant.ERROR_AMOUNT;
import static by.academy.pharmacy2.entity.Constant.ERROR_DATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PrescriptionDTO implements Serializable {
    /**
     * Contains identification number of prescription.
     */
    private Long id;
    /**
     * Contains user who has the prescription.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserDTO user;
    /**
     * Contains information about medicine product.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private MedicineProductDTO medicineProduct;
    /**
     * Contains amount of prescribed medicine.
     */
    @NotNull(message = ERROR_AMOUNT)
    @Positive(message = ERROR_AMOUNT)
    private Integer amount;
    /**
     * Contains date of prescription.
     */
    @NotNull(message = ERROR_DATE)
    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date date;
}
