package by.academy.pharmacy2.controller.administrator;

import by.academy.pharmacy2.service.database.OrderDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import static by.academy.pharmacy2.entity.Constant.ADMINISTRATOR_ORDERS;
import static by.academy.pharmacy2.entity.Constant.ADMINISTRATOR_ORDERS_INDEX;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_ORDER_TYPE;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_PAGE_NUMBER;
import static by.academy.pharmacy2.entity.Constant.DEFAULT_RECORDS_PER_PAGE;
import static by.academy.pharmacy2.entity.Constant.EMPTY;
import static by.academy.pharmacy2.entity.Constant.ID;
import static by.academy.pharmacy2.entity.Constant.ORDER_FIELD;
import static by.academy.pharmacy2.entity.Constant.ORDER_TYPE;
import static by.academy.pharmacy2.entity.Constant.PAGE_CURRENT_PAGE;
import static by.academy.pharmacy2.entity.Constant.PAGINATION;
import static by.academy.pharmacy2.entity.Constant.SEARCH_VALUE;
import static by.academy.pharmacy2.entity.Constant.SLASH;

@Controller
@RequestMapping(ADMINISTRATOR_ORDERS)
@RequiredArgsConstructor
public class AdministratorOrderController {
    private final OrderDBService orderDBServiceImpl;

    @GetMapping({EMPTY, SLASH})
    public String index(final Model model) {
        return findPaginated(DEFAULT_PAGE_NUMBER, DEFAULT_RECORDS_PER_PAGE,
                ID, DEFAULT_ORDER_TYPE, EMPTY, model);
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
                .addAttribute(PAGINATION, orderDBServiceImpl.readPaginated(
                        currentPage, recordsPerPage, orderField, orderType, searchValue));
        return ADMINISTRATOR_ORDERS_INDEX;
    }
}
