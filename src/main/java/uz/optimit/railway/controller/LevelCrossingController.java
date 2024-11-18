package uz.optimit.railway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.optimit.railway.anotations.CheckPermission;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.LevelCrossingDto;
import uz.optimit.railway.service.LevelCrossingService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/level-crossing")
public class LevelCrossingController {

    private final LevelCrossingService service;

    @CheckPermission("ADD_LEVEL_CROSSING")
    @PostMapping
    public ResponseEntity<?> create(@RequestBody LevelCrossingDto levelCrossingDto) {
        ApiResponse apiResponse = service.create(levelCrossingDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("EDIT_LEVEL_CROSSING")
    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody LevelCrossingDto levelCrossingDto) {
        ApiResponse apiResponse = service.update(id, levelCrossingDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_LEVEL_CROSSING")
    @GetMapping
    public ResponseEntity<?> getAll() {
        ApiResponse apiResponse = service.getAll();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_LEVEL_CROSSING")
    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        ApiResponse apiResponse = service.getById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_LEVEL_CROSSING")
    @GetMapping("/get-by-plot-id/{plotId}")
    public ResponseEntity<?> getByPlotId(@PathVariable UUID plotId) {
        ApiResponse apiResponse = service.getByPlotId(plotId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("DELETE_LEVEL_CROSSING")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = service.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
