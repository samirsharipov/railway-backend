package uz.optimit.railway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.optimit.railway.entity.User;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AuthenticationResponseDto {
    private String token;
    private User user;
}
