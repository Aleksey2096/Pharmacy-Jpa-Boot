package by.academy.pharmacy2.controller.client;

import by.academy.pharmacy2.service.database.MedicineProductDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static by.academy.pharmacy2.entity.Constant.CLIENT_MEDICINE_PRODUCTS;
import static by.academy.pharmacy2.entity.Constant.CLIENT_MEDICINE_PRODUCTS_INDEX;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_ORDER_TYPE;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_PAGE_NUMBER;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_RECORDS_PER_PAGE;
import static by.academy.pharmacy2.entity.Constant.EMPTY;
import static by.academy.pharmacy2.entity.Constant.ID;
import static by.academy.pharmacy2.entity.Constant.MEDICINE_ID;
import static by.academy.pharmacy2.entity.Constant.MEDICINE_ID_PAGE_CURRENT_PAGE;
import static by.academy.pharmacy2.entity.Constant.ORDER_FIELD;
import static by.academy.pharmacy2.entity.Constant.ORDER_TYPE;
import static by.academy.pharmacy2.entity.Constant.PAGINATION;
import static by.academy.pharmacy2.entity.Constant.SEARCH_VALUE;

@Controller
@RequestMapping(CLIENT_MEDICINE_PRODUCTS)
@RequiredArgsConstructor
public class ClientMedicineProductController {
    private final MedicineProductDBService medicineProductDBServiceImpl;

    @GetMapping(MEDICINE_ID)
    public String index(@PathVariable final long medicineId, final Model model) {
        return findPaginatedByMedicine(medicineId, DEFAULT_PAGE_NUMBER, DEFAULT_RECORDS_PER_PAGE,
                ID, DEFAULT_ORDER_TYPE, EMPTY, model);
    }

    @GetMapping(MEDICINE_ID_PAGE_CURRENT_PAGE)
    public String findPaginatedByMedicine(@PathVariable final long medicineId,
                                          @PathVariable final int currentPage,
                                          @RequestParam final int recordsPerPage,
                                          @RequestParam final String orderField,
                                          @RequestParam final String orderType,
                                          @RequestParam final String searchValue,
                                          final Model model) {
        model.addAttribute(SEARCH_VALUE, searchValue)
                .addAttribute(ORDER_FIELD, orderField)
                .addAttribute(ORDER_TYPE, orderType)
                .addAttribute(PAGINATION,
                        medicineProductDBServiceImpl.readPaginatedByMedicine(currentPage,
                                recordsPerPage, orderField, orderType, searchValue, medicineId));
        return CLIENT_MEDICINE_PRODUCTS_INDEX;
    }
}
