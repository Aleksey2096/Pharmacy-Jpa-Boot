package by.academy.pharmacy2.controller.client;

import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.dto.UserDtoDetails;
import by.academy.pharmacy2.service.database.UserDBService;
import by.academy.pharmacy2.service.util.RequestDataUtil;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static by.academy.pharmacy2.entity.Constant.CLIENT_USERS;
import static by.academy.pharmacy2.entity.Constant.CLIENT_USERS_EDIT;
import static by.academy.pharmacy2.entity.Constant.EDIT;
import static by.academy.pharmacy2.entity.Constant.ID_PARAM;
import static by.academy.pharmacy2.entity.Constant.PREVIOUS_REQUEST_LINK;
import static by.academy.pharmacy2.entity.Constant.REDIRECT;
import static by.academy.pharmacy2.entity.Constant.USER;

@Controller
@RequestMapping(CLIENT_USERS)
@RequiredArgsConstructor
public class ClientUserController {
    private final UserDBService userDBServiceImpl;

    @GetMapping(EDIT)
    public String edit(final Model model, @RequestParam final String previousRequestLink) {
        UserDTO userDTO = ((UserDtoDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).userDTO();
        model.addAttribute(USER, userDBServiceImpl.readById(userDTO.getHealthCareCardNumber()))
                .addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink);
        return CLIENT_USERS_EDIT;
    }

    @PatchMapping(ID_PARAM)
    public String update(final Model model, @RequestParam final String previousRequestLink,
                         @ModelAttribute(USER) @Valid final UserDTO userDTO,
                         final BindingResult bindingResult,
                         @PathVariable final long id, @RequestParam final MultipartFile image)
            throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink);
            return CLIENT_USERS_EDIT;
        }

        UserDTO oldUser = userDBServiceImpl.readById(id);
        RequestDataUtil.saveUserImage(userDTO, image);
        if (userDTO.getAvatarImagePath() == null) {
            userDTO.setAvatarImagePath(oldUser.getAvatarImagePath());
        }

        userDTO.setRole(oldUser.getRole());

        userDBServiceImpl.update(userDTO);
        return REDIRECT + previousRequestLink;
    }
}
