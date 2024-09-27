package uz.optimit.railway.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.optimit.railway.anotations.CheckPermission;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.UserDto;
import uz.optimit.railway.service.UserService;

import java.util.UUID;

@RestController
@RequestMapping("/api/user")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @CheckPermission("ADD_USER")
    @PostMapping("/create")
    public ResponseEntity<?> create(@RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.create(userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("EDIT_USER")
    @PutMapping("/edit/{id}")
    public ResponseEntity<?> edit(@PathVariable UUID id, @RequestBody UserDto userDto) {
        ApiResponse apiResponse = userService.edit(id,userDto);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_USER")
    @GetMapping("/get-all-user")
    public ResponseEntity<?> getAllUser() {
        ApiResponse apiResponse = userService.getAllUser();
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("GET_USER")
    @GetMapping("/get-by-id/{id}")
    public ResponseEntity<?> getUserById(@PathVariable UUID id) {
        ApiResponse apiResponse = userService.getById(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @CheckPermission("DELETE_USER")
    @DeleteMapping("/delete-by-id/{id}")
    public ResponseEntity<?> delete(@PathVariable UUID id) {
        ApiResponse apiResponse = userService.delete(id);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

}
