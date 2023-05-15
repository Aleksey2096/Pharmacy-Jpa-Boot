package by.academy.pharmacy2.service.database;

import by.academy.pharmacy2.dto.OrderDTO;
import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.entity.PaginationObject;

public interface OrderDBService extends DBService<OrderDTO, Long> {
    PaginationObject<OrderDTO> readPaginated(int currentPage, int recordsPerPage, String orderField,
                                             String orderType, String searchValue);

    PaginationObject<OrderDTO> readPaginatedByUser(int currentPage, int recordsPerPage,
                                                   String orderField, String orderType,
                                                   String searchValue, UserDTO userDTO);
}
