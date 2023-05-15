package by.academy.pharmacy2.service.database;

import by.academy.pharmacy2.entity.PaginationObject;
import jakarta.persistence.criteria.Predicate;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.transaction.annotation.Transactional;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static by.academy.pharmacy2.entity.Constant.PERCENTAGE_VALUE;
import static by.academy.pharmacy2.entity.Constant.UNCHECKED;

public abstract class BaseDBService<E, D, I> implements DBService<D, I> {
    private ModelMapper modelMapper;

    public abstract JpaRepository<E, I> getRepository();

    public ModelMapper getModelMapper() {
        return modelMapper;
    }

    @Autowired
    public void setModelMapper(final ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
    }

    @Transactional
    @Override
    public D create(final D dto) {
        return modelMapper.map(getRepository().save(modelMapper.map(dto, getEntityClass())),
                getDtoClass());
    }

    @Transactional(readOnly = true)
    @Override
    public D readById(final I id) {
        return getRepository().findById(id).map(newE -> modelMapper.map(newE, getDtoClass()))
                .orElse(null);
    }

    // @PreAuthorize("hasRole('ROLE_ADMINISTRATOR')")
    @Transactional
    @Override
    public void update(final D dto) {
        getRepository().save(modelMapper.map(dto, getEntityClass()));
    }

    @Transactional
    @Override
    public void deleteById(final I id) {
        getRepository().deleteById(id);
    }

    @Transactional(readOnly = true)
    @Override
    public Set<D> readAll() {
        return getRepository().findAll().stream().map(x -> modelMapper.map(x, getDtoClass()))
                .collect(Collectors.toSet());
    }

    @SuppressWarnings(UNCHECKED)
    @Transactional(readOnly = true)
    public PaginationObject<D> readPaginated(final int currentPage, final int recordsPerPage,
                                             final String orderField, final String orderType,
                                             final Specification<E> spec) {
        Sort sort = createSort(orderField, orderType);
        Pageable pageable = PageRequest.of(currentPage - 1, recordsPerPage, sort);
        Page<E> page = ((JpaSpecificationExecutor<E>) getRepository()).findAll(spec, pageable);
        return new PaginationObject<>(currentPage, recordsPerPage, page.getTotalPages(),
                page.getContent().stream().map(x -> modelMapper.map(x, getDtoClass()))
                        .collect(Collectors.toList()));
    }

    public Specification<E> createSpecification(final String fieldName, final Object o) {
        return (root, cq, cb) -> cb.equal(root.get(fieldName), o);
    }

    public Specification<E> createSearchSpecification(final String searchValue,
                                                      final String... fieldNames) {
        return (root, cq, cb) -> {
            Predicate[] predicates = new Predicate[fieldNames.length];
            for (int i = 0; i < predicates.length; ++i) {
                predicates[i] = cb.like(root.get(fieldNames[i]).as(String.class),
                        String.format(PERCENTAGE_VALUE, searchValue));
            }
            return cb.or(predicates);
        };
    }

    private Sort createSort(final String orderField, final String orderType) {
        return Sort.Direction.ASC.name().equalsIgnoreCase(orderType)
                ? Sort.by(orderField).ascending()
                : Sort.by(orderField).descending();
    }

    @SuppressWarnings(UNCHECKED)
    private Class<E> getEntityClass() {
        return (Class<E>) Objects.requireNonNull(
                GenericTypeResolver.resolveTypeArguments(getClass(), BaseDBService.class))[0];
    }

    @SuppressWarnings(UNCHECKED)
    private Class<D> getDtoClass() {
        return (Class<D>) Objects.requireNonNull(
                GenericTypeResolver.resolveTypeArguments(getClass(), BaseDBService.class))[1];
    }
}
