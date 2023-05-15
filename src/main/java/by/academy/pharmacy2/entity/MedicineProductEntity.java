package by.academy.pharmacy2.entity;

import by.academy.pharmacy2.service.util.FormConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Set;

import static by.academy.pharmacy2.entity.Constant.CART;
import static by.academy.pharmacy2.entity.Constant.ID;
import static by.academy.pharmacy2.entity.Constant.MEDICINE_ID_DB;
import static by.academy.pharmacy2.entity.Constant.MEDICINE_PRODUCT;
import static by.academy.pharmacy2.entity.Constant.MEDICINE_PRODUCTS_DB;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = MEDICINE_PRODUCTS_DB)
public class MedicineProductEntity implements Serializable {
    /**
     * Contains identification number of medicine storage.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Reference to the object containing information about medicine.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = MEDICINE_ID_DB, referencedColumnName = ID)
    private MedicineEntity medicine;
    /**
     * Contains the size or frequency of a dose of a medicine or drug.
     */
    private Short dosage;
    /**
     * Contains physical representation of medicine.
     */
    @Convert(converter = FormConverter.class)
    private Form form;
    /**
     * Contains price of the medicine.
     */
    private BigDecimal price;
    /**
     * Contains amount of concrete medicine available in the storage.
     */
    private Integer amount;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToMany(mappedBy = CART)
    private Set<UserEntity> customers;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = MEDICINE_PRODUCT)
    private Set<PrescriptionEntity> prescriptionEntities;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = MEDICINE_PRODUCT)
    private Set<OrderEntity> orderEntities;
}
