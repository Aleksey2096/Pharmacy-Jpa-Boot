package by.academy.pharmacy2.repository;

import by.academy.pharmacy2.entity.PrescriptionRequestEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface PrescriptionRequestRepository
        extends JpaRepository<PrescriptionRequestEntity, Long>,
        JpaSpecificationExecutor<PrescriptionRequestEntity> {
}
