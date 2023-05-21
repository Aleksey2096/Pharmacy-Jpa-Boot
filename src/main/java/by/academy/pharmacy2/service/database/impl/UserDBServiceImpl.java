package by.academy.pharmacy2.service.database.impl;

import by.academy.pharmacy2.dto.UserDTO;
import by.academy.pharmacy2.entity.MedicineProductEntity;
import by.academy.pharmacy2.entity.PaginationObject;
import by.academy.pharmacy2.entity.UserEntity;
import by.academy.pharmacy2.repository.MedicineProductRepository;
import by.academy.pharmacy2.repository.UserRepository;
import by.academy.pharmacy2.service.database.BaseDBService;
import by.academy.pharmacy2.service.database.SpecificationFields;
import by.academy.pharmacy2.service.database.UserDBService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.Objects;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserDBServiceImpl extends BaseDBService<UserEntity, UserDTO, Long>
        implements UserDBService {
    private final MedicineProductRepository medicineProductRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JpaRepository<UserEntity, Long> getRepository() {
        return userRepository;
    }

    @Override
    @Transactional
    public UserDTO create(final UserDTO dto) {

        dto.setJoinedDate(new Date());
        dto.getPersonalInfo().setHealthCareCardNumber(dto.getHealthCareCardNumber());
        dto.getPersonalInfo().getAddress().setHealthCareCardNumber(dto.getHealthCareCardNumber());

        dto.setPassword(passwordEncoder.encode(dto.getPassword()));

        return super.create(dto);
    }

    @Override
    @Transactional
    public void update(final UserDTO dto) {

        dto.getPersonalInfo().setHealthCareCardNumber(dto.getHealthCareCardNumber());
        dto.getPersonalInfo().getAddress().setHealthCareCardNumber(dto.getHealthCareCardNumber());

        UserEntity userEntity = getModelMapper().map(dto, UserEntity.class);
        UserEntity oldUserEntity = userRepository.findById(dto.getHealthCareCardNumber())
                .orElse(null);
        if (!Objects.requireNonNull(oldUserEntity).getPassword().equals(userEntity.getPassword())) {
            userEntity.setPassword(passwordEncoder.encode(userEntity.getPassword()));
        }
        userEntity.setCart(oldUserEntity.getCart());
        userRepository.save(userEntity);
    }

    @Override
    @Transactional(readOnly = true)
    public PaginationObject<UserDTO> readPaginated(final int currentPage, final int recordsPerPage,
                                                   final String orderField, final String orderType,
                                                   final String searchValue) {
        Specification<UserEntity> spec = Optional.ofNullable(searchValue)
                .filter(StringUtils::isNotBlank)
                .map(x -> createSearchSpecification(x, SpecificationFields.USER.getFields()))
                .orElse(null);
        return readPaginated(currentPage, recordsPerPage, orderField, orderType, spec);
    }

    @Override
    @Transactional
    public void addToCart(final long healthCareCardNumber, final long medicineProductId) {
        userRepository.findById(healthCareCardNumber).ifPresent(x -> {
            MedicineProductEntity medicineProductEntity = medicineProductRepository
                    .findById(medicineProductId).orElse(null);
            if (!x.getCart().contains(medicineProductEntity)) {
                x.getCart().add(medicineProductEntity);
                userRepository.save(x);
            }
        });
    }

    @Override
    @Transactional
    public void deleteFromCart(final long healthCareCardNumber, final long medicineProductId) {
        userRepository.findById(healthCareCardNumber).ifPresent(x -> {
            MedicineProductEntity medicineProductEntity = medicineProductRepository
                    .findById(medicineProductId).orElse(null);
            x.getCart().remove(medicineProductEntity);
            userRepository.save(x);
        });
    }
}
