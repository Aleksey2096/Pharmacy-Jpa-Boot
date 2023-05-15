package by.academy.pharmacy2.service.util;

import by.academy.pharmacy2.dto.MedicineDTO;
import by.academy.pharmacy2.dto.PrescriptionRequestDTO;
import by.academy.pharmacy2.dto.UserDTO;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import static by.academy.pharmacy2.entity.Constant.COLON;
import static by.academy.pharmacy2.entity.Constant.EMPTY;
import static by.academy.pharmacy2.entity.Constant.IMG_MEDICINE;
import static by.academy.pharmacy2.entity.Constant.IMG_PRESCRIPTION_SCAN;
import static by.academy.pharmacy2.entity.Constant.IMG_USER;
import static by.academy.pharmacy2.entity.Constant.JPG;
import static by.academy.pharmacy2.entity.Constant.STATIC_PATH;

public class RequestDataUtil {
    private RequestDataUtil() {
    }

    public static void saveUserImage(final UserDTO userDTO, final MultipartFile image)
            throws IOException {
        if (image != null && !image.isEmpty()) {
            String avatarImagePath = IMG_USER + userDTO.getHealthCareCardNumber() + JPG;
            userDTO.setAvatarImagePath(avatarImagePath);
            Files.write(Paths.get(new File(STATIC_PATH + avatarImagePath).getAbsolutePath()),
                    image.getBytes());
        }
    }

    public static void saveMedicineImage(final MedicineDTO medicineDTO, final MultipartFile image)
            throws IOException {
        if (image != null && !image.isEmpty()) {
            String medicineImagePath = IMG_MEDICINE + medicineDTO.getTitle() + JPG;
            medicineDTO.setMedicineImagePath(medicineImagePath);
            Files.write(Paths.get(new File(STATIC_PATH + medicineImagePath).getAbsolutePath()),
                    image.getBytes());
        }
    }

    public static void savePrescriptionScan(final PrescriptionRequestDTO prescriptionRequestDTO,
                                            final MultipartFile image) throws IOException {
        if (image != null && !image.isEmpty()) {
            String prescriptionScanPath = IMG_PRESCRIPTION_SCAN + LocalDateTime.now().toString()
                    .replace(COLON, EMPTY) + JPG;
            prescriptionRequestDTO.setPrescriptionScanPath(prescriptionScanPath);
            Files.write(Paths.get(new File(STATIC_PATH + prescriptionScanPath).getAbsolutePath()),
                    image.getBytes());
        }
    }
}
