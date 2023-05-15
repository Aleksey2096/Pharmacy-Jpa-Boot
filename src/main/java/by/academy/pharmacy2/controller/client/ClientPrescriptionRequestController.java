package by.academy.pharmacy2.controller.client;

import by.academy.pharmacy2.dto.PrescriptionRequestDTO;
import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.dto.UserDtoDetails;
import by.academy.pharmacy2.service.database.PrescriptionRequestDBService;
import by.academy.pharmacy2.service.util.PrescriptionRequestValidator;
import by.academy.pharmacy2.service.util.RequestDataUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

import static by.academy.pharmacy2.entity.Constant.CLIENT_PRESCRIPTION_REQUESTS;
import static by.academy.pharmacy2.entity.Constant.CLIENT_PRESCRIPTION_REQUESTS_INDEX;
import static by.academy.pharmacy2.entity.Constant.CLIENT_PRESCRIPTION_REQUESTS_NEW;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_ORDER_TYPE;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_PAGE_NUMBER;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_RECORDS_PER_PAGE;
import static by.academy.pharmacy2.entity.Constant.EMPTY;
import static by.academy.pharmacy2.entity.Constant.ID;
import static by.academy.pharmacy2.entity.Constant.NEW;
import static by.academy.pharmacy2.entity.Constant.ORDER_FIELD;
import static by.academy.pharmacy2.entity.Constant.ORDER_TYPE;
import static by.academy.pharmacy2.entity.Constant.PAGE_CURRENT_PAGE;
import static by.academy.pharmacy2.entity.Constant.PAGINATION;
import static by.academy.pharmacy2.entity.Constant.PRESCRIPTION_REQUEST;
import static by.academy.pharmacy2.entity.Constant.PREVIOUS_REQUEST_LINK;
import static by.academy.pharmacy2.entity.Constant.REDIRECT;
import static by.academy.pharmacy2.entity.Constant.SEARCH_VALUE;
import static by.academy.pharmacy2.entity.Constant.SLASH;

@Controller
@RequestMapping(CLIENT_PRESCRIPTION_REQUESTS)
@RequiredArgsConstructor
public class ClientPrescriptionRequestController {
    private final PrescriptionRequestDBService prescriptionRequestDBServiceImpl;
    private final PrescriptionRequestValidator prescriptionRequestValidator;

    @GetMapping({EMPTY, SLASH})
    public String index(final Model model) {
        return findPaginatedByUser(DEFAULT_PAGE_NUMBER, DEFAULT_RECORDS_PER_PAGE, ID,
                DEFAULT_ORDER_TYPE, EMPTY, model);
    }

    @GetMapping(PAGE_CURRENT_PAGE)
    public String findPaginatedByUser(@PathVariable final int currentPage,
                                      @RequestParam final int recordsPerPage,
                                      @RequestParam final String orderField,
                                      @RequestParam final String orderType,
                                      @RequestParam final String searchValue,
                                      final Model model) {
        UserDTO userDTO = ((UserDtoDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).userDTO();
        model.addAttribute(SEARCH_VALUE, searchValue)
                .addAttribute(ORDER_FIELD, orderField)
                .addAttribute(ORDER_TYPE, orderType)
                .addAttribute(PAGINATION, prescriptionRequestDBServiceImpl.readPaginatedByUser(
                        currentPage, recordsPerPage, orderField, orderType, searchValue, userDTO));
        return CLIENT_PRESCRIPTION_REQUESTS_INDEX;
    }

    @GetMapping(NEW)
    public String newObject(final Model model,
                            @ModelAttribute(PRESCRIPTION_REQUEST)
                            final PrescriptionRequestDTO prescriptionRequestDTO,
                            @RequestParam final String previousRequestLink) {
        model.addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink);
        return CLIENT_PRESCRIPTION_REQUESTS_NEW;
    }

    @PostMapping
    public String create(final Model model, @RequestParam final String previousRequestLink,
                         @ModelAttribute(PRESCRIPTION_REQUEST)
                         final PrescriptionRequestDTO prescriptionRequestDTO,
                         final BindingResult bindingResult, @RequestParam final MultipartFile image)
            throws IOException {

        RequestDataUtil.savePrescriptionScan(prescriptionRequestDTO, image);

        prescriptionRequestValidator.validate(prescriptionRequestDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink);
            return CLIENT_PRESCRIPTION_REQUESTS_NEW;
        }

        prescriptionRequestDBServiceImpl.create(prescriptionRequestDTO);
        return REDIRECT + previousRequestLink;
    }
}
