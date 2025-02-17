package uz.optimit.railway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.optimit.railway.anotations.CheckPermission;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.RoleDto;
import uz.optimit.railway.service.RoleService;

import java.util.UUID;

@RestController
@RequestMapping("/api/role")
@RequiredArgsConstructor
public class RoleController {

    private final RoleService roleService;

    @CheckPermission("ADD_ROLE")
    @PostMapping("/create")
    public ResponseEntity<ApiResponse> create(@RequestBody RoleDto roleDto) {
        ApiResponse apiResponse = roleService.create(roleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @CheckPermission("EDIT_ROLE")
    @PutMapping("/edit/{id}")
    public ResponseEntity<ApiResponse> edit(@PathVariable UUID id, @RequestBody RoleDto roleDto) {
        ApiResponse apiResponse = roleService.edit(id, roleDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @CheckPermission("GET_ROLE")
    @GetMapping("/get-all-roles")
    public ResponseEntity<ApiResponse> getAllRoles() {
        ApiResponse apiResponse = roleService.getAllRoles();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @CheckPermission("GET_ROLE")
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<ApiResponse> getById(@PathVariable UUID id) {
        ApiResponse apiResponse = roleService.getById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }

    @CheckPermission("DELETE_ROLE")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<ApiResponse> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = roleService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 400).body(apiResponse);
    }
}