package by.academy.pharmacy2.dto;

import by.academy.pharmacy2.entity.Role;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Digits;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
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
import static by.academy.pharmacy2.entity.Constant.ERROR_HEALTH_CARE_CARD_NUMBER;
import static by.academy.pharmacy2.entity.Constant.ERROR_LOGIN;
import static by.academy.pharmacy2.entity.Constant.ERROR_PASSWORD;
import static by.academy.pharmacy2.entity.Constant.HEALTH_CARE_CARD_NUMBER_MIN_VALUE;
import static by.academy.pharmacy2.entity.Constant.INT_SIXTEEN;
import static by.academy.pharmacy2.entity.Constant.INT_ZERO;
import static by.academy.pharmacy2.entity.Constant.LOGIN_PATTERN;
import static by.academy.pharmacy2.entity.Constant.PASSWORD_PATTERN;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class UserDTO implements Serializable {
    /**
     * Contains health care card number of user.
     */
    @NotNull(message = ERROR_HEALTH_CARE_CARD_NUMBER)
    @Min(value = HEALTH_CARE_CARD_NUMBER_MIN_VALUE, message = ERROR_HEALTH_CARE_CARD_NUMBER)
    @Digits(fraction = INT_ZERO, integer = INT_SIXTEEN, message = ERROR_HEALTH_CARE_CARD_NUMBER)
    private Long healthCareCardNumber;
    /**
     * Contains users' login. Regexp expression to check login validity. Login
     * requirements: 1) Username consists of alphanumeric characters (a-zA-Z0-9),
     * lowercase, or uppercase. 2) Username allowed of the dot (.), underscore (_),
     * and hyphen (-). 3) The dot (.), underscore (_), or hyphen (-) must not be the
     * first or last character. 4) The dot (.), underscore (_), or hyphen (-) does
     * not appear consecutively, e.g. 5) The number of characters must be between 5
     * and 20. ^[a-zA-Z0-9] # start with an alphanumeric character ( # start of
     * (group 1) [._-](?![._-]) # follow by a dot, hyphen, or underscore, negative #
     * lookahead to ensures dot, hyphen, and underscore # does not appear
     * consecutively | # or [a-zA-Z0-9] # an alphanumeric character ) # end of
     * (group 1) {3,18} # ensures the length of (group 1) between 3 and 18
     * [a-zA-Z0-9]$ # end with an alphanumeric character # {3,18} plus the first and
     * last alphanumeric # characters, total length became {5,20}
     */
    @Pattern(regexp = LOGIN_PATTERN, message = ERROR_LOGIN)
    private String login;
    /**
     * Contains users' password. Regexp expression to check password validity.
     * Password requirements: 1) Password must contain at least one digit [0-9]. 2)
     * Password must contain at least one lowercase Latin character [a-z]. 3)
     * Password must contain at least one uppercase Latin character [A-Z]. 4)
     * Password must contain at least one special character like ! @ # & ( ). 5)
     * Password must contain a length of at least 8 characters and a maximum of 20
     * characters. ^ # start of line (?=.*[0-9]) # positive lookahead, digit [0-9]
     * (?=.*[a-z]) # positive lookahead, one lowercase character [a-z] (?=.*[A-Z]) #
     * positive lookahead, one uppercase character [A-Z]
     * (?=.*[!@#&()–[{}]:;',?/*~$^+=<>]) # positive lookahead, one of the special
     * character in this [.] . # matches anything {8,20} # length at least 8
     * characters and maximum of 20 characters $ # end of line
     */
    @Pattern(regexp = PASSWORD_PATTERN, message = ERROR_PASSWORD)
    private String password;
    /**
     * Contains client, pharmacist, administrator roles of users.
     */
    private Role role;
    /**
     * Contains date when user created account.
     */
    @DateTimeFormat(pattern = DATE_FORMAT)
    private Date joinedDate;
    /**
     * Contains users' personal information.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @NotNull
    @Valid
    private PersonalInfoDTO personalInfo;
    private String avatarImagePath;
}
