package uz.optimit.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.optimit.railway.entity.Employee;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    List<Employee> findAllByEnterpriseId(UUID id);
    List<Employee> findAllByUserId(UUID id);
    Optional<Employee> findByUserId(UUID id);
}
