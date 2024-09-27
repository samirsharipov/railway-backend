package uz.optimit.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.optimit.railway.entity.Job;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
    List<Job> findAllByOrderByCreatedAtDesc();
}
