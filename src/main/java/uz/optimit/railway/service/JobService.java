package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Job;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.JobDto;
import uz.optimit.railway.repository.JobRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class JobService {
    private final JobRepository jobRepository;

    public ApiResponse getAll() {
        List<Job> jobs = jobRepository.findAllByOrderByCreatedAtDesc();
        List<JobDto> jobDtoList = new ArrayList<>();
        for (Job job : jobs) {
            JobDto jobDto = new JobDto();
            jobDto.setId(job.getId());
            jobDto.setName(job.getName());
            jobDto.setDescription(job.getDescription());
            jobDtoList.add(jobDto);
        }
        if (jobDtoList.isEmpty())
            return new ApiResponse("not found", false);

        return new ApiResponse("found", true, jobDtoList);
    }

    public ApiResponse create(JobDto dto) {
        Job job = new Job();
        job.setName(dto.getName());
        job.setDescription(dto.getDescription());
        jobRepository.save(job);
        return new ApiResponse("Job created", true);
    }

    public ApiResponse getById(UUID id) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isEmpty()) {
            return new ApiResponse("not found", false);
        }
        Job job = optionalJob.get();
        JobDto jobDto = new JobDto();
        jobDto.setId(job.getId());
        jobDto.setName(job.getName());
        jobDto.setDescription(job.getDescription());

        return new ApiResponse("found", true, jobDto);
    }

    public ApiResponse edit(UUID id, JobDto dto) {
        Optional<Job> optionalJob = jobRepository.findById(id);
        if (optionalJob.isEmpty()) {
            return new ApiResponse("not found", false);
        }
        Job job = optionalJob.get();
        job.setName(dto.getName());
        job.setDescription(dto.getDescription());
        jobRepository.save(job);
        return new ApiResponse("Job updated", true);
    }

    public ApiResponse delete(UUID id) {
        jobRepository.deleteById(id);
        return new ApiResponse("Job deleted", true);
    }
}
