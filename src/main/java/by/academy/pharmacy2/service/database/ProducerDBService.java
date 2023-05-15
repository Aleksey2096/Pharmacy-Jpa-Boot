package by.academy.pharmacy2.service.database;

import by.academy.pharmacy2.dto.ProducerDTO;
import by.academy.pharmacy2.entity.PaginationObject;

public interface ProducerDBService extends DBService<ProducerDTO, Long> {
    PaginationObject<ProducerDTO> readPaginated(int currentPage, int recordsPerPage,
                                                String orderField, String orderType,
                                                String searchValue);
}
