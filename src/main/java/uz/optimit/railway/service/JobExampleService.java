package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.JobExample;
import uz.optimit.railway.payload.JobExampleRequest;
import uz.optimit.railway.payload.JobExampleResponse;
import uz.optimit.railway.repository.JobExampleRepository;
import uz.optimit.railway.payload.ApiResponse;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class JobExampleService {

    private final JobExampleRepository jobExampleRepository;

    // CREATE
    public ApiResponse create(JobExampleRequest request) {
        if (jobExampleRepository.findByName(request.getName()).isPresent()) {
            return new ApiResponse("Bunday nomdagi ish allaqachon mavjud", false);
        }

        JobExample jobExample = new JobExample();
        jobExample.setName(request.getName());
        jobExampleRepository.save(jobExample);
        return new ApiResponse("Muvaffaqiyatli qo'shildi", true);
    }

    // READ ALL
    public List<JobExampleResponse> getAll() {
        return jobExampleRepository.findAll().stream()
                .map(job -> new JobExampleResponse(job.getId(), job.getName()))
                .collect(Collectors.toList());
    }

    // READ ONE BY ID
    public ApiResponse getById(UUID id) {
        Optional<JobExample> optionalJob = jobExampleRepository.findById(id);
        if (optionalJob.isEmpty()) {
            return new ApiResponse("Bunday ID topilmadi", false);
        }
        JobExample jobExample = optionalJob.get();
        JobExampleResponse response = new JobExampleResponse(jobExample.getId(), jobExample.getName());
        return new ApiResponse("Muvaffaqiyatli topildi", true, response);
    }

    // UPDATE
    public ApiResponse update(UUID id, JobExampleRequest request) {
        Optional<JobExample> optionalJob = jobExampleRepository.findById(id);
        if (optionalJob.isEmpty()) {
            return new ApiResponse("Bunday ID topilmadi", false);
        }

        JobExample jobExample = optionalJob.get();
        jobExample.setName(request.getName());
        jobExampleRepository.save(jobExample);
        return new ApiResponse("Muvaffaqiyatli yangilandi", true);
    }

    // DELETE
    public ApiResponse delete(UUID id) {
        Optional<JobExample> optionalJob = jobExampleRepository.findById(id);
        if (optionalJob.isEmpty()) {
            return new ApiResponse("Bunday ID topilmadi", false);
        }
        optionalJob.get().setDeleted(true);
        jobExampleRepository.save(optionalJob.get());
        return new ApiResponse("Muvaffaqiyatli o'chirildi", true);
    }
}