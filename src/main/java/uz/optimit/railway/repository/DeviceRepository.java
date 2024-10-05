package uz.optimit.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.optimit.railway.entity.Device;

import java.util.List;
import java.util.UUID;

public interface DeviceRepository extends JpaRepository<Device, UUID> {

    List<Device> findAllByIsStationIsFalse();

    List<Device> findAllByIsStationIsTrue();

    List<Device> findAllByStationId(UUID stationId);

    List<Device> findAllByLevelCrossingId(UUID levelCrossingId);
}
