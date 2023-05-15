package by.academy.pharmacy2.controller.client;

import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.dto.UserDtoDetails;
import by.academy.pharmacy2.service.database.MedicineProductDBService;
import by.academy.pharmacy2.service.util.OrderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.math.BigDecimal;

import static by.academy.pharmacy2.entity.Constant.CLIENT_CART;
import static by.academy.pharmacy2.entity.Constant.CLIENT_CART_INDEX;
import static by.academy.pharmacy2.entity.Constant.CLIENT_MEDICINES;
import static by.academy.pharmacy2.entity.Constant.EMPTY;
import static by.academy.pharmacy2.entity.Constant.MAKE_ORDER;
import static by.academy.pharmacy2.entity.Constant.MEDICINE_PRODUCTS;
import static by.academy.pharmacy2.entity.Constant.PRODUCT;
import static by.academy.pharmacy2.entity.Constant.REDIRECT;
import static by.academy.pharmacy2.entity.Constant.SLASH;
import static by.academy.pharmacy2.entity.Constant.USER;

@Controller
@RequestMapping(CLIENT_CART)
@RequiredArgsConstructor
public class ClientCartController {
    private final MedicineProductDBService medicineProductDBServiceImpl;
    private final OrderUtil orderUtil;

    @GetMapping({EMPTY, SLASH})
    public String index(final Model model) {
        UserDTO userDTO = ((UserDtoDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).userDTO();
        model.addAttribute(USER, userDTO)
                .addAttribute(MEDICINE_PRODUCTS,
                        medicineProductDBServiceImpl.getCart(userDTO.getHealthCareCardNumber()));
        return CLIENT_CART_INDEX;
    }

    @PostMapping(MAKE_ORDER)
    public String makeOrder(@RequestParam final long paymentCardNumber,
                            @RequestParam final String contactPhone,
                            @RequestParam final String deliveryAddress,
                            @RequestParam(PRODUCT) final String[] products,
                            @RequestParam final BigDecimal totalPrice) {
        orderUtil.makeOrder(paymentCardNumber, contactPhone, deliveryAddress, products, totalPrice);
        return REDIRECT + CLIENT_MEDICINES;
    }
}
