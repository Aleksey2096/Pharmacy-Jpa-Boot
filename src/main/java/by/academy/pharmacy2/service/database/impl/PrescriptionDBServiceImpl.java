package by.academy.pharmacy2.service.database.impl;

import by.academy.pharmacy2.dto.PrescriptionDTO;
import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.entity.PaginationObject;
import by.academy.pharmacy2.entity.PrescriptionEntity;
import by.academy.pharmacy2.entity.UserEntity;
import by.academy.pharmacy2.repository.PrescriptionRepository;
import by.academy.pharmacy2.service.database.BaseDBService;
import by.academy.pharmacy2.service.database.PrescriptionDBService;
import by.academy.pharmacy2.service.database.SpecificationFields;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import static by.academy.pharmacy2.entity.Constant.USER;

@Service
@RequiredArgsConstructor
public class PrescriptionDBServiceImpl extends
        BaseDBService<PrescriptionEntity, PrescriptionDTO, Long> implements PrescriptionDBService {
    private final PrescriptionRepository prescriptionRepository;

    @Override
    public JpaRepository<PrescriptionEntity, Long> getRepository() {
        return prescriptionRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationObject<PrescriptionDTO> readPaginatedByUser(final int currentPage,
                                                                 final int recordsPerPage,
                                                                 final String orderField,
                                                                 final String orderType,
                                                                 final String searchValue,
                                                                 final UserDTO userDTO) {
        Specification<PrescriptionEntity> userSpec = createSpecification(USER,
                getModelMapper().map(userDTO, UserEntity.class));
        Specification<PrescriptionEntity> spec = Optional.ofNullable(searchValue)
                .filter(StringUtils::isNotBlank)
                .map(x -> createSearchSpecification(x,
                        SpecificationFields.PRESCRIPTION.getFields()).and(userSpec))
                .orElse(userSpec);
        return readPaginated(currentPage, recordsPerPage, orderField, orderType, spec);
    }

    @Override
    @Transactional(readOnly = true)
    public List<PrescriptionDTO> getPrescriptions(final long healthCareCardNumber) {
        return prescriptionRepository.findByUser_HealthCareCardNumber(healthCareCardNumber).stream()
                .map(x -> getModelMapper().map(x, PrescriptionDTO.class))
                .collect(Collectors.toList());
    }
}
