package uz.optimit.railway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.StationDto;
import uz.optimit.railway.service.StationService;

import java.util.UUID;

@RestController
@RequestMapping("/api/station")
@RequiredArgsConstructor
public class StationController {

    private final StationService service;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody StationDto stationDto) {
        ApiResponse apiResponse = service.create(stationDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @RequestBody StationDto stationDto) {
        ApiResponse apiResponse = service.edit(id, stationDto);
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

    @GetMapping("/get-by-plot-id/{plotId}")
    public ResponseEntity<?> getByPlotId(@PathVariable UUID plotId) {
        ApiResponse apiResponse = service.getByPlotId(plotId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
