package uz.optimit.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.optimit.railway.entity.Job;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
    // Kunduzi bajariladigan ishlarni olish
    List<Job> findAllByYearJobIsFalseAndStation_IdAndStartTimeBetweenOrderByCreatedAtDesc(UUID stationId, java.sql.Timestamp startTime, java.sql.Timestamp endTime);

    // Yillik bajariladigan ishlarni olish
    List<Job> findAllByYearJobIsTrueAndStation_IdAndStartTimeBetweenOrderByCreatedAtDesc(UUID stationId, java.sql.Timestamp startTime, java.sql.Timestamp endTime);

    List<Job> findAllByYearJobIsFalseAndStation_IdAndDoneIsNullAndPausedIsTrueAndStartTimeBetweenOrderByCreatedAtDesc(UUID stationId, Timestamp startTime, Timestamp endTime);

    List<Job> findAllByYearJobIsTrueAndStation_IdAndDoneIsNullAndPausedIsTrueAndStartTimeBetweenOrderByCreatedAtDesc(UUID stationId, Timestamp startTime, Timestamp endTime);

    List<Job> findAllByYearJobIsFalseAndStation_IdAndDoneIsNotNullAndDoneAndStartTimeBetweenOrderByCreatedAtDesc(UUID stationId, boolean done, Timestamp startTime, Timestamp endTime);

    List<Job> findAllByYearJobIsTrueAndStation_IdAndDoneIsNotNullAndDoneAndStartTimeBetweenOrderByCreatedAtDesc(UUID stationId, boolean done, Timestamp startTime, Timestamp endTime);
}