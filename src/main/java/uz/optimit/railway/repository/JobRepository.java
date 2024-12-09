package uz.optimit.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.optimit.railway.entity.Job;

import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {

    // So'nggi yaratilgan joblarni olish
    List<Job> findAllByOrderByCreatedAtDesc();

    // Kunduzi bajariladigan ishlarni olish
    List<Job> findAllByYearJobIsFalseAndStation_IdAndStartTimeBetween(UUID stationId, java.sql.Timestamp startTime, java.sql.Timestamp endTime);

    // Yillik bajariladigan ishlarni olish
    List<Job> findAllByYearJobIsTrueAndStation_IdAndStartTimeBetween(UUID stationId, java.sql.Timestamp startTime, java.sql.Timestamp endTime);
}