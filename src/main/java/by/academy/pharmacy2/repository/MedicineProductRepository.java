package by.academy.pharmacy2.repository;

import by.academy.pharmacy2.entity.MedicineProductEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineProductRepository extends JpaRepository<MedicineProductEntity, Long>,
        JpaSpecificationExecutor<MedicineProductEntity> {
}
