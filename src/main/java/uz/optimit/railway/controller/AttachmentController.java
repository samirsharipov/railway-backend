package uz.optimit.railway.controller;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.service.AttachmentService;

import java.io.IOException;
import java.util.UUID;

@RestController
@RequestMapping("/api/attachment")
@RequiredArgsConstructor
public class AttachmentController {

    private final AttachmentService service;

    @PostMapping("/upload")
    public ResponseEntity<?> upload(@RequestParam("file") MultipartFile file) throws IOException {
        ApiResponse apiResponse = service.upload(file);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }

    @GetMapping("/download/{id}")
    public ResponseEntity<?> download(@PathVariable UUID id, HttpServletResponse response) throws IOException {
        ApiResponse apiResponse = service.download(id, response);
        return ResponseEntity.status(apiResponse.isSuccess() ? 200 : 409).body(apiResponse);
    }
}
