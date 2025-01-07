package uz.optimit.railway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.optimit.railway.anotations.CheckPermission;
import uz.optimit.railway.payload.ActionDto;
import uz.optimit.railway.payload.ActionEditDto;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.CheckDto;
import uz.optimit.railway.service.ActionService;

import java.util.UUID;

@RestController
@RequestMapping("/api/action")
@RequiredArgsConstructor
public class ActionController {

    private final ActionService service;

    @CheckPermission("ADD_ACTION")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody ActionDto actionDto) {
        ApiResponse apiResponse = service.create(actionDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("EDIT_ACTION")
    @PutMapping("/edit-done/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @RequestBody ActionEditDto actionEditDto) {
        ApiResponse apiResponse = service.edit(id, actionEditDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_ACTION")
    @GetMapping("/get-by-user-done-is-false/{userId}")
    public ResponseEntity<?> getByUserDone(@PathVariable UUID userId) {
        ApiResponse apiResponse = service.getByUserDone(userId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_ACTION")
    @GetMapping("/get-by-user-done/{userId}")
    public ResponseEntity<?> getByUserDoneTrue(@PathVariable UUID userId) {
        ApiResponse apiResponse = service.getByUserDoneTrue(userId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @PostMapping("/check-user")
    public ResponseEntity<?> checkUser(@RequestBody CheckDto checkDto) {
        ApiResponse apiResponse = service.checkUser(checkDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("DELETE_ACTION")
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = service.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/action-get-filter/{deviceId}")
    public ResponseEntity<?> getFilter(@PathVariable UUID deviceId) {
        ApiResponse apiResponse = service.getFiler(deviceId);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
