package by.academy.pharmacy2.service.database;

import by.academy.pharmacy2.dto.PrescriptionDTO;
import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.entity.PaginationObject;

import java.util.List;

public interface PrescriptionDBService extends DBService<PrescriptionDTO, Long> {
	PaginationObject<PrescriptionDTO> readPaginatedByUser(int currentPage, int recordsPerPage,
														  String orderField, String orderType,
														  String searchValue, UserDTO userDTO);

	List<PrescriptionDTO> getPrescriptions(long healthCareCardNumber);
}
