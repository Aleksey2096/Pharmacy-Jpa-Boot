package by.academy.pharmacy2.dto;

import by.academy.pharmacy2.entity.PrescriptionRequestStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import lombok.ToString;

import java.time.LocalDateTime;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public final class PrescriptionRequestDTO {
    /**
     * Contains identification number of prescription request.
     */
    private Long id;
    /**
     * Contains path to prescription scan.
     */
    private String prescriptionScanPath;
    /**
     * Contains time and date of prescription scan uploading.
     */
    private LocalDateTime uploadDateTime;
    /**
     * Contains status of prescription request.
     */
    private PrescriptionRequestStatus prescriptionRequestStatus;
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private UserDTO user;
}
