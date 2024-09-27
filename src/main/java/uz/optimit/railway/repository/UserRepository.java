package uz.optimit.railway.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.optimit.railway.entity.User;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface UserRepository extends JpaRepository<User, UUID> {

    boolean existsByUsername(String username);
    List<User> findAllByDeletedIsFalse();

    @Modifying
    @Transactional
    @Query("UPDATE users u SET u.deleted = true, u.enabled = false WHERE u.id = :userId")
    void logicalDelete(UUID userId);

    Optional<User> findByUsername(String username);
}
