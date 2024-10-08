package uz.optimit.railway.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.optimit.railway.entity.Station;

import java.util.List;
import java.util.UUID;

public interface StationRepository extends JpaRepository<Station, UUID> {

    List<Station> findAllByPlotIdAndDeletedIsFalse(UUID plotId);
    List<Station> findAllByDeletedIsFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Station b SET b.deleted = true WHERE b.id = :id")
    void softDelete(UUID id);
}
