package by.academy.pharmacy2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.PrimaryKeyJoinColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

import static by.academy.pharmacy2.entity.Constant.ADDRESS;
import static by.academy.pharmacy2.entity.Constant.ADDRESSES;
import static by.academy.pharmacy2.entity.Constant.HEALTH_CARE_CARD_NUMBER_DB;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = ADDRESSES)
public class AddressEntity implements Serializable {
    @Id
    @Column(name = HEALTH_CARE_CARD_NUMBER_DB)
    private Long healthCareCardNumber;
    private Integer postcode;
    private String city;
    private String street;
    private Integer house;
    private Integer apartment;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = ADDRESS)
    @PrimaryKeyJoinColumn
    private PersonalInfoEntity personalInfoEntity;
}
