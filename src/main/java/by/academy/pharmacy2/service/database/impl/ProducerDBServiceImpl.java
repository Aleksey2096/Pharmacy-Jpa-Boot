package by.academy.pharmacy2.service.database.impl;

import by.academy.pharmacy2.dto.ProducerDTO;
import by.academy.pharmacy2.entity.PaginationObject;
import by.academy.pharmacy2.entity.ProducerEntity;
import by.academy.pharmacy2.repository.ProducerRepository;
import by.academy.pharmacy2.service.database.BaseDBService;
import by.academy.pharmacy2.service.database.ProducerDBService;
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
public class ProducerDBServiceImpl extends BaseDBService<ProducerEntity, ProducerDTO, Long>
        implements ProducerDBService {
    private final ProducerRepository producerRepository;

    @Override
    public JpaRepository<ProducerEntity, Long> getRepository() {
        return producerRepository;
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationObject<ProducerDTO> readPaginated(final int currentPage,
                                                       final int recordsPerPage,
                                                       final String orderField,
                                                       final String orderType,
                                                       final String searchValue) {
        Specification<ProducerEntity> spec = Optional.ofNullable(searchValue)
                .filter(StringUtils::isNotBlank)
                .map(x -> createSearchSpecification(x, SpecificationFields.PRODUCER.getFields()))
                .orElse(null);
        return readPaginated(currentPage, recordsPerPage, orderField, orderType, spec);
    }
}
