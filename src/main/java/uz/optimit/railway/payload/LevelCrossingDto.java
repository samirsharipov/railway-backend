package uz.optimit.railway.payload;

import uz.optimit.railway.entity.LevelCrossing;

import java.util.UUID;

/**
 * DTO for {@link LevelCrossing}
 */

public record LevelCrossingDto(UUID id, String name, String description, String address, Double latitude,
                               Double longitude, UUID plotId, String plotName) {

    public LevelCrossingDto(String name, String description, String address, Double latitude, Double longitude, UUID plotId, String plotName) {
        this(null, name, description, address, latitude, longitude, plotId, plotName);
    }

    public LevelCrossingDto(String name, String description, String address, UUID plotId, String plotName) {
        this(null, name, description, address, null, null, plotId, plotName);
    }

}