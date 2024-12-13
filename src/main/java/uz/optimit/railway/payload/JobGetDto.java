package uz.optimit.railway.payload;

import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.optimit.railway.entity.Station;
import uz.optimit.railway.entity.User;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class JobGetDto {
    private UUID id;

    private Timestamp createdAt;

    private String createdBy;

    private String name;

    private String description;

    private Timestamp startTime;

    private Timestamp doneTime;

    private String confirmUser;

    private String doneOrPausedUser;

    private String station;

    private boolean yearJob;

    private boolean confirm;

    private boolean done;

    private boolean paused;
}
