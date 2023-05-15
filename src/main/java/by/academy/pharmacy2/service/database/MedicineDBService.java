package by.academy.pharmacy2.service.database;

import by.academy.pharmacy2.dto.MedicineDTO;
import by.academy.pharmacy2.entity.PaginationObject;

public interface MedicineDBService extends DBService<MedicineDTO, Long> {
    PaginationObject<MedicineDTO> readPaginated(int currentPage, int recordsPerPage,
                                                String orderField, String orderType,
                                                String searchValue);
}
