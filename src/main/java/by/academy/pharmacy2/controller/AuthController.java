package by.academy.pharmacy2.controller;

import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.entity.Role;
import by.academy.pharmacy2.service.database.UserDBService;
import by.academy.pharmacy2.service.util.RequestDataUtil;
import by.academy.pharmacy2.service.util.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static by.academy.pharmacy2.entity.Constant.AUTH;
import static by.academy.pharmacy2.entity.Constant.AUTH_LOGIN;
import static by.academy.pharmacy2.entity.Constant.AUTH_LOGIN1;
import static by.academy.pharmacy2.entity.Constant.AUTH_REGISTRATION1;
import static by.academy.pharmacy2.entity.Constant.LOGIN_PATH;
import static by.academy.pharmacy2.entity.Constant.PREVIOUS_REQUEST_LINK;
import static by.academy.pharmacy2.entity.Constant.REDIRECT;
import static by.academy.pharmacy2.entity.Constant.REGISTRATION;
import static by.academy.pharmacy2.entity.Constant.USER;

@Controller
@RequestMapping(AUTH)
@RequiredArgsConstructor
public class AuthController {
    private final UserDBService userDBServiceImpl;
    private final UserValidator userValidator;

    @GetMapping(LOGIN_PATH)
    public String loginPage() {
        return AUTH_LOGIN1;
    }

    @GetMapping(REGISTRATION)
    public String registrationPage(final Model model, @ModelAttribute(USER) final UserDTO userDTO,
                                   @RequestParam final String previousRequestLink) {
        model.addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink);
        return AUTH_REGISTRATION1;
    }

    @PostMapping(REGISTRATION)
    public String performRegistration(final Model model,
                                      @RequestParam final String previousRequestLink,
                                      @ModelAttribute(USER) @Valid final UserDTO userDTO,
                                      final BindingResult bindingResult,
                                      @RequestParam final MultipartFile image) throws IOException {

        RequestDataUtil.saveUserImage(userDTO, image);

        userValidator.validate(userDTO, bindingResult);

        model.addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink);
        if (bindingResult.hasErrors()) {
            return AUTH_REGISTRATION1;
        }

        userDTO.setRole(Role.CLIENT);

        userDBServiceImpl.create(userDTO);
        return REDIRECT + AUTH_LOGIN;
    }
}
