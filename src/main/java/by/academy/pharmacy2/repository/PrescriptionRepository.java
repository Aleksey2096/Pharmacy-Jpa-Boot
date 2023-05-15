package by.academy.pharmacy2.repository;

import by.academy.pharmacy2.entity.PrescriptionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PrescriptionRepository extends JpaRepository<PrescriptionEntity, Long>,
        JpaSpecificationExecutor<PrescriptionEntity> {
    List<PrescriptionEntity> findByUser_HealthCareCardNumber(Long healthCareCardNumber);
}
