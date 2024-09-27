package uz.optimit.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.optimit.railway.entity.Station;

import java.util.UUID;

public interface StationRepository extends JpaRepository<Station, UUID> {

}
