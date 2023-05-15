package by.academy.pharmacy2.repository;

import by.academy.pharmacy2.entity.MedicineEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

@Repository
public interface MedicineRepository extends JpaRepository<MedicineEntity, Long>,
        JpaSpecificationExecutor<MedicineEntity> {
}
