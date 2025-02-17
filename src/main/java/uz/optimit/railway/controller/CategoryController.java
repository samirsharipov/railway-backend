package uz.optimit.railway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.optimit.railway.anotations.CheckPermission;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.CategoryDto;
import uz.optimit.railway.service.CategoryService;

import java.util.UUID;

@RestController
@RequestMapping("/api/category")
@RequiredArgsConstructor
public class CategoryController {
    private final CategoryService service;

    @CheckPermission("ADD_CATEGORY")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody CategoryDto categoryDto) {
        ApiResponse apiResponse = service.create(categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("EDIT_CATEGORY")
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @RequestBody CategoryDto categoryDto) {
        ApiResponse apiResponse = service.edit(id, categoryDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_CATEGORY")
    @GetMapping
    public ResponseEntity<?> getAll() {
        ApiResponse apiResponse = service.getAll();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_CATEGORY")
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getById(@PathVariable UUID id) {
        ApiResponse apiResponse = service.getById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("DELETE_CATEGORY")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = service.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_CATEGORY")
    @GetMapping("get-by-is-station/{isStation}/{isLevelCrossing}/{isPeregon}")
    public ResponseEntity<?> getByIsStation(@PathVariable boolean isStation,
                                            @PathVariable boolean isLevelCrossing,
                                            @PathVariable boolean isPeregon) {
        ApiResponse apiResponse = service.getByIsStation(isStation, isLevelCrossing, isPeregon);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
