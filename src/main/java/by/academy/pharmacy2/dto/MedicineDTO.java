package by.academy.pharmacy2.dto;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
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
import static by.academy.pharmacy2.entity.Constant.ERROR_APPROVAL_DATE;
import static by.academy.pharmacy2.entity.Constant.ERROR_TITLE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class MedicineDTO implements Serializable {
    /**
     * Contains identification number of medicine.
     */
    private Long id;
    /**
     * Contains title of the medicine.
     */
    @NotEmpty(message = ERROR_TITLE)
    private String title;
    /**
     * Indicates whether medicine needs prescription to be sold or not.
     */
    private Boolean isNonprescription;
    /**
     * Contains information about medicine producer.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private ProducerDTO producer;
    @NotNull(message = ERROR_APPROVAL_DATE)
    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date approvalDate;
    private String medicineImagePath;
}
