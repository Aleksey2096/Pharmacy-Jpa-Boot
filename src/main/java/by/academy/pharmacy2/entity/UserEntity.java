package by.academy.pharmacy2.entity;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.MapsId;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.OrderColumn;
import jakarta.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import java.util.Set;

import static by.academy.pharmacy2.entity.Constant.AVATAR_IMAGE_PATH_DB;
import static by.academy.pharmacy2.entity.Constant.CARTS;
import static by.academy.pharmacy2.entity.Constant.CART_ORDER;
import static by.academy.pharmacy2.entity.Constant.DATE_JOINED_DB;
import static by.academy.pharmacy2.entity.Constant.HEALTH_CARE_CARD_NUMBER_DB;
import static by.academy.pharmacy2.entity.Constant.MEDICINE_PRODUCT_ID_DB;
import static by.academy.pharmacy2.entity.Constant.USER;
import static by.academy.pharmacy2.entity.Constant.USERS;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = USERS)
public class UserEntity implements Serializable {
    /**
     * Contains health care card number of user.
     */
    @Id
    @Column(name = HEALTH_CARE_CARD_NUMBER_DB)
    private Long healthCareCardNumber;
    /**
     * Contains users' login.
     */
    private String login;
    /**
     * Contains users' password.
     */
    private String password;
    /**
     * Contains client, pharmacist, administrator roles of users.
     */
    @Enumerated(EnumType.ORDINAL)
    private Role role;
    /**
     * Contains date when user created account.
     */
    @Column(name = DATE_JOINED_DB)
    private Date joinedDate;
    /**
     * Contains users' personal information.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToOne(cascade = CascadeType.ALL)
    @MapsId
    @JoinColumn(name = HEALTH_CARE_CARD_NUMBER_DB)
    private PersonalInfoEntity personalInfo;
    @Column(name = AVATAR_IMAGE_PATH_DB)
    private String avatarImagePath;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OrderColumn(name = CART_ORDER)
    @ManyToMany
    @JoinTable(name = CARTS, joinColumns = @JoinColumn(name = HEALTH_CARE_CARD_NUMBER_DB),
            inverseJoinColumns = @JoinColumn(name = MEDICINE_PRODUCT_ID_DB))
    private List<MedicineProductEntity> cart;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = USER)
    private Set<PrescriptionEntity> prescriptionEntities;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = USER)
    private Set<OrderEntity> orderEntities;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @OneToMany(mappedBy = USER)
    private Set<PrescriptionRequestEntity> prescriptionRequestEntities;
}
