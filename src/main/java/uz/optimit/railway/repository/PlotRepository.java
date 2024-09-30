package uz.optimit.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.optimit.railway.entity.Plot;

import java.util.List;
import java.util.UUID;

public interface PlotRepository extends JpaRepository<Plot, UUID> {
    List<Plot> findAllByEnterpriseId(UUID enterpriseId);
}
