package by.academy.pharmacy2.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.MapsId;
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
import java.math.BigDecimal;
import java.util.Date;

import static by.academy.pharmacy2.entity.Constant.DATE_OF_BIRTH_DB;
import static by.academy.pharmacy2.entity.Constant.HEALTH_CARE_CARD_NUMBER_DB;
import static by.academy.pharmacy2.entity.Constant.PAYMENT_CARD_NUMBER_DB;
import static by.academy.pharmacy2.entity.Constant.PERSONAL_ACCOUNT_DB;
import static by.academy.pharmacy2.entity.Constant.PERSONAL_INFO;
import static by.academy.pharmacy2.entity.Constant.PERSONAL_INFO_DB;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PERSONAL_INFO_DB)
public class PersonalInfoEntity implements Serializable {
    /**
     * Contains health care card number of personal info.
     */
    @Id
    @Column(name = HEALTH_CARE_CARD_NUMBER_DB)
    private Long healthCareCardNumber;
    /**
     * Contains surname of the user.
     */
    private String surname;
    /**
     * Contains name of the user.
     */
    private String name;
    /**
     * Contains users' birthdate.
     */
    @Column(name = DATE_OF_BIRTH_DB)
    private Date birthDate;
    /**
     * Contains users' address.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = HEALTH_CARE_CARD_NUMBER_DB)
    private AddressEntity address;
    /**
     * Contains users' phone number.
     */
    private String phone;
    /**
     * Contains users' email address.
     */
    private String email;
    /**
     * Contains position of the pharmacist.
     */
    private String position;
    /**
     * Contains clients' personal account value.
     */
    @Column(name = PERSONAL_ACCOUNT_DB)
    private BigDecimal personalAccount;
    /**
     * Contains number of clients' payment card.
     */
    @Column(name = PAYMENT_CARD_NUMBER_DB)
    private Long paymentCardNumber;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(mappedBy = PERSONAL_INFO)
    @PrimaryKeyJoinColumn
    private UserEntity userEntity;
}
