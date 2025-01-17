package uz.optimit.railway.repository;

import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import uz.optimit.railway.entity.Action;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface ActionRepository extends JpaRepository<Action, UUID> {

    List<Action> findAllByUserIdAndDoneIsFalseOrderByCreatedAtDesc(UUID user_id);
    List<Action> findAllByUserIdAndDoneIsTrueOrderByCreatedAtDesc(UUID user_id);
    List<Action> findAllByDeviceIdOrderByCreatedAtDesc(UUID deviceId);

    @Query("SELECT a FROM Action a WHERE a.device.id = :deviceId AND a.done = true ORDER BY a.createdAt DESC")
    Optional<Action> findLatestDoneActionByDeviceId(@Param("deviceId") UUID deviceId);

    @Modifying
    @Transactional
    @Query("UPDATE Action b SET b.deleted = true WHERE b.id = :id")
    void softDelete(UUID id);

    @Query("SELECT a FROM Action a WHERE " +
            "(a.device.peregon.id = :peregonId OR :peregon IS NULL) AND " +
            "(a.device.levelCrossing.id = :levelCrossingId OR :levelCrossing IS NULL) AND " +
            "(a.device.station.id = :stationId OR :station IS NULL)")
    List<Action> findActionsByFilters(
            @Param("peregon") UUID peregonId,
            @Param("levelCrossing") UUID levelCrossingId,
            @Param("station") UUID stationId
    );

    List<Action> findAllByDevice_CategoryIdAndDoneIsFalseOrderByCreatedAtDesc(UUID deviceCategoryId);
}
