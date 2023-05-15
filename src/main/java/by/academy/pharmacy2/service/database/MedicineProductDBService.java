package by.academy.pharmacy2.service.database;

import by.academy.pharmacy2.dto.MedicineProductDTO;
import by.academy.pharmacy2.entity.PaginationObject;

import java.util.List;

public interface MedicineProductDBService extends DBService<MedicineProductDTO, Long> {
	PaginationObject<MedicineProductDTO> readPaginated(int currentPage, int recordsPerPage,
													   String orderField, String orderType,
													   String searchValue);

	PaginationObject<MedicineProductDTO> readPaginatedByMedicine(int currentPage,
																 int recordsPerPage,
																 String orderField,
																 String orderType,
																 String searchValue,
																 Long medicineId);

	List<MedicineProductDTO> getCart(long healthCareCardNumber);
}
