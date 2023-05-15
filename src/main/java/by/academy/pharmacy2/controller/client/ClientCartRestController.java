package by.academy.pharmacy2.controller.client;

import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.dto.UserDtoDetails;
import by.academy.pharmacy2.service.database.UserDBService;
import by.academy.pharmacy2.service.util.OrderUtil;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.math.BigDecimal;

import static by.academy.pharmacy2.entity.Constant.CLIENT_CART_REST;
import static by.academy.pharmacy2.entity.Constant.ID_ADD_TO_CART;
import static by.academy.pharmacy2.entity.Constant.ID_DELETE_FROM_CART;
import static by.academy.pharmacy2.entity.Constant.PRODUCT;
import static by.academy.pharmacy2.entity.Constant.RESULT_NOT_ENOUGH_FUNDS;
import static by.academy.pharmacy2.entity.Constant.RESULT_OK;
import static by.academy.pharmacy2.entity.Constant.RESULT_PRESCRIPTION_REQUIRED;
import static by.academy.pharmacy2.entity.Constant.VALIDATE_ORDER;

@RestController
@RequestMapping(CLIENT_CART_REST)
@RequiredArgsConstructor
public class ClientCartRestController {
    private final UserDBService userDBServiceImpl;
    private final OrderUtil orderUtil;

    @PostMapping(ID_ADD_TO_CART)
    public ResponseEntity<Void> addToCart(@PathVariable final long id) {
        UserDTO userDTO = ((UserDtoDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).userDTO();
        userDBServiceImpl.addToCart(userDTO.getHealthCareCardNumber(), id);
        return ResponseEntity.noContent().build();
    }

    @DeleteMapping(ID_DELETE_FROM_CART)
    public ResponseEntity<Void> deleteFromCart(@PathVariable final long id) {
        UserDTO userDTO = ((UserDtoDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).userDTO();
        userDBServiceImpl.deleteFromCart(userDTO.getHealthCareCardNumber(), id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping(VALIDATE_ORDER)
    public ResponseEntity<String> validateOrder(@RequestParam(PRODUCT) final String[] products,
                                                @RequestParam final BigDecimal totalPrice) {
        if (!orderUtil.isEnoughAccountFunds(totalPrice)) {
            return new ResponseEntity<>(RESULT_NOT_ENOUGH_FUNDS, HttpStatus.BAD_REQUEST);
        }
        if (!orderUtil.isValidOrders(products)) {
            return new ResponseEntity<>(RESULT_PRESCRIPTION_REQUIRED, HttpStatus.BAD_REQUEST);
        }
        return new ResponseEntity<>(RESULT_OK, HttpStatus.OK);
    }
}
