package uz.optimit.railway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.DeviceDto;
import uz.optimit.railway.service.DeviceService;

import java.util.UUID;

@RestController
@RequestMapping("/api/device")
@RequiredArgsConstructor
public class DeviceController {

    private final DeviceService deviceService;

    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody DeviceDto deviceDto) {
        ApiResponse apiResponse = deviceService.create(deviceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @RequestBody DeviceDto deviceDto) {
        ApiResponse apiResponse = deviceService.edit(id, deviceDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/get-all")
    public ResponseEntity<?> getAll() {
        ApiResponse apiResponse = deviceService.getAll();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        ApiResponse apiResponse = deviceService.getById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/get-info-for-QR/{id}")
    public ResponseEntity<?> getInfoForQR(@PathVariable UUID id) {
        ApiResponse apiResponse = deviceService.getInfoForQR(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/get-all-info-actions/{id}")
    public ResponseEntity<?> getAllInfoActions(@PathVariable UUID id) {
        ApiResponse apiResponse = deviceService.getAllInfoActions(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/get-by-stationId/{stationId}")
    public ResponseEntity<?> getByStationId(@PathVariable UUID stationId) {
        ApiResponse apiResponse = deviceService.getByStationId(stationId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/get-by-levelCrossingId/{levelCrossingId}")
    public ResponseEntity<?> getByLevelCrossingId(@PathVariable UUID levelCrossingId) {
        ApiResponse apiResponse = deviceService.getByLevelCrossingId(levelCrossingId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/get-by-category/{categoryId}")
    public ResponseEntity<?> getByCategory(@PathVariable UUID categoryId) {
        ApiResponse apiResponse = deviceService.getByCategory(categoryId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/get-by-plot/{plotId}/{isStation}")
    public ResponseEntity<?> getByPlot(@PathVariable UUID plotId,
                                       @PathVariable boolean isStation) {
        ApiResponse apiResponse = deviceService.getByPlot(plotId,isStation);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = deviceService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("get-by-category-plot/{categoryId}/{plotId}")
    public ResponseEntity<?> getByCategoryPlot(@PathVariable UUID categoryId,
                                               @PathVariable UUID plotId,
                                               @RequestParam(required = false) UUID stationId) {
        ApiResponse apiResponse = deviceService.getByCategoryPlot(categoryId,plotId,stationId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }


}
