package uz.optimit.railway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.optimit.railway.entity.Enterprise;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.EnterpriseDto;
import uz.optimit.railway.payload.MtuDto;
import uz.optimit.railway.service.EnterpriseService;
import uz.optimit.railway.service.MtuService;

import java.util.UUID;

@RestController
@RequestMapping("/api/enterprise")
@RequiredArgsConstructor
public class EnterpriseController {

    private final EnterpriseService service;


    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody EnterpriseDto enterpriseDto) {
        ApiResponse apiResponse = service.create(enterpriseDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @RequestBody EnterpriseDto enterpriseDto) {
        ApiResponse apiResponse = service.edit(id,enterpriseDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        ApiResponse apiResponse = service.getAll();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        ApiResponse apiResponse = service.getById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/get-by-mtu/{mtuId}")
    public ResponseEntity<?> getByMtu(@PathVariable UUID mtuId) {
        ApiResponse apiResponse = service.getByMtu(mtuId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
