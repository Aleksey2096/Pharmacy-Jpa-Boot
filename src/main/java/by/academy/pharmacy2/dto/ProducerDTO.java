package by.academy.pharmacy2.dto;

import by.academy.pharmacy2.entity.Country;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.util.Date;

import static by.academy.pharmacy2.entity.Constant.DATE_FORMAT;
import static by.academy.pharmacy2.entity.Constant.ERROR_COMPANY_NAME;
import static by.academy.pharmacy2.entity.Constant.ERROR_CREATION_DATE;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class ProducerDTO implements Serializable {
    /**
     * Contains identification number of producer.
     */
    private Long id;
    /**
     * Contains name of the company.
     */
    @NotEmpty(message = ERROR_COMPANY_NAME)
    private String companyName;
    /**
     * Contains information about country where medicine was produced.
     */
    private Country country;
    /**
     * Contains date when company was created.
     */
    @NotNull(message = ERROR_CREATION_DATE)
    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date creationDate;
}
