package uz.optimit.railway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class StationDto {
    private UUID id;
    private String name;
    private String description;
    private String address;
    private double latitude;
    private double longitude;
}
