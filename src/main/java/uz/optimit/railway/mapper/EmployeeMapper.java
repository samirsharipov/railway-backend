package uz.optimit.railway.mapper;

import uz.optimit.railway.payload.EmployeeDto;
import uz.optimit.railway.entity.Employee;
import uz.optimit.railway.entity.Enterprise;
import uz.optimit.railway.entity.User;

public class EmployeeMapper {

    public static Employee toEntity(Employee employee, EmployeeDto dto, Enterprise enterprise, User user) {
        employee.setFio(dto.getFirstName() + " " + dto.getLastName());
        employee.setEnterprise(enterprise);
        employee.setJshshir(dto.getJshshir());
        employee.setRole(dto.getPosition());
        employee.setUser(user);
        return employee;
    }

    public static EmployeeDto toDto(Employee employee) {
        EmployeeDto dto = new EmployeeDto();
        dto.setFirstName(employee.getUser().getFirstName());
        dto.setLastName(employee.getUser().getLastName());
        dto.setPassword(employee.getUser().getPassword());
        dto.setRoleId(employee.getUser().getRole().getId());
        dto.setEnterpriseId(employee.getEnterprise().getId());
        dto.setJshshir(employee.getJshshir());
        dto.setPosition(employee.getRole());
        return dto;
    }
}
