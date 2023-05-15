package by.academy.pharmacy2.service.database.impl;

import by.academy.pharmacy2.dto.PrescriptionRequestDTO;
import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.dto.UserDtoDetails;
import by.academy.pharmacy2.entity.PaginationObject;
import by.academy.pharmacy2.entity.PrescriptionRequestEntity;
import by.academy.pharmacy2.entity.PrescriptionRequestStatus;
import by.academy.pharmacy2.entity.UserEntity;
import by.academy.pharmacy2.repository.PrescriptionRequestRepository;
import by.academy.pharmacy2.service.database.BaseDBService;
import by.academy.pharmacy2.service.database.PrescriptionRequestDBService;
import by.academy.pharmacy2.service.database.SpecificationFields;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

import static by.academy.pharmacy2.entity.Constant.PRESCRIPTION_REQUEST_STATUS;
import static by.academy.pharmacy2.entity.Constant.USER;

@Service
@RequiredArgsConstructor
public class PrescriptionRequestDBServiceImpl
        extends BaseDBService<PrescriptionRequestEntity, PrescriptionRequestDTO, Long>
        implements PrescriptionRequestDBService {
    private final PrescriptionRequestRepository prescriptionRequestRepository;

    @Override
    public JpaRepository<PrescriptionRequestEntity, Long> getRepository() {
        return prescriptionRequestRepository;
    }

    @Override
    @Transactional
    public PrescriptionRequestDTO create(final PrescriptionRequestDTO dto) {

        dto.setUploadDateTime(LocalDateTime.now());
        dto.setPrescriptionRequestStatus(PrescriptionRequestStatus.UNPROCESSED);
        UserDTO userDTO = ((UserDtoDetails) SecurityContextHolder.getContext().getAuthentication()
                .getPrincipal()).userDTO();
        dto.setUser(userDTO);

        return super.create(dto);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationObject<PrescriptionRequestDTO> readPaginated(final int currentPage,
                                                                  final int recordsPerPage,
                                                                  final String orderField,
                                                                  final String orderType,
                                                                  final String searchValue) {
        Specification<PrescriptionRequestEntity> spec = !searchValue.isBlank()
                ? createSearchSpecification(searchValue,
                SpecificationFields.PRESCRIPTION_REQUEST.getFields())
                : null;
        return readPaginated(currentPage, recordsPerPage, orderField, orderType, spec);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationObject<PrescriptionRequestDTO> readPaginatedByStatus(final int currentPage,
                                                                          final int recordsPerPage,
                                                                          final String orderField,
                                                                          final String orderType,
                                                                          final String searchValue,
                                                                          final PrescriptionRequestStatus status) {
        Specification<PrescriptionRequestEntity> statusSpec = createSpecification(
                PRESCRIPTION_REQUEST_STATUS, status);
        Specification<PrescriptionRequestEntity> spec = !searchValue.isBlank()
                ? createSearchSpecification(searchValue,
                SpecificationFields.PRESCRIPTION_REQUEST.getFields()).and(statusSpec)
                : statusSpec;
        return readPaginated(currentPage, recordsPerPage, orderField, orderType, spec);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationObject<PrescriptionRequestDTO> readPaginatedByUser(final int currentPage,
                                                                        final int recordsPerPage,
                                                                        final String orderField,
                                                                        final String orderType,
                                                                        final String searchValue,
                                                                        final UserDTO userDTO) {
        Specification<PrescriptionRequestEntity> userSpec = createSpecification(USER,
                getModelMapper().map(userDTO, UserEntity.class));
        Specification<PrescriptionRequestEntity> spec = !searchValue.isBlank()
                ? createSearchSpecification(searchValue,
                SpecificationFields.PRESCRIPTION_REQUEST.getFields()).and(userSpec)
                : userSpec;
        return readPaginated(currentPage, recordsPerPage, orderField, orderType, spec);
    }
}
