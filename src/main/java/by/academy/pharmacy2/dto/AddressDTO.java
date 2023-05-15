package by.academy.pharmacy2.dto;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

import static by.academy.pharmacy2.entity.Constant.A_ZA_Z_$;
import static by.academy.pharmacy2.entity.Constant.ERROR_APARTMENT;
import static by.academy.pharmacy2.entity.Constant.ERROR_CITY;
import static by.academy.pharmacy2.entity.Constant.ERROR_HOUSE;
import static by.academy.pharmacy2.entity.Constant.ERROR_POSTCODE;
import static by.academy.pharmacy2.entity.Constant.ERROR_STREET;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class AddressDTO implements Serializable {
    private Long healthCareCardNumber;
    @NotNull(message = ERROR_POSTCODE)
    @Positive(message = ERROR_POSTCODE)
    private Integer postcode;
    @Pattern(regexp = A_ZA_Z_$, message = ERROR_CITY)
    private String city;
    @Pattern(regexp = A_ZA_Z_$, message = ERROR_STREET)
    private String street;
    @NotNull(message = ERROR_HOUSE)
    @Positive(message = ERROR_HOUSE)
    private Integer house;
    @NotNull(message = ERROR_APARTMENT)
    @Positive(message = ERROR_APARTMENT)
    private Integer apartment;
}
