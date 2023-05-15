package by.academy.pharmacy2.dto;

import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import static by.academy.pharmacy2.entity.Constant.A_ZA_Z_$;
import static by.academy.pharmacy2.entity.Constant.DATE_FORMAT;
import static by.academy.pharmacy2.entity.Constant.ERROR_BIRTH_DATE;
import static by.academy.pharmacy2.entity.Constant.ERROR_EMAIL;
import static by.academy.pharmacy2.entity.Constant.ERROR_NAME;
import static by.academy.pharmacy2.entity.Constant.ERROR_PAYMENT_CARD_NUMBER;
import static by.academy.pharmacy2.entity.Constant.ERROR_PERSONAL_ACCOUNT;
import static by.academy.pharmacy2.entity.Constant.ERROR_PHONE;
import static by.academy.pharmacy2.entity.Constant.ERROR_SURNAME;
import static by.academy.pharmacy2.entity.Constant.INT_SIXTEEN;
import static by.academy.pharmacy2.entity.Constant.INT_TEN;
import static by.academy.pharmacy2.entity.Constant.INT_TWO;
import static by.academy.pharmacy2.entity.Constant.INT_ZERO;
import static by.academy.pharmacy2.entity.Constant.PAYMENT_CARD_NUMBER_MIN_VALUE;
import static by.academy.pharmacy2.entity.Constant.PHONE_PATTERN;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PersonalInfoDTO implements Serializable {
    /**
     * Contains health care card number of personal info.
     */
    private Long healthCareCardNumber;
    /**
     * Contains surname of the user.
     */
    @Pattern(regexp = A_ZA_Z_$, message = ERROR_SURNAME)
    private String surname;
    /**
     * Contains name of the user.
     */
    @Pattern(regexp = A_ZA_Z_$, message = ERROR_NAME)
    private String name;
    /**
     * Contains users' birthdate.
     */
    @NotNull(message = ERROR_BIRTH_DATE)
    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date birthDate;
    /**
     * Contains users' address.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NotNull
    @Valid
    private AddressDTO address;
    /**
     * Contains users' phone number.
     */
    @Pattern(regexp = PHONE_PATTERN, message = ERROR_PHONE)
    private String phone;
    /**
     * Contains users' email address.
     */
    @NotEmpty(message = ERROR_EMAIL)
    @Email(message = ERROR_EMAIL)
    private String email;
    /**
     * Contains position of the pharmacist.
     */
    private String position;
    /**
     * Contains clients' personal account value.
     */
    @NotNull(message = ERROR_PERSONAL_ACCOUNT)
    @PositiveOrZero(message = ERROR_PERSONAL_ACCOUNT)
    @Digits(fraction = INT_TWO, integer = INT_TEN, message = ERROR_PERSONAL_ACCOUNT)
    private BigDecimal personalAccount;
    /**
     * Contains number of clients' payment card.
     */
    @NotNull(message = ERROR_PAYMENT_CARD_NUMBER)
    @Min(value = PAYMENT_CARD_NUMBER_MIN_VALUE, message = ERROR_PAYMENT_CARD_NUMBER)
    @Digits(fraction = INT_ZERO, integer = INT_SIXTEEN, message = ERROR_PAYMENT_CARD_NUMBER)
    private Long paymentCardNumber;
}
