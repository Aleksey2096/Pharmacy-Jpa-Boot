package by.academy.pharmacy2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
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
import java.util.Date;
import java.util.Set;

import static by.academy.pharmacy2.entity.Constant.APPROVAL_DATE_DB;
import static by.academy.pharmacy2.entity.Constant.ID;
import static by.academy.pharmacy2.entity.Constant.IS_NONPRESCRIPTION_DB;
import static by.academy.pharmacy2.entity.Constant.MEDICINE;
import static by.academy.pharmacy2.entity.Constant.MEDICINES;
import static by.academy.pharmacy2.entity.Constant.MEDICINE_IMAGE_PATH_DB;
import static by.academy.pharmacy2.entity.Constant.PRODUCER_ID_DB;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = MEDICINES)
public class MedicineEntity implements Serializable {
    /**
     * Contains identification number of medicine.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Contains title of the medicine.
     */
    private String title;
    /**
     * Indicates whether medicine needs prescription to be sold or not.
     */
    @Column(name = IS_NONPRESCRIPTION_DB)
    private Boolean isNonprescription;
    /**
     * Contains information about medicine producer.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = PRODUCER_ID_DB, referencedColumnName = ID)
    private ProducerEntity producer;
    @Column(name = APPROVAL_DATE_DB)
    private Date approvalDate;
    @Column(name = MEDICINE_IMAGE_PATH_DB)
    private String medicineImagePath;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = MEDICINE)
    private Set<MedicineProductEntity> medicineProductEntities;
}
