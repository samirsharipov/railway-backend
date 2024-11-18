package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.*;
import uz.optimit.railway.entity.template.Identifiable;
import uz.optimit.railway.mapper.EmployeeMapper;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.EmployeeDto;
import uz.optimit.railway.repository.*;

import java.io.IOException;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class EmployeeService {

    private final EmployeeRepository repository;
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final EnterpriseRepository enterpriseRepository;
    private final PasswordEncoder passwordEncoder;
    private final StationRepository stationRepository;


    public ApiResponse create(EmployeeDto employeeDto) {

        Role role = findByIdOrThrow(roleRepository, employeeDto.getRoleId(), "Role");
        Enterprise enterprise = findByIdOrThrow(enterpriseRepository, employeeDto.getEnterpriseId(), "Enterprise");
        List<Station> station = findByIdsOrThrow(stationRepository, employeeDto.getStationIdList(), "Station");


        User user = new User();
        toUser(employeeDto, user, role);
        user = userRepository.save(user);

        if (employeeDto.getAttachmentId() != null) {
            Attachment attachment = new Attachment();
            attachment.setId(employeeDto.getAttachmentId());
        }

        Employee employee = new Employee();
        repository.save(EmployeeMapper.toEntity(employee, employeeDto, enterprise, user, station));
        return new ApiResponse("Employee created", true);
    }


    public ApiResponse update(UUID id, EmployeeDto employeeDto) throws IOException {
        Employee employee = findByIdOrThrow(repository, id, "Employee");
        Role role = findByIdOrThrow(roleRepository, employeeDto.getRoleId(), "Role");
        Enterprise enterprise = findByIdOrThrow(enterpriseRepository, employeeDto.getEnterpriseId(), "Enterprise");
        List<Station> station = findByIdsOrThrow(stationRepository, employeeDto.getStationIdList(), "Station");


        User user = employee.getUser();
        toUser(employeeDto, user, role);
        if (employeeDto.getAttachmentId() != null) {
            Attachment attachment = new Attachment();
            attachment.setId(employeeDto.getAttachmentId());
        }
        userRepository.save(user);


        repository.save(EmployeeMapper.toEntity(employee, employeeDto, enterprise, user, station));

        return new ApiResponse("Employee updated", true);
    }


    public ApiResponse getAll() {
        List<Employee> employees = repository.findAll();
        if (employees.isEmpty())
            return new ApiResponse("Employee not found", false);
        return new ApiResponse("Employee found", true, employees.stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList()));
    }

    public ApiResponse getById(UUID id) {
        Employee employee = repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Employee not found"));
        return new ApiResponse("Employee found", true, EmployeeMapper.toDto(employee));
    }

    public ApiResponse getByEnterpriseId(UUID enterpriseId) {
        List<Employee> all = repository.findAllByEnterpriseId(enterpriseId);
        if (all.isEmpty())
            return new ApiResponse("Employee not found", false);

        return new ApiResponse("Employee found", true, all.stream()
                .map(EmployeeMapper::toDto)
                .collect(Collectors.toList()));
    }

    private void toUser(EmployeeDto employeeDto, User user, Role role) {
        user.setRole(role);
        user.setFirstName(employeeDto.getFirstName());
        user.setLastName(employeeDto.getLastName());
        user.setUsername(employeeDto.getJshshir());
        user.setPassword(passwordEncoder.encode(employeeDto.getPassword()));
    }

    public static <T> T findByIdOrThrow(JpaRepository<T, UUID> repository, UUID id, String entityName) {
        return repository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException(entityName + " not found"));
    }

    public static <T> List<T> findByIdsOrThrow(JpaRepository<T, UUID> repository, List<UUID> ids, String entityName) {
        List<T> result = repository.findAllById(ids);

        // Agar topilgan elementlar ro'yxati id'lar ro'yxatiga teng bo'lmasa, demak, ba'zi id'lar topilmadi.
        if (result.size() != ids.size()) {
            Set<UUID> foundIds = result.stream().map(entity -> ((Identifiable) entity).getId()).collect(Collectors.toSet());
            for (UUID id : ids) {
                if (!foundIds.contains(id)) {
                    throw new IllegalArgumentException(entityName + " with ID " + id + " not found");
                }
            }
        }
        return result;
    }

}