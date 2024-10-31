package uz.optimit.railway.payload;

import lombok.Getter;
import lombok.Setter;

import java.util.UUID;

@Getter
@Setter
public class PeregonDto {
    private UUID id;
    private String name;
    private String description;
    private String address;
    private UUID plotId;
    private String plotName;
    private double latitude;
    private double longitude;
}
