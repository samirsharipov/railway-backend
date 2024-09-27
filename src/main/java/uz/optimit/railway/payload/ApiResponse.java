package uz.optimit.railway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ApiResponse {
    private String message;
    private boolean success = false;
    private Object data;

    public ApiResponse(String message) {
        this.message = message;
    }

    public ApiResponse(String message, boolean success) {
        this.message = message;
        this.success = success;
    }
}
