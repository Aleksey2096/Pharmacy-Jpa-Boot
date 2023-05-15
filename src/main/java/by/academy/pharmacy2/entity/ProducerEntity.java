package by.academy.pharmacy2.entity;

import by.academy.pharmacy2.service.util.CountryConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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

import static by.academy.pharmacy2.entity.Constant.COMPANY_NAME_DB;
import static by.academy.pharmacy2.entity.Constant.COUNTRY_CODE_DB;
import static by.academy.pharmacy2.entity.Constant.CREATION_DATE_DB;
import static by.academy.pharmacy2.entity.Constant.PRODUCER;
import static by.academy.pharmacy2.entity.Constant.PRODUCERS;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PRODUCERS)
public class ProducerEntity implements Serializable {
    /**
     * Contains identification number of producer.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Contains name of the company.
     */
    @Column(name = COMPANY_NAME_DB)
    private String companyName;
    /**
     * Contains information about country where medicine was produced.
     */
    @Column(name = COUNTRY_CODE_DB)
    @Convert(converter = CountryConverter.class)
    private Country country;
    /**
     * Contains date when company was created.
     */
    @Column(name = CREATION_DATE_DB)
    private Date creationDate;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = PRODUCER)
    private Set<MedicineEntity> medicineEntities;
}
