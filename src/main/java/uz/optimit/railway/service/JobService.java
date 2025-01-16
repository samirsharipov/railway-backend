package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Job;
import uz.optimit.railway.entity.Station;
import uz.optimit.railway.entity.User;
import uz.optimit.railway.payload.*;
import uz.optimit.railway.repository.JobRepository;
import uz.optimit.railway.repository.StationRepository;
import uz.optimit.railway.repository.UserRepository;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobService {

    private final JobRepository jobRepository;
    private final UserRepository userRepository;
    private final StationRepository stationRepository;

    /**
     * Yangi Job qo'shish
     */
    public ApiResponse create(JobDto dto) {
        // Station tekshiriladi
        Optional<Station> optionalStation = stationRepository.findById(dto.getStationId());
        if (optionalStation.isEmpty()) {
            return new ApiResponse("Station topilmadi", false);
        }

        // Ishni yaratilmoqda
        Job job = new Job();
        job.setName(dto.getName());
        job.setDescription(dto.getDescription());
        job.setCreatedBy(dto.getCreatedBy());
        job.setStartTime(dto.getStartTime());
        job.setDoneTime(null);
        job.setConfirmUser(null);
        job.setDoneOrPausedUser(null);
        job.setStation(optionalStation.get());
        job.setYearJob(dto.isYearJob());
        job.setDone(false);
        job.setPaused(false);

        jobRepository.save(job);
        return new ApiResponse("Ish muvaffaqiyatli qo'shildi", true);
    }

    /**
     * Kunduzi yoki yillik ishlarni olish
     */
    public ApiResponse getAll(UUID stationId, boolean daily, String status) {
        // Vaqtni hisoblaymiz (universal qism)
        TimeRange timeRange = daily ? getDailyTimeRange() : getYearlyTimeRange();
        List<Job> jobs = switch (status) {
            case "all" -> daily
                    ? jobRepository.findAllByYearJobIsFalseAndStation_IdAndStartTimeBetween(stationId, timeRange.startTime(), timeRange.endTime())
                    : jobRepository.findAllByYearJobIsTrueAndStation_IdAndStartTimeBetween(stationId, timeRange.startTime(), timeRange.endTime());
            case "rejected" -> daily
                    ? jobRepository.findAllByYearJobIsFalseAndStation_IdAndDoneIsNotNullAndDoneAndStartTimeBetween(stationId, false, timeRange.startTime(), timeRange.endTime())
                    : jobRepository.findAllByYearJobIsTrueAndStation_IdAndDoneIsNotNullAndDoneAndStartTimeBetween(stationId, false, timeRange.startTime(), timeRange.endTime());
            case "done" -> daily
                    ? jobRepository.findAllByYearJobIsFalseAndStation_IdAndDoneIsNotNullAndDoneAndStartTimeBetween(stationId, true, timeRange.startTime(), timeRange.endTime())
                    : jobRepository.findAllByYearJobIsTrueAndStation_IdAndDoneIsNotNullAndDoneAndStartTimeBetween(stationId, true, timeRange.startTime(), timeRange.endTime());
            case "paused" -> daily
                    ? jobRepository.findAllByYearJobIsFalseAndStation_IdAndDoneIsNullAndPausedIsTrueAndStartTimeBetween(stationId, timeRange.startTime(), timeRange.endTime())
                    : jobRepository.findAllByYearJobIsTrueAndStation_IdAndDoneIsNullAndPausedIsTrueAndStartTimeBetween(stationId, timeRange.startTime(), timeRange.endTime());
            default -> throw new IllegalArgumentException("Noto'g'ri status: " + status);
        };

        // Joblarni DTO'ga o'zgartirish
        List<JobGetDto> jobDtoList = jobs.stream().map(this::mapToGetDto).collect(Collectors.toList());
        return new ApiResponse("Muvaffaqiyatli topildi", true, jobDtoList);
    }

    /**
     * Kun boshidan kun oxirigacha bo'lgan vaqt oralig'ini olish
     */
    private TimeRange getDailyTimeRange() {
        LocalDateTime startOfDay = LocalDateTime.now().with(LocalTime.MIN);
        LocalDateTime endOfDay = LocalDateTime.now().with(LocalTime.MAX);
        return new TimeRange(Timestamp.valueOf(startOfDay), Timestamp.valueOf(endOfDay));
    }

    /**
     * Yil boshidan yil oxirigacha bo'lgan vaqt oralig'ini olish
     */
    private TimeRange getYearlyTimeRange() {
        LocalDateTime startOfYear = LocalDateTime.now().withDayOfYear(1).with(LocalTime.MIN);
        LocalDateTime endOfYear = LocalDateTime.now().withDayOfYear(1).plusYears(1).minusDays(1).with(LocalTime.MAX);
        return new TimeRange(Timestamp.valueOf(startOfYear), Timestamp.valueOf(endOfYear));
    }

    /**
     * Jobni ID bo'yicha olish
     */
    public ApiResponse getById(UUID id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isEmpty()) {
            return new ApiResponse("Job topilmadi", false);
        }
        JobGetDto jobDto = mapToGetDto(optionalJob.get());
        return new ApiResponse("Muvaffaqiyatli topildi", true, jobDto);
    }

    /**
     * Jobni ID bo'yicha yangilash
     */
    public ApiResponse edit(UUID id, JobDto dto) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isEmpty()) {
            return new ApiResponse("Job topilmadi", false);
        }

        Optional<Station> optionalStation = stationRepository.findById(dto.getStationId());
        if (optionalStation.isEmpty()) {
            return new ApiResponse("Station topilmadi", false);
        }

        Job job = optionalJob.get();
        job.setName(dto.getName());
        job.setDescription(dto.getDescription());
        job.setStartTime(dto.getStartTime());
        job.setDoneOrPausedUser(null);
        job.setStation(optionalStation.get());
        job.setYearJob(dto.isYearJob());
        job.setDone(dto.isDone());
        job.setPaused(dto.isPaused());

        jobRepository.save(job);
        return new ApiResponse("Ish muvaffaqiyatli yangilandi", true);
    }

    /**
     * Jobni ID bo'yicha o'chirish
     */
    public ApiResponse delete(UUID id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isEmpty()) {
            return new ApiResponse("Job topilmadi", false);
        }
        optionalJob.get().setDeleted(true);
        jobRepository.save(optionalJob.get());
        return new ApiResponse("Job muvaffaqiyatli o'chirildi", true);
    }

    private JobGetDto mapToGetDto(Job job) {
        JobGetDto dto = new JobGetDto();
        dto.setId(job.getId());
        dto.setCreatedAt(job.getCreatedAt());
        dto.setName(job.getName());
        dto.setDescription(job.getDescription());
        dto.setStartTime(job.getStartTime());
        dto.setDoneTime(job.getDoneTime());
        userRepository.findById(job.getCreatedBy()).ifPresent(user -> dto.setCreatedBy(user.getFirstName() + " " + user.getLastName()));
        dto.setStation(job.getStation().getName());
        dto.setDoneOrPausedUser(job.getDoneOrPausedUser() != null ? job.getDoneOrPausedUser().getFirstName() + " " + job.getDoneOrPausedUser().getLastName() : null);
        dto.setConfirmUser(job.getConfirmUser() != null ? job.getConfirmUser().getFirstName() + " " + job.getConfirmUser().getLastName() : null);
        dto.setYearJob(job.isYearJob());
        dto.setDone(job.isDone());
        dto.setPaused(job.isPaused());
        dto.setConfirm(job.isConfirm());
        return dto;
    }


    public ApiResponse confirm(UUID id, JobConfirmDto dto) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isEmpty()) {
            return new ApiResponse("Ish topilmadi", false);
        }
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isEmpty()) {
            return new ApiResponse("Hodim topilmadi", false);
        }

        Job job = optionalJob.get();
        if (job.isConfirm()) {
            return new ApiResponse("Ish alaqachon tasdiqlangan", false);
        }
        job.setConfirm(dto.isConfirmed());
        job.setConfirmUser(optionalUser.get());
        job.setDescription(dto.getDescription());
        jobRepository.save(job);
        return new ApiResponse("Saqlandi", true);
    }

    public ApiResponse done(UUID id, JobDoneDto dto) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isEmpty()) {
            return new ApiResponse("Ish topilmadi", false);
        }
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isEmpty()) {
            return new ApiResponse("Hodim topilmadi", false);
        }

        Job job = optionalJob.get();
        job.setDone(dto.isDone());
        job.setDoneOrPausedUser(optionalUser.get());
        jobRepository.save(job);
        return new ApiResponse("Saqlandi", true);
    }

    public ApiResponse pause(UUID id, JobPauseDto dto) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isEmpty()) {
            return new ApiResponse("Ish topilmadi", false);
        }
        Optional<User> optionalUser = userRepository.findById(dto.getUserId());
        if (optionalUser.isEmpty()) {
            return new ApiResponse("Hodim topilmadi", false);
        }

        Job job = optionalJob.get();
        job.setPaused(dto.isPause());
        job.setDoneOrPausedUser(optionalUser.get());
        job.setStartTime(dto.getStartTime());
        jobRepository.save(job);
        return new ApiResponse("Saqlandi", true);
    }

    /**
     * Vaqt oralig'ini saqlash uchun ichki sinf
     */
    private record TimeRange(Timestamp startTime, Timestamp endTime) {
    }
}