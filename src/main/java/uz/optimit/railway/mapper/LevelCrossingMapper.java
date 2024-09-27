package uz.optimit.railway.mapper;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import uz.optimit.railway.entity.LevelCrossing;
import uz.optimit.railway.entity.Plot;
import uz.optimit.railway.payload.LevelCrossingDto;
import uz.optimit.railway.service.PlotService;

@Component
@RequiredArgsConstructor
public class LevelCrossingMapper {

    private final PlotService plotService;

    public LevelCrossing toEntity(LevelCrossingDto dto) {
        Plot plot = new Plot();
        if (dto.plotId() != null) {
            plot = plotService.findById(dto.plotId());
        }
        return new LevelCrossing(
                dto.name(),
                dto.description(),
                dto.address(),
                dto.latitude(),
                dto.longitude(),
                plot
        );
    }

    public LevelCrossingDto toDto(LevelCrossing entity) {
        return new LevelCrossingDto(
                entity.getId(),
                entity.getName(),
                entity.getDescription(),
                entity.getAddress(),
                entity.getLatitude() != null ? entity.getLongitude() : null,
                entity.getLongitude() != null ? entity.getLongitude() : null,
                entity.getPlot() != null ? entity.getPlot().getId() : null
        );
    }

    public void update(LevelCrossingDto dto, LevelCrossing entity) {
        entity.setName(dto.name());
        entity.setDescription(dto.description());
        entity.setAddress(dto.address());
        entity.setLatitude(dto.latitude());
        entity.setLongitude(dto.longitude() != null ? dto.longitude() : null);
        entity.setLongitude(dto.longitude() != null ? dto.longitude() : null);
        entity.setPlot(dto.plotId() != null ? plotService.findById(dto.plotId()) : entity.getPlot());
    }


}
