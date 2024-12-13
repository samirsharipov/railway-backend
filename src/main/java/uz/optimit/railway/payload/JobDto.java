package uz.optimit.railway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
public class JobDto {
    private UUID id;
    private UUID createdBy;
    private String name;
    private String description;
    private Timestamp startTime;
    private Timestamp doneTime;
    private UUID confirmUserId;
    private UUID doneOrPausedUserId;
    private UUID stationId;
    private boolean yearJob;
    private boolean done;
    private boolean paused;
}