package uz.optimit.railway.repository;


import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.optimit.railway.entity.Mtu;

import java.util.List;
import java.util.UUID;

public interface MtuRepository extends JpaRepository<Mtu, UUID> {

    List<Mtu> findAllByDeletedIsFalse();

    @Modifying
    @Transactional
    @Query("UPDATE Mtu b SET b.deleted = true WHERE b.id = :id")
    void softDelete(UUID id);
}
