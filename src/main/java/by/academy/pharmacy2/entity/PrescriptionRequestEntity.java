package by.academy.pharmacy2.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
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

import java.time.LocalDateTime;

import static by.academy.pharmacy2.entity.Constant.HEALTH_CARE_CARD_NUMBER_DB;
import static by.academy.pharmacy2.entity.Constant.PRESCRIPTION_REQUESTS_DB;
import static by.academy.pharmacy2.entity.Constant.PRESCRIPTION_REQUEST_STATUS_DB;
import static by.academy.pharmacy2.entity.Constant.PRESCRIPTION_SCAN_PATH_DB;
import static by.academy.pharmacy2.entity.Constant.UPLOAD_DATE_TIME_DB;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = PRESCRIPTION_REQUESTS_DB)
public class PrescriptionRequestEntity {
    /**
     * Contains identification number of prescription request.
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Contains path to prescription scan.
     */
    @Column(name = PRESCRIPTION_SCAN_PATH_DB)
    private String prescriptionScanPath;
    /**
     * Contains time and date of prescription scan uploading.
     */
    @Column(name = UPLOAD_DATE_TIME_DB)
    private LocalDateTime uploadDateTime;
    /**
     * Contains status of prescription request.
     */
    @Column(name = PRESCRIPTION_REQUEST_STATUS_DB)
    @Enumerated(EnumType.STRING)
    private PrescriptionRequestStatus prescriptionRequestStatus;
    /**
     * Contains user who made prescription request.
     */
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @ManyToOne
    @JoinColumn(name = HEALTH_CARE_CARD_NUMBER_DB,
            referencedColumnName = HEALTH_CARE_CARD_NUMBER_DB)
    private UserEntity user;
}
