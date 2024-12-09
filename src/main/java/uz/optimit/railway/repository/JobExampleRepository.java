package uz.optimit.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.optimit.railway.entity.JobExample;

import java.util.Optional;
import java.util.UUID;

public interface JobExampleRepository extends JpaRepository<JobExample, UUID> {
    Optional<JobExample> findByName(String name);
}
