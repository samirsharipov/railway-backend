package uz.optimit.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.optimit.railway.entity.Peregon;

import java.util.List;
import java.util.UUID;

public interface PeregonRepository extends JpaRepository<Peregon, UUID> {
    List<Peregon> findAllByDeletedIsFalseOrderByCreatedAtDesc();

    List<Peregon> findAllByPlotIdAndDeletedIsFalseOrderByCreatedAtDesc(UUID plotId);
}
