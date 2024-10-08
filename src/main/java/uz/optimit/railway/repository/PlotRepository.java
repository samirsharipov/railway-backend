package uz.optimit.railway.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.optimit.railway.entity.Plot;

import java.util.List;
import java.util.UUID;

public interface PlotRepository extends JpaRepository<Plot, UUID> {
    List<Plot> findAllByEnterpriseIdAndDeletedIsFalse(UUID enterpriseId);

    List<Plot> findAllByDeletedIsFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Plot b SET b.deleted = true WHERE b.id = :id")
    void softDelete(UUID id);
}
