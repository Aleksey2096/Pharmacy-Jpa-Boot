package by.academy.pharmacy2.controller.administrator;

import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.service.database.UserDBService;
import by.academy.pharmacy2.service.util.RequestDataUtil;
import by.academy.pharmacy2.service.util.UserValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static by.academy.pharmacy2.entity.Constant.ADMINISTRATOR_USERS;
import static by.academy.pharmacy2.entity.Constant.ADMINISTRATOR_USERS_EDIT;
import static by.academy.pharmacy2.entity.Constant.ADMINISTRATOR_USERS_INDEX;
import static by.academy.pharmacy2.entity.Constant.ADMINISTRATOR_USERS_NEW;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_ORDER_TYPE;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_PAGE_NUMBER;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_RECORDS_PER_PAGE;
import static by.academy.pharmacy2.entity.Constant.EMPTY;
import static by.academy.pharmacy2.entity.Constant.HEALTH_CARE_CARD_NUMBER;
import static by.academy.pharmacy2.entity.Constant.ID_EDIT;
import static by.academy.pharmacy2.entity.Constant.ID_PARAM;
import static by.academy.pharmacy2.entity.Constant.NEW;
import static by.academy.pharmacy2.entity.Constant.ORDER_FIELD;
import static by.academy.pharmacy2.entity.Constant.ORDER_TYPE;
import static by.academy.pharmacy2.entity.Constant.PAGE_CURRENT_PAGE;
import static by.academy.pharmacy2.entity.Constant.PAGINATION;
import static by.academy.pharmacy2.entity.Constant.PREVIOUS_REQUEST_LINK;
import static by.academy.pharmacy2.entity.Constant.REDIRECT;
import static by.academy.pharmacy2.entity.Constant.SEARCH_VALUE;
import static by.academy.pharmacy2.entity.Constant.SLASH;
import static by.academy.pharmacy2.entity.Constant.USER;

@Controller
@RequestMapping(ADMINISTRATOR_USERS)
@RequiredArgsConstructor
public class AdministratorUserController {
    private final UserDBService userDBServiceImpl;
    private final UserValidator userValidator;

    @GetMapping({EMPTY, SLASH})
    public String index(final Model model) {
        return findPaginated(DEFAULT_PAGE_NUMBER, DEFAULT_RECORDS_PER_PAGE,
                HEALTH_CARE_CARD_NUMBER, DEFAULT_ORDER_TYPE, EMPTY, model);
    }

    @GetMapping(PAGE_CURRENT_PAGE)
    public String findPaginated(@PathVariable final int currentPage,
                                @RequestParam final int recordsPerPage,
                                @RequestParam final String orderField,
                                @RequestParam final String orderType,
                                @RequestParam final String searchValue,
                                final Model model) {
        model.addAttribute(SEARCH_VALUE, searchValue)
                .addAttribute(ORDER_FIELD, orderField)
                .addAttribute(ORDER_TYPE, orderType)
                .addAttribute(PAGINATION, userDBServiceImpl.readPaginated(currentPage,
                        recordsPerPage, orderField, orderType, searchValue));
        return ADMINISTRATOR_USERS_INDEX;
    }

    @GetMapping(NEW)
    public String newObject(final Model model, @ModelAttribute(USER) final UserDTO userDTO,
                            @RequestParam final String previousRequestLink) {
        model.addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink);
        return ADMINISTRATOR_USERS_NEW;
    }

    @PostMapping
    public String create(final Model model, @RequestParam final String previousRequestLink,
                         @ModelAttribute(USER) @Valid final UserDTO userDTO,
                         final BindingResult bindingResult,
                         @RequestParam final MultipartFile image) throws IOException {

        RequestDataUtil.saveUserImage(userDTO, image);

        userValidator.validate(userDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink);
            return ADMINISTRATOR_USERS_NEW;
        }

        userDBServiceImpl.create(userDTO);
        return REDIRECT + previousRequestLink;
    }

    @GetMapping(ID_EDIT)
    public String edit(final Model model, @PathVariable final long id,
                       @RequestParam final String previousRequestLink) {
        model.addAttribute(USER, userDBServiceImpl.readById(id))
                .addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink);
        return ADMINISTRATOR_USERS_EDIT;
    }

    @PatchMapping(ID_PARAM)
    public String update(final Model model, @RequestParam final String previousRequestLink,
                         @ModelAttribute(USER) @Valid final UserDTO userDTO,
                         final BindingResult bindingResult, @PathVariable final long id,
                         @RequestParam final MultipartFile image) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink);
            return ADMINISTRATOR_USERS_EDIT;
        }

        RequestDataUtil.saveUserImage(userDTO, image);
        if (userDTO.getAvatarImagePath() == null) {
            userDTO.setAvatarImagePath(userDBServiceImpl.readById(id).getAvatarImagePath());
        }

        userDBServiceImpl.update(userDTO);
        return REDIRECT + previousRequestLink;
    }

    @DeleteMapping(ID_PARAM)
    public String delete(@PathVariable final long id,
                         @RequestParam final String previousRequestLink) {
        userDBServiceImpl.deleteById(id);
        return REDIRECT + previousRequestLink;
    }
}
