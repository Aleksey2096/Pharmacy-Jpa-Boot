package by.academy.pharmacy2.controller.pharmacist;

import by.academy.pharmacy2.dto.PrescriptionRequestDTO;
import by.academy.pharmacy2.entity.PrescriptionRequestStatus;
import by.academy.pharmacy2.service.database.PrescriptionRequestDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static by.academy.pharmacy2.entity.Constant.DEFAULT_ORDER_TYPE;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_PAGE_NUMBER;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_RECORDS_PER_PAGE;
import static by.academy.pharmacy2.entity.Constant.EMPTY;
import static by.academy.pharmacy2.entity.Constant.ID;
import static by.academy.pharmacy2.entity.Constant.ID_PARAM;
import static by.academy.pharmacy2.entity.Constant.ORDER_FIELD;
import static by.academy.pharmacy2.entity.Constant.ORDER_TYPE;
import static by.academy.pharmacy2.entity.Constant.PAGE_CURRENT_PAGE;
import static by.academy.pharmacy2.entity.Constant.PAGINATION;
import static by.academy.pharmacy2.entity.Constant.PHARMACIST_PRESCRIPTION_REQUESTS;
import static by.academy.pharmacy2.entity.Constant.PHARMACIST_PRESCRIPTION_REQUESTS_INDEX;
import static by.academy.pharmacy2.entity.Constant.REDIRECT;
import static by.academy.pharmacy2.entity.Constant.SEARCH_VALUE;
import static by.academy.pharmacy2.entity.Constant.SLASH;

@Controller
@RequestMapping(PHARMACIST_PRESCRIPTION_REQUESTS)
@RequiredArgsConstructor
public class PharmacistPrescriptionRequestController {
    private final PrescriptionRequestDBService prescriptionRequestDBServiceImpl;

    @GetMapping({EMPTY, SLASH})
    public String index(final Model model) {
        return findPaginatedUnprocessed(DEFAULT_PAGE_NUMBER,
                DEFAULT_RECORDS_PER_PAGE, ID, DEFAULT_ORDER_TYPE, EMPTY, model);
    }

    @GetMapping(PAGE_CURRENT_PAGE)
    public String findPaginatedUnprocessed(@PathVariable final int currentPage,
                                           @RequestParam final int recordsPerPage,
                                           @RequestParam final String orderField,
                                           @RequestParam final String orderType,
                                           @RequestParam final String searchValue,
                                           final Model model) {
        model.addAttribute(SEARCH_VALUE, searchValue)
                .addAttribute(ORDER_FIELD, orderField)
                .addAttribute(ORDER_TYPE, orderType)
                .addAttribute(PAGINATION,
                        prescriptionRequestDBServiceImpl.readPaginatedByStatus(currentPage,
                                recordsPerPage, orderField, orderType, searchValue,
                                PrescriptionRequestStatus.UNPROCESSED));
        return PHARMACIST_PRESCRIPTION_REQUESTS_INDEX;
    }

    @PatchMapping(ID_PARAM)
    public String dismissPrescriptionRequest(@PathVariable final long id,
                                             @RequestParam final String previousRequestLink) {
        PrescriptionRequestDTO prescriptionRequestDTO = prescriptionRequestDBServiceImpl
                .readById(id);
        prescriptionRequestDTO.setPrescriptionRequestStatus(PrescriptionRequestStatus.DISMISSED);
        prescriptionRequestDBServiceImpl.update(prescriptionRequestDTO);
        return REDIRECT + previousRequestLink;
    }
}
