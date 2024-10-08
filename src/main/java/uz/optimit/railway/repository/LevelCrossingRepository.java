package uz.optimit.railway.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.optimit.railway.entity.LevelCrossing;

import java.util.List;
import java.util.UUID;

public interface LevelCrossingRepository extends JpaRepository<LevelCrossing, UUID> {
    List<LevelCrossing> findAllByPlotIdAndDeletedIsFalse(UUID plotId);

    List<LevelCrossing> findAllByDeletedIsFalse();

    @Modifying
    @Transactional
    @Query("UPDATE LevelCrossing b SET b.deleted = true WHERE b.id = :id")
    void softDelete(UUID id);
}