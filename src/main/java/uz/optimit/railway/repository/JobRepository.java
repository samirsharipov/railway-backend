package uz.optimit.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.optimit.railway.entity.Job;

import java.sql.Timestamp;
import java.util.List;
import java.util.UUID;

public interface JobRepository extends JpaRepository<Job, UUID> {
    // Kunduzi bajariladigan ishlarni olish
    List<Job> findAllByYearJobIsFalseAndStartTimeBetweenOrderByCreatedAtDesc( java.sql.Timestamp startTime, java.sql.Timestamp endTime);

    // Yillik bajariladigan ishlarni olish
    List<Job> findAllByYearJobIsTrueAndStartTimeBetweenOrderByCreatedAtDesc( java.sql.Timestamp startTime, java.sql.Timestamp endTime);

    List<Job> findAllByYearJobIsFalseAndDoneIsNullAndPausedIsTrueAndStartTimeBetweenOrderByCreatedAtDesc( Timestamp startTime, Timestamp endTime);

    List<Job> findAllByYearJobIsTrueAndDoneIsNullAndPausedIsTrueAndStartTimeBetweenOrderByCreatedAtDesc( Timestamp startTime, Timestamp endTime);

    List<Job> findAllByYearJobIsFalseAndDoneIsNotNullAndDoneAndStartTimeBetweenOrderByCreatedAtDesc( boolean done, Timestamp startTime, Timestamp endTime);

    List<Job> findAllByYearJobIsTrueAndDoneIsNotNullAndDoneAndStartTimeBetweenOrderByCreatedAtDesc( boolean done, Timestamp startTime, Timestamp endTime);
}