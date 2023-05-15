package by.academy.pharmacy2.service.database;

import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.entity.PaginationObject;

public interface UserDBService extends DBService<UserDTO, Long> {
    PaginationObject<UserDTO> readPaginated(int currentPage, int recordsPerPage, String orderField,
                                            String orderType, String searchValue);

    void addToCart(long healthCareCardNumber, long medicineProductId);

    void deleteFromCart(long healthCareCardNumber, long medicineProductId);
}
