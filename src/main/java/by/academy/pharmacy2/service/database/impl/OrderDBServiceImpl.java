package by.academy.pharmacy2.service.database.impl;

import by.academy.pharmacy2.dto.OrderDTO;
import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.entity.OrderEntity;
import by.academy.pharmacy2.entity.PaginationObject;
import by.academy.pharmacy2.entity.UserEntity;
import by.academy.pharmacy2.repository.OrderRepository;
import by.academy.pharmacy2.service.database.BaseDBService;
import by.academy.pharmacy2.service.database.OrderDBService;
import by.academy.pharmacy2.service.database.SpecificationFields;
import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import static by.academy.pharmacy2.entity.Constant.USER;

@Service
@RequiredArgsConstructor
public class OrderDBServiceImpl extends BaseDBService<OrderEntity, OrderDTO, Long>
        implements OrderDBService {
    private final OrderRepository orderRepository;

    @Override
    public JpaRepository<OrderEntity, Long> getRepository() {
        return orderRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationObject<OrderDTO> readPaginated(final int currentPage, final int recordsPerPage,
                                                    final String orderField, final String orderType,
                                                    final String searchValue) {
        Specification<OrderEntity> spec = !searchValue.isBlank()
                ? createSearchSpecification(searchValue, SpecificationFields.ORDER.getFields())
                : null;
        return readPaginated(currentPage, recordsPerPage, orderField, orderType, spec);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationObject<OrderDTO> readPaginatedByUser(final int currentPage,
                                                          final int recordsPerPage,
                                                          final String orderField,
                                                          final String orderType,
                                                          final String searchValue,
                                                          final UserDTO userDTO) {
        Specification<OrderEntity> userSpec = createSpecification(USER,
                getModelMapper().map(userDTO, UserEntity.class));
        Specification<OrderEntity> spec = !searchValue.isBlank()
                ? createSearchSpecification(searchValue, SpecificationFields.ORDER.getFields()).and(
                userSpec)
                : userSpec;
        return readPaginated(currentPage, recordsPerPage, orderField, orderType, spec);
    }
}
