package by.academy.pharmacy2.service.database.impl;

import by.academy.pharmacy2.dto.MedicineProductDTO;
import by.academy.pharmacy2.entity.MedicineProductEntity;
import by.academy.pharmacy2.entity.PaginationObject;
import by.academy.pharmacy2.entity.UserEntity;
import by.academy.pharmacy2.repository.MedicineProductRepository;
import by.academy.pharmacy2.repository.MedicineRepository;
import by.academy.pharmacy2.repository.UserRepository;
import by.academy.pharmacy2.service.database.BaseDBService;
import by.academy.pharmacy2.service.database.MedicineProductDBService;
import by.academy.pharmacy2.service.database.SpecificationFields;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.academy.pharmacy2.entity.Constant.MEDICINE;

@Service
@RequiredArgsConstructor
public class MedicineProductDBServiceImpl
        extends BaseDBService<MedicineProductEntity, MedicineProductDTO, Long>
        implements MedicineProductDBService {
    private final MedicineRepository medicineRepository;
    private final MedicineProductRepository medicineProductRepository;
    private final UserRepository userRepository;

    @Override
    public JpaRepository<MedicineProductEntity, Long> getRepository() {
        return medicineProductRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationObject<MedicineProductDTO> readPaginated(final int currentPage,
                                                              final int recordsPerPage,
                                                              final String orderField,
                                                              final String orderType,
                                                              final String searchValue) {
        Specification<MedicineProductEntity> spec = Optional.ofNullable(searchValue)
                .filter(StringUtils::isNotBlank)
                .map(x -> createSearchSpecification(x,
                        SpecificationFields.MEDICINE_PRODUCT.getFields()))
                .orElse(null);
        return readPaginated(currentPage, recordsPerPage, orderField, orderType, spec);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationObject<MedicineProductDTO> readPaginatedByMedicine(final int currentPage,
                                                                        final int recordsPerPage,
                                                                        final String orderField,
                                                                        final String orderType,
                                                                        final String searchValue,
                                                                        final Long medicineId) {
        Specification<MedicineProductEntity> medicineSpec = createSpecification(MEDICINE,
                medicineRepository.findById(medicineId).orElse(null));
        Specification<MedicineProductEntity> spec = Optional.ofNullable(searchValue)
                .filter(StringUtils::isNotBlank)
                .map(x -> createSearchSpecification(x,
                        SpecificationFields.MEDICINE_PRODUCT.getFields()).and(medicineSpec))
                .orElse(medicineSpec);
        return readPaginated(currentPage, recordsPerPage, orderField, orderType, spec);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MedicineProductDTO> getCart(final long healthCareCardNumber) {
        UserEntity userEntity = userRepository.findById(healthCareCardNumber).orElse(null);
        return Objects.requireNonNull(userEntity).getCart().stream().filter(Objects::nonNull)
                .map(x -> getModelMapper().map(x, MedicineProductDTO.class))
                .collect(Collectors.toList());
    }
}
