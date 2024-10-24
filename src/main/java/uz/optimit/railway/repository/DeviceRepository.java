package uz.optimit.railway.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.optimit.railway.entity.Device;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {


    List<Device> findAllByStationIdAndDeletedIsFalse(UUID stationId);

    List<Device> findAllByLevelCrossingIdAndDeletedIsFalse(UUID levelCrossingId);

    List<Device> findAllByCategoryIdAndDeletedIsFalse(UUID categoryId);

    @Modifying
    @Transactional
    @Query("UPDATE Device b SET b.deleted = true WHERE b.id = :id")
    void softDelete(UUID id);

    List<Device> findAllByStation_PlotIdAndIsStationIsTrueAndDeletedIsFalse(UUID plotId);

    List<Device> findAllByStation_PlotIdAndIsStationIsFalseAndDeletedIsFalse(UUID plotId);

    List<Device> findAllByStation_IdAndStation_Plot_IdAndDeletedIsFalse(UUID stationId, UUID plotId);
}
