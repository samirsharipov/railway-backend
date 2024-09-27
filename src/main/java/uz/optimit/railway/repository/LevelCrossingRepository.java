package uz.optimit.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.optimit.railway.entity.LevelCrossing;

import java.util.List;
import java.util.UUID;

public interface LevelCrossingRepository extends JpaRepository<LevelCrossing, UUID> {

    List<LevelCrossing> findAllByPlotId(UUID plotId);
}