package uz.optimit.railway.repository;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.optimit.railway.entity.Enterprise;

import java.util.List;
import java.util.UUID;

public interface EnterpriseRepository extends JpaRepository<Enterprise, UUID> {
    List<Enterprise> findAllByMtuIdAndDeletedIsFalse(UUID mtuId);

    List<Enterprise> findAllByDeletedIsFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Enterprise b SET b.deleted = true WHERE b.id = :id")
    void softDelete(UUID id);
}
