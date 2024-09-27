package uz.optimit.railway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ActionDto {

    private UUID id;
    private String description;
    private UUID deviceId;
    private UUID userId;

}
