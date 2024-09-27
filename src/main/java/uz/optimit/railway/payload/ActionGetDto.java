package uz.optimit.railway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionGetDto {
    private UUID id;
    private String description;
    private UUID userId;
    private String userFirstName;
    private String userLastName;
    private UUID deviceId;
    private String deviceName;
    private Timestamp createdAt;
    private Timestamp doneTime;
    private boolean done;
}
