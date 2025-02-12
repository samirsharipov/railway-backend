package uz.optimit.railway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.optimit.railway.anotations.CheckPermission;
import uz.optimit.railway.payload.*;
import uz.optimit.railway.service.JobService;

import java.sql.Timestamp;
import java.util.UUID;

@RestController
@RequestMapping("/api/job")
@RequiredArgsConstructor
public class JobController {

    private final JobService jobService;

    @CheckPermission("ADD_JOB")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody JobDto dto) {
        ApiResponse apiResponse = jobService.create(dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/getAll")
    public ResponseEntity<ApiResponse> getAll(@RequestParam boolean daily,
                                              @RequestParam String status,
                                              @RequestParam Timestamp date) {
        ApiResponse apiResponse = jobService.getAll(daily,status,date);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_JOB")
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable UUID id) {
        ApiResponse apiResponse = jobService.getById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("EDIT_JOB")
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @RequestBody JobDto dto) {
        ApiResponse apiResponse = jobService.edit(id, dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("CONFIRM_JOB")
    @PutMapping("/confirm/{id}")
    public ResponseEntity<?> confirm(@PathVariable UUID id,@RequestBody JobConfirmDto dto) {
        ApiResponse apiResponse = jobService.confirm(id,dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("DONE_JOB")
    @PutMapping("/done/{id}")
    public ResponseEntity<?> done(@PathVariable UUID id,@RequestBody JobDoneDto dto) {
        ApiResponse apiResponse = jobService.done(id,dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("PAUSE_JOB")
    @PutMapping("/pause/{id}")
    public ResponseEntity<?> pause(@PathVariable UUID id,@RequestBody JobPauseDto dto) {
        ApiResponse apiResponse = jobService.pause(id,dto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("DELETE_JOB")
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = jobService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}