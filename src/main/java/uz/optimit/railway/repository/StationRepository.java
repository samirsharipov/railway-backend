package uz.optimit.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.optimit.railway.entity.Plot;
import uz.optimit.railway.entity.Station;

import java.util.List;
import java.util.UUID;

public interface StationRepository extends JpaRepository<Station, UUID> {

    List<Station> findAllByPlotId(UUID plotId);
}
