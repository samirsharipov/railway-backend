package uz.optimit.railway.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import uz.optimit.railway.entity.Category;

import java.util.List;
import java.util.UUID;

public interface CategoryRepository extends JpaRepository<Category, UUID> {

    @Modifying
    @Transactional
    @Query("UPDATE Category c SET c.deleted = true WHERE c.id = :id")
    void softDelete(UUID id);

    List<Category> findAllByDeletedIsFalse();

    List<Category> findAllByStationIsFalseAndDeletedIsFalse();

    List<Category> findAllByStationIsTrueAndDeletedIsFalse();


}
