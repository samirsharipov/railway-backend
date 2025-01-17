package uz.optimit.railway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.optimit.railway.anotations.CheckPermission;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.service.PlotDto;
import uz.optimit.railway.service.PlotService;

import java.util.UUID;

@RestController
@RequestMapping("/api/plot")
@RequiredArgsConstructor
public class PlotController {

    private final PlotService service;


    @CheckPermission("ADD_PLOT")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PlotDto plotDto) {
        ApiResponse apiResponse = service.create(plotDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("EDIT_PLOT")
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @RequestBody PlotDto plotDto) {
        ApiResponse apiResponse = service.edit(id, plotDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_PLOT")
    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        ApiResponse apiResponse = service.getAll();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_PLOT")
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        ApiResponse apiResponse = service.getById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/get-by-enterprise/{enterpriseId}")
    public ResponseEntity<?> getByEnterprise(@PathVariable UUID enterpriseId) {
        ApiResponse apiResponse = service.getByEnterprise(enterpriseId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("DELETE_PLOT")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = service.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
