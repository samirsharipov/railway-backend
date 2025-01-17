package uz.optimit.railway.payload;

import lombok.Data;
import uz.optimit.railway.entity.Employee;
import uz.optimit.railway.entity.User;

@Data
public class UserEmployeeDto {
    private User user;
    private Employee employee;
}
