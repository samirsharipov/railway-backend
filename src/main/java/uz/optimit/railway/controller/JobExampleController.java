package uz.optimit.railway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.optimit.railway.payload.JobExampleRequest;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.service.JobExampleService;

import java.util.UUID;

@RestController
@RequestMapping("/api/job-example")
@RequiredArgsConstructor
public class JobExampleController {

    private final JobExampleService jobExampleService;

    // CREATE
    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody JobExampleRequest request) {
        ApiResponse response = jobExampleService.create(request);
        return ResponseEntity.status(response.isSuccess() ? 201 : 409).body(response);
    }

    // READ ALL
    @GetMapping
    public ResponseEntity<?> getAll() {
        return ResponseEntity.ok(jobExampleService.getAll());
    }

    // READ ONE
    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable UUID id) {
        return ResponseEntity.ok(jobExampleService.getById(id));
    }

    // UPDATE
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable UUID id, @RequestBody JobExampleRequest request) {
        return ResponseEntity.ok(jobExampleService.update(id, request));
    }

    // DELETE
    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        return ResponseEntity.ok(jobExampleService.delete(id));
    }
}
