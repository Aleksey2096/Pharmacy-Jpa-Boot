package by.academy.pharmacy2.service.util;

import by.academy.pharmacy2.dto.MedicineProductDTO;
import by.academy.pharmacy2.dto.OrderDTO;
import by.academy.pharmacy2.dto.PrescriptionDTO;
import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.dto.UserDtoDetails;
import by.academy.pharmacy2.service.database.MedicineProductDBService;
import by.academy.pharmacy2.service.database.OrderDBService;
import by.academy.pharmacy2.service.database.PrescriptionDBService;
import by.academy.pharmacy2.service.database.UserDBService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static by.academy.pharmacy2.entity.Constant.SPACE;

@Service
@RequiredArgsConstructor
public class OrderUtil {
    private final MedicineProductDBService medicineProductDBServiceImpl;
    private final PrescriptionDBService prescriptionDBServiceImpl;
    private final UserDBService userDBServiceImpl;
    private final OrderDBService orderDBServiceImpl;

    public boolean isEnoughAccountFunds(final BigDecimal totalPrice) {
        UserDTO userDTO = ((UserDtoDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).userDTO();
        return userDTO.getPersonalInfo().getPersonalAccount().compareTo(totalPrice) >= 0;
    }

    public boolean isValidOrders(final String[] products) {
        UserDTO userDTO = ((UserDtoDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).userDTO();
        List<PrescriptionDTO> prescriptions = prescriptionDBServiceImpl
                .getPrescriptions(userDTO.getHealthCareCardNumber());
        List<OrderDTO> orders = extractOrders(null, null, null, products);
        Map<MedicineProductDTO, Integer> prescriptionsMap = prescriptions.stream().collect(
                Collectors.toMap(PrescriptionDTO::getMedicineProduct, PrescriptionDTO::getAmount));
        Map<MedicineProductDTO, Integer> ordersMap = orders.stream()
                .filter(x -> !x.getMedicineProduct().getMedicine().getIsNonprescription())
                .collect(Collectors.toMap(OrderDTO::getMedicineProduct, OrderDTO::getAmount));
        for (MedicineProductDTO medicineProductDTO : ordersMap.keySet()) {
            if (!prescriptionsMap.containsKey(medicineProductDTO) || prescriptionsMap
                    .get(medicineProductDTO) < ordersMap.get(medicineProductDTO)) {
                return false;
            }
        }
        return true;
    }

    public void makeOrder(final long paymentCardNumber, final String contactPhone,
                          final String deliveryAddress, final String[] products,
                          final BigDecimal totalPrice) {
        UserDTO userDTO = ((UserDtoDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).userDTO();
        List<PrescriptionDTO> prescriptions = prescriptionDBServiceImpl
                .getPrescriptions(userDTO.getHealthCareCardNumber());
        List<OrderDTO> orders = extractOrders(paymentCardNumber, contactPhone, deliveryAddress,
                products);
        updateUserAccount(totalPrice);
        updateUserPrescriptions(orders, prescriptions);
        updateUserCart(orders);
        updateMedicineProducts(orders);
        createOrders(orders);
    }

    private List<OrderDTO> extractOrders(final Long paymentCardNumber,
                                         final String contactPhone, final String deliveryAddress,
                                         final String[] products) {
        LocalDateTime localDateTime = LocalDateTime.now();
        List<OrderDTO> orderDTOs = new ArrayList<>();
        UserDTO userDTO = ((UserDtoDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).userDTO();
        for (final String product : products) {
            String[] productArray = product.split(SPACE);
            OrderDTO orderDTO = OrderDTO.builder()
                    .price(new BigDecimal(productArray[1]))
                    .amount(Integer.parseInt(productArray[2]))
                    .localDateTime(localDateTime)
                    .paymentCardNumber(paymentCardNumber)
                    .contactPhone(contactPhone)
                    .deliveryAddress(deliveryAddress)
                    .user(userDTO)
                    .medicineProduct(
                            medicineProductDBServiceImpl.readById(Long.parseLong(productArray[0])))
                    .build();
            orderDTOs.add(orderDTO);
        }
        return orderDTOs;
    }

    private void updateUserAccount(final BigDecimal totalPrice) {
        UserDTO principalUser = ((UserDtoDetails) SecurityContextHolder.getContext()
                .getAuthentication().getPrincipal()).userDTO();
        UserDTO databaseUser = userDBServiceImpl.readById(principalUser.getHealthCareCardNumber());
        BigDecimal currentAccount = databaseUser.getPersonalInfo().getPersonalAccount()
                .subtract(totalPrice);
        principalUser.getPersonalInfo().setPersonalAccount(currentAccount);
        databaseUser.getPersonalInfo().setPersonalAccount(currentAccount);
        userDBServiceImpl.update(databaseUser);
    }

    private void updateUserPrescriptions(final List<OrderDTO> orderDTOs,
                                         final List<PrescriptionDTO> userPrescriptions) {
        for (PrescriptionDTO prescriptionDTO : userPrescriptions) {
            for (OrderDTO orderDTO : orderDTOs) {
                if (prescriptionDTO.getMedicineProduct().equals(orderDTO.getMedicineProduct())) {
                    if (Objects.equals(prescriptionDTO.getAmount(), orderDTO.getAmount())) {
                        prescriptionDBServiceImpl.deleteById(prescriptionDTO.getId());
                    } else {
                        prescriptionDTO
                                .setAmount(prescriptionDTO.getAmount() - orderDTO.getAmount());
                        prescriptionDBServiceImpl.update(prescriptionDTO);
                    }
                }
            }
        }
    }

    private void updateUserCart(final List<OrderDTO> orderDTOs) {
        UserDTO userDTO = ((UserDtoDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).userDTO();
        orderDTOs.forEach(x -> userDBServiceImpl.deleteFromCart(userDTO.getHealthCareCardNumber(),
                x.getMedicineProduct().getId()));
    }

    private void updateMedicineProducts(final List<OrderDTO> orderDTOs) {
        orderDTOs.forEach(x -> {
            x.getMedicineProduct().setAmount(x.getMedicineProduct().getAmount() - x.getAmount());
            medicineProductDBServiceImpl.update(x.getMedicineProduct());
        });
    }

    private void createOrders(final List<OrderDTO> orderDTOs) {
        orderDTOs.forEach(orderDBServiceImpl::create);
    }
}
