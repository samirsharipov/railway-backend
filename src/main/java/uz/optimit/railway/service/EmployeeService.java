package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import uz.optimit.railway.entity.*;
import uz.optimit.railway.mapper.EmployeeMapper;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.EmployeeDto;
import uz.optimit.railway.repository.EmployeeRepository;
import uz.optimit.railway.repository.EnterpriseRepository;
import uz.optimit.railway.repository.RoleRepository;
import uz.optimit.railway.repository.UserRepository;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
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
    private final AttachmentService attachmentService;


    public ApiResponse create(EmployeeDto employeeDto, MultipartFile file) throws IOException {

        Role role = findByIdOrThrow(roleRepository, employeeDto.getRoleId(), "Role");
        Enterprise enterprise = findByIdOrThrow(enterpriseRepository, employeeDto.getEnterpriseId(), "Enterprise");


        User user = new User();
        toUser(employeeDto, user, role);
        user = userRepository.save(user);

        Employee employee = new Employee();
        try {
            setAttachment(file, employee);
        } catch (IOException e) {
            return new ApiResponse("Faylni yuklashda xatolik yuz berdi", false);
        }
        setAttachment(file, employee);
        repository.save(EmployeeMapper.toEntity(employee, employeeDto, enterprise, user));
        return new ApiResponse("Employee created", true);
    }

    private void setAttachment(MultipartFile file, Employee employee) throws IOException {
        if (!file.isEmpty()) {
            Attachment attachment = attachmentService.attachmentCreate(file);
            employee.setAttachment(attachment);
        }
    }

    public ApiResponse update(UUID id, EmployeeDto employeeDto, MultipartFile file) throws IOException {
        Employee employee = findByIdOrThrow(repository, id, "Employee");
        Role role = findByIdOrThrow(roleRepository, employeeDto.getRoleId(), "Role");
        Enterprise enterprise = findByIdOrThrow(enterpriseRepository, employeeDto.getEnterpriseId(), "Enterprise");

        User user = employee.getUser();
        toUser(employeeDto, user, role);
        userRepository.save(user);

        try {
            setAttachment(file, employee);
        } catch (IOException e) {
            return new ApiResponse("Faylni yuklashda xatolik yuz berdi", false);
        }

        repository.save(EmployeeMapper.toEntity(employee, employeeDto, enterprise, user));

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
}
