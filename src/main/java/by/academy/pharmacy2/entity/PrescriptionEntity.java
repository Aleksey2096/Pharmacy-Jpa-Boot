package by.academy.pharmacy2.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;

import static by.academy.pharmacy2.entity.Constant.HEALTH_CARE_CARD_NUMBER_DB;
import static by.academy.pharmacy2.entity.Constant.ID;
import static by.academy.pharmacy2.entity.Constant.MEDICINE_PRODUCT_ID_DB;
import static by.academy.pharmacy2.entity.Constant.PRESCRIPTIONS;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PRESCRIPTIONS)
public class PrescriptionEntity implements Serializable {
    /**
     * Contains identification number of prescription.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Contains user who has the prescription.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = HEALTH_CARE_CARD_NUMBER_DB,
            referencedColumnName = HEALTH_CARE_CARD_NUMBER_DB)
    private UserEntity user;
    /**
     * Contains information about medicine product.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = MEDICINE_PRODUCT_ID_DB, referencedColumnName = ID)
    private MedicineProductEntity medicineProduct;
    /**
     * Contains amount of prescribed medicine.
     */
    private Integer amount;
    /**
     * Contains date of prescription.
     */
    private Date date;
}
