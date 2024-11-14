package uz.optimit.railway.controller;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.EmployeeDto;
import uz.optimit.railway.service.AttachmentService;
import uz.optimit.railway.service.EmployeeService;

import java.util.List;
import java.util.UUID;

@RestController
@RequestMapping("/api/employee")
@RequiredArgsConstructor
public class EmployeeController {

    private final EmployeeService employeeService;

    @SneakyThrows
    @PostMapping
    public ResponseEntity<ApiResponse> create(@RequestBody EmployeeDto employeeDto, @RequestParam("file") MultipartFile file) {
        ApiResponse apiResponse = employeeService.create(employeeDto ,file);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.CREATED : HttpStatus.CONFLICT).body(apiResponse);
    }

    @SneakyThrows
    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse> update(@PathVariable UUID id, @RequestBody EmployeeDto employeeDto,@RequestParam("file") MultipartFile file) {
        ApiResponse apiResponse = employeeService.update(id, employeeDto, file);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.CONFLICT).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<ApiResponse> getAll() {
        ApiResponse apiResponse = employeeService.getAll();
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable UUID id) {
        ApiResponse apiResponse = employeeService.getById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }

    @GetMapping("/get-by-enterpriseId/{enterpriseId}")
    public ResponseEntity<ApiResponse> getByEnterpriseId(@PathVariable UUID enterpriseId) {
        ApiResponse apiResponse = employeeService.getByEnterpriseId(enterpriseId);
        return ResponseEntity.status(apiResponse.isSuccess() ? HttpStatus.OK : HttpStatus.NOT_FOUND).body(apiResponse);
    }

}
