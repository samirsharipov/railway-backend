package uz.optimit.railway.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.optimit.railway.entity.Role;

import java.util.List;
import java.util.UUID;

public interface RoleRepository extends JpaRepository<Role, UUID> {
    List<Role> findAllByDeletedIsFalse();


    @Modifying
    @Transactional
    @Query("UPDATE Role r set r.deleted = true where r.id = :id")
    void deleteLogical(UUID id);
}
