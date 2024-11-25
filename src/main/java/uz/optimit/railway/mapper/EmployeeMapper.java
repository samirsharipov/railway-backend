package uz.optimit.railway.mapper;

import uz.optimit.railway.entity.Station;
import uz.optimit.railway.payload.EmployeeDto;
import uz.optimit.railway.entity.Employee;
import uz.optimit.railway.entity.Enterprise;
import uz.optimit.railway.entity.User;

import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

public class EmployeeMapper {

    public static Employee toEntity(Employee employee, EmployeeDto dto, Enterprise enterprise, User user, List<Station> stationList) {
        employee.setFio(dto.getFirstName() + " " + dto.getLastName());
        employee.setEnterprise(enterprise);
        employee.setJshshir(dto.getJshshir());
        employee.setRole(dto.getPosition());
        employee.setUser(user);
        employee.setStation(stationList);
        return employee;
    }

    public static EmployeeDto toDto(Employee employee) {
        if (employee == null) {
            return null; // Agar employee null bo'lsa, null qaytarish
        }

        EmployeeDto dto = new EmployeeDto();

        // Null tekshiruvi bilan to'ldirish
        if (employee.getUser() != null) {
            dto.setId(employee.getId());
            dto.setFirstName(employee.getUser().getFirstName());
            dto.setLastName(employee.getUser().getLastName());
            dto.setPassword(employee.getUser().getPassword());
            if (employee.getUser().getRole() != null) {
                dto.setRoleId(employee.getUser().getRole().getId());
            }
        }

        if (employee.getEnterprise() != null) {
            dto.setEnterpriseId(employee.getEnterprise().getId());
        }

        dto.setJshshir(employee.getJshshir());
        dto.setPosition(employee.getRole());

        if (employee.getAttachment() != null) {
            dto.setAttachmentId(employee.getAttachment().getId());
        }

        // stationIdList ni to'ldirish
        if (employee.getStation() != null) {
            List<UUID> stationIds = employee.getStation().stream()
                    .map(Station::getId)  // Station modelidagi getId() metodini chaqiradi
                    .collect(Collectors.toList());
            dto.setStationIdList(stationIds);
        }

        return dto;
    }
}