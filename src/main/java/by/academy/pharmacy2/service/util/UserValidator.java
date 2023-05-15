package by.academy.pharmacy2.service.util;

import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.service.database.UserDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import static by.academy.pharmacy2.entity.Constant.AVATAR_IMAGE_PATH;
import static by.academy.pharmacy2.entity.Constant.EMPTY;
import static by.academy.pharmacy2.entity.Constant.ERROR_AVATAR_IMAGE_PATH;
import static by.academy.pharmacy2.entity.Constant.ERROR_SAME_HEALTH_CARE_CARD_NUMBER;
import static by.academy.pharmacy2.entity.Constant.ERROR_SAME_LOGIN;
import static by.academy.pharmacy2.entity.Constant.HEALTH_CARE_CARD_NUMBER;
import static by.academy.pharmacy2.entity.Constant.LOGIN;

@Component
@RequiredArgsConstructor
public class UserValidator implements Validator {
    private final UserDetailsService userDetailsServiceImpl;
    private final UserDBService userDBServiceImpl;

    @Override
    public boolean supports(final Class<?> clazz) {
        return UserDTO.class.equals(clazz);
    }

    @Override
    public void validate(final Object target, final Errors errors) {
        UserDTO userDTO = (UserDTO) target;
        if (userDTO.getHealthCareCardNumber() != null
                && userDBServiceImpl.readById(userDTO.getHealthCareCardNumber()) != null) {
            errors.rejectValue(HEALTH_CARE_CARD_NUMBER, EMPTY, ERROR_SAME_HEALTH_CARE_CARD_NUMBER);
        }
        if (userDTO.getAvatarImagePath() == null) {
            errors.rejectValue(AVATAR_IMAGE_PATH, EMPTY, ERROR_AVATAR_IMAGE_PATH);
        }
        try {
            userDetailsServiceImpl.loadUserByUsername(userDTO.getLogin());
            errors.rejectValue(LOGIN, EMPTY, ERROR_SAME_LOGIN);
        } catch (UsernameNotFoundException ignored) {
        }
    }
}
