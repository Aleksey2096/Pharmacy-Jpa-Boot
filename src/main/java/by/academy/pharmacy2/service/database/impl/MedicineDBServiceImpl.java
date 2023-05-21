package by.academy.pharmacy2.service.database.impl;

import by.academy.pharmacy2.dto.MedicineDTO;
import by.academy.pharmacy2.entity.InfoLog;
import by.academy.pharmacy2.entity.MedicineEntity;
import by.academy.pharmacy2.entity.PaginationObject;
import by.academy.pharmacy2.repository.MedicineRepository;
import by.academy.pharmacy2.service.database.BaseDBService;
import by.academy.pharmacy2.service.database.MedicineDBService;
import by.academy.pharmacy2.service.database.SpecificationFields;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MedicineDBServiceImpl extends BaseDBService<MedicineEntity, MedicineDTO, Long>
        implements MedicineDBService {
    private final MedicineRepository medicineRepository;

    @Override
    public JpaRepository<MedicineEntity, Long> getRepository() {
        return medicineRepository;
    }

    @InfoLog
    @Override
    @Transactional(readOnly = true)
    public PaginationObject<MedicineDTO> readPaginated(final int currentPage,
                                                       final int recordsPerPage,
                                                       final String orderField,
                                                       final String orderType,
                                                       final String searchValue) {
        Specification<MedicineEntity> spec = Optional.ofNullable(searchValue)
                .filter(StringUtils::isNotBlank)
                .map(x -> createSearchSpecification(x, SpecificationFields.MEDICINE.getFields()))
                .orElse(null);
        return readPaginated(currentPage, recordsPerPage, orderField, orderType, spec);
    }
}
