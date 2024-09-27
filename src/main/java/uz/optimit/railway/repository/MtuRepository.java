package uz.optimit.railway.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import uz.optimit.railway.entity.Mtu;

import java.util.UUID;

public interface MtuRepository extends JpaRepository<Mtu, UUID> {
}
