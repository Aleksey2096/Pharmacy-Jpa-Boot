package by.academy.pharmacy2.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class OrderDTO implements Serializable {
    /**
     * Contains identification number of order.
     */
    private Long id;
    /**
     * Contains precise time and date of the order.
     */
    private LocalDateTime localDateTime;
    /**
     * Contains user who ordered the medicine.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserDTO user;
    /**
     * Contains medicine storage with medicine which was ordered by user.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private MedicineProductDTO medicineProduct;
    /**
     * Contains ordered amount of the medicine.
     */
    private Integer amount;
    /**
     * Contains price of the medicine product, while purchasing.
     */
    private BigDecimal price;

    /**
     * Contains number of clients' payment card.
     */
    private Long paymentCardNumber;
    /**
     * Contains users' contact phone number.
     */
    private String contactPhone;
    /**
     * Contains users' delivery address.
     */
    private String deliveryAddress;
}
