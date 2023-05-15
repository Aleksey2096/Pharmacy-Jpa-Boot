package by.academy.pharmacy2.service.database;

import by.academy.pharmacy2.dto.PrescriptionRequestDTO;
import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.entity.PaginationObject;
import by.academy.pharmacy2.entity.PrescriptionRequestStatus;

public interface PrescriptionRequestDBService extends DBService<PrescriptionRequestDTO, Long> {
    PaginationObject<PrescriptionRequestDTO> readPaginated(int currentPage, int recordsPerPage,
                                                           String orderField, String orderType,
                                                           String searchValue);

    PaginationObject<PrescriptionRequestDTO> readPaginatedByStatus(int currentPage,
                                                                   int recordsPerPage,
                                                                   String orderField,
                                                                   String orderType,
                                                                   String searchValue,
                                                                   PrescriptionRequestStatus status);

    PaginationObject<PrescriptionRequestDTO> readPaginatedByUser(int currentPage,
                                                                 int recordsPerPage,
                                                                 String orderField,
                                                                 String orderType,
                                                                 String searchValue,
                                                                 UserDTO userDTO);
}
