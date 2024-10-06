package uz.optimit.railway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.LevelCrossingDto;
import uz.optimit.railway.service.LevelCrossingService;

import java.util.UUID;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/level-crossing")
public class LevelCrossingController {

    private final LevelCrossingService service;

    @PostMapping
    public ResponseEntity<?> create(@RequestBody LevelCrossingDto levelCrossingDto) {
        ApiResponse apiResponse = service.create(levelCrossingDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@PathVariable UUID id, @RequestBody LevelCrossingDto levelCrossingDto) {
        ApiResponse apiResponse = service.update(id, levelCrossingDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping
    public ResponseEntity<?> getAll() {
        ApiResponse apiResponse = service.getAll();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        ApiResponse apiResponse = service.getById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/get-by-plot/{plotId}")
    public ResponseEntity<?> getByPlotId(@PathVariable UUID plotId) {
        ApiResponse apiResponse = service.getByPlotId(plotId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
