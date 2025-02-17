package uz.optimit.railway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class DeviceDto {
    private UUID id;
    private String name;
    private String description;
    private UUID stationId;
    private UUID levelCrossingId;
    private UUID categoryId;
    private UUID plotId;
    private UUID peregonId;
    private String stationName;
    private String peregonName;
    private String levelCrossingName;
    private double latitude;
    private double longitude;
    private boolean isCheck;
}
