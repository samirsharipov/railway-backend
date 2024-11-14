package uz.optimit.railway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EmployeeDto {
    private String firstName;

    private String lastName;

    private String password;

    private UUID roleId;

    private UUID enterpriseId;

    private String jshshir;

    private String position;
}
