package by.academy.pharmacy2.controller.pharmacist;

import by.academy.pharmacy2.dto.MedicineProductDTO;
import by.academy.pharmacy2.service.database.MedicineDBService;
import by.academy.pharmacy2.service.database.MedicineProductDBService;
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

import java.io.IOException;

import static by.academy.pharmacy2.entity.Constant.DEFAULT_ORDER_TYPE;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_PAGE_NUMBER;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_RECORDS_PER_PAGE;
import static by.academy.pharmacy2.entity.Constant.EMPTY;
import static by.academy.pharmacy2.entity.Constant.ID;
import static by.academy.pharmacy2.entity.Constant.ID_EDIT;
import static by.academy.pharmacy2.entity.Constant.ID_PARAM;
import static by.academy.pharmacy2.entity.Constant.MEDICINES;
import static by.academy.pharmacy2.entity.Constant.MEDICINE_PRODUCT;
import static by.academy.pharmacy2.entity.Constant.NEW;
import static by.academy.pharmacy2.entity.Constant.ORDER_FIELD;
import static by.academy.pharmacy2.entity.Constant.ORDER_TYPE;
import static by.academy.pharmacy2.entity.Constant.PAGE_CURRENT_PAGE;
import static by.academy.pharmacy2.entity.Constant.PAGINATION;
import static by.academy.pharmacy2.entity.Constant.PHARMACIST_MEDICINE_PRODUCTS;
import static by.academy.pharmacy2.entity.Constant.PHARMACIST_MEDICINE_PRODUCTS_EDIT;
import static by.academy.pharmacy2.entity.Constant.PHARMACIST_MEDICINE_PRODUCTS_INDEX;
import static by.academy.pharmacy2.entity.Constant.PHARMACIST_MEDICINE_PRODUCTS_NEW;
import static by.academy.pharmacy2.entity.Constant.PREVIOUS_REQUEST_LINK;
import static by.academy.pharmacy2.entity.Constant.REDIRECT;
import static by.academy.pharmacy2.entity.Constant.SEARCH_VALUE;
import static by.academy.pharmacy2.entity.Constant.SLASH;

@Controller
@RequestMapping(PHARMACIST_MEDICINE_PRODUCTS)
@RequiredArgsConstructor
public class PharmacistMedicineProductController {
    private final MedicineDBService medicineDBServiceImpl;
    private final MedicineProductDBService medicineProductDBServiceImpl;

    @GetMapping({EMPTY, SLASH})
    public String index(final Model model) {
        return findPaginated(DEFAULT_PAGE_NUMBER, DEFAULT_RECORDS_PER_PAGE, ID,
                DEFAULT_ORDER_TYPE, EMPTY, model);
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
                .addAttribute(PAGINATION, medicineProductDBServiceImpl.readPaginated(currentPage,
                        recordsPerPage, orderField, orderType, searchValue));
        return PHARMACIST_MEDICINE_PRODUCTS_INDEX;
    }

    @GetMapping(NEW)
    public String newObject(final Model model, @ModelAttribute(MEDICINE_PRODUCT)
    final MedicineProductDTO medicineProductDTO,
                            @RequestParam final String previousRequestLink) {
        model.addAttribute(MEDICINES, medicineDBServiceImpl.readAll())
                .addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink);
        return PHARMACIST_MEDICINE_PRODUCTS_NEW;
    }

    @PostMapping
    public String create(final Model model, @RequestParam final String previousRequestLink,
                         @ModelAttribute(MEDICINE_PRODUCT) @Valid
                         final MedicineProductDTO medicineProductDTO,
                         final BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute(MEDICINES, medicineDBServiceImpl.readAll())
                    .addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink);
            return PHARMACIST_MEDICINE_PRODUCTS_NEW;
        }

        medicineProductDBServiceImpl.create(medicineProductDTO);
        return REDIRECT + previousRequestLink;
    }

    @GetMapping(ID_EDIT)
    public String edit(final Model model, @PathVariable final long id,
                       @RequestParam final String previousRequestLink) {
        model.addAttribute(MEDICINE_PRODUCT, medicineProductDBServiceImpl.readById(id))
                .addAttribute(MEDICINES, medicineDBServiceImpl.readAll())
                .addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink);
        return PHARMACIST_MEDICINE_PRODUCTS_EDIT;
    }

    @PatchMapping(ID_PARAM)
    public String update(final Model model, @RequestParam final String previousRequestLink,
                         @ModelAttribute(MEDICINE_PRODUCT) @Valid
                         final MedicineProductDTO medicineProductDTO,
                         final BindingResult bindingResult) throws IOException {

        if (bindingResult.hasErrors()) {
            model.addAttribute(MEDICINES, medicineDBServiceImpl.readAll())
                    .addAttribute(PREVIOUS_REQUEST_LINK, previousRequestLink);
            return PHARMACIST_MEDICINE_PRODUCTS_EDIT;
        }

        medicineProductDBServiceImpl.update(medicineProductDTO);
        return REDIRECT + previousRequestLink;
    }

    @DeleteMapping(ID_PARAM)
    public String delete(@PathVariable final long id,
                         @RequestParam final String previousRequestLink) {
        medicineProductDBServiceImpl.deleteById(id);
        return REDIRECT + previousRequestLink;
    }
}
