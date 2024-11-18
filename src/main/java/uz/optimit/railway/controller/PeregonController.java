package uz.optimit.railway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.optimit.railway.anotations.CheckPermission;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.PeregonDto;
import uz.optimit.railway.service.PeregonService;

import java.util.UUID;

@RestController
@RequestMapping("/api/peregon")
@RequiredArgsConstructor
public class PeregonController {

    private final PeregonService service;

    @CheckPermission("ADD_PEREGON")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody PeregonDto peregonDto) {
        ApiResponse apiResponse = service.create(peregonDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("EDIT_PEREGON")
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @RequestBody PeregonDto peregonDto) {
        ApiResponse apiResponse = service.edit(id, peregonDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_PEREGON")
    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        ApiResponse apiResponse = service.getAll();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_PEREGON")
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        ApiResponse apiResponse = service.getById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_PEREGON")
    @GetMapping("/get-by-plot-id/{plotId}")
    public ResponseEntity<?> getByPlotId(@PathVariable UUID plotId) {
        ApiResponse apiResponse = service.getByPlotId(plotId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("DELETE_PEREGON")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = service.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
