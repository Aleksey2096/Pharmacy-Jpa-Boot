package by.academy.pharmacy2.controller.pharmacist;

import by.academy.pharmacy2.dto.PrescriptionDTO;
import by.academy.pharmacy2.dto.PrescriptionRequestDTO;
import by.academy.pharmacy2.entity.PrescriptionRequestStatus;
import by.academy.pharmacy2.service.database.PrescriptionDBService;
import by.academy.pharmacy2.service.database.PrescriptionRequestDBService;
import by.academy.pharmacy2.service.util.PrescriptionValidator;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static by.academy.pharmacy2.entity.Constant.PHARMACIST_PRESCRIPTIONS;
import static by.academy.pharmacy2.entity.Constant.PHARMACIST_PRESCRIPTIONS_NEW;
import static by.academy.pharmacy2.entity.Constant.PRESCRIPTION;
import static by.academy.pharmacy2.entity.Constant.PRESCRIPTION_REQUESTS_ID;
import static by.academy.pharmacy2.entity.Constant.PRESCRIPTION_REQUEST_ID;
import static by.academy.pharmacy2.entity.Constant.PRESCRIPTION_REQUEST_ID_NEW;
import static by.academy.pharmacy2.entity.Constant.PREVIOUS_REQUEST_LINK;
import static by.academy.pharmacy2.entity.Constant.PREVIOUS_REQUEST_LINK_APPROVED;
import static by.academy.pharmacy2.entity.Constant.REDIRECT;

@Controller
@RequestMapping(PHARMACIST_PRESCRIPTIONS)
@RequiredArgsConstructor
public class PharmacistPrescriptionController {
    private final PrescriptionRequestDBService prescriptionRequestDBServiceImpl;
    private final PrescriptionDBService prescriptionDBServiceImpl;
    private final PrescriptionValidator prescriptionValidator;

    @GetMapping(PRESCRIPTION_REQUEST_ID_NEW)
    public String newObject(final Model model, @PathVariable final long prescriptionRequestId,
                            @RequestParam final String previousRequestLink,
                            @RequestParam final String previousRequestLinkApproved,
                            @ModelAttribute(PRESCRIPTION) final PrescriptionDTO prescriptionDTO) {
        model.addAttribute(PRESCRIPTION_REQUESTS_ID, prescriptionRequestId)
                .addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink)
                .addAttribute(PREVIOUS_REQUEST_LINK_APPROVED, previousRequestLinkApproved);
        return PHARMACIST_PRESCRIPTIONS_NEW;
    }

    @PostMapping(PRESCRIPTION_REQUEST_ID)
    public String createApprove(final Model model, @PathVariable final long prescriptionRequestId,
                                @RequestParam final String previousRequestLink,
                                @RequestParam final String previousRequestLinkApproved,
                                @ModelAttribute(PRESCRIPTION) @Valid
                                final PrescriptionDTO prescriptionDTO,
                                final BindingResult bindingResult) {

        prescriptionValidator.validate(prescriptionDTO, bindingResult);

        if (bindingResult.hasErrors()) {
            model.addAttribute(PRESCRIPTION_REQUESTS_ID, prescriptionRequestId)
                    .addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink)
                    .addAttribute(PREVIOUS_REQUEST_LINK_APPROVED, previousRequestLinkApproved);
            return PHARMACIST_PRESCRIPTIONS_NEW;
        }

        prescriptionDBServiceImpl.create(prescriptionDTO);
        PrescriptionRequestDTO prescriptionRequestDTO = prescriptionRequestDBServiceImpl
                .readById(prescriptionRequestId);
        prescriptionRequestDTO.setPrescriptionRequestStatus(PrescriptionRequestStatus.APPROVED);
        prescriptionRequestDBServiceImpl.update(prescriptionRequestDTO);
        return REDIRECT + previousRequestLinkApproved;
    }
}
