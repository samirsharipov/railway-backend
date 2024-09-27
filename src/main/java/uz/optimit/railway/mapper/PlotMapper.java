package uz.optimit.railway.mapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import uz.optimit.railway.entity.Enterprise;
import uz.optimit.railway.entity.Plot;
import uz.optimit.railway.service.EnterpriseService;
import uz.optimit.railway.service.PlotDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class PlotMapper {

    private final ModelMapper modelMapper;
    private final EnterpriseService enterpriseService;


    @PostConstruct
    public void init() {
        modelMapper.addMappings(new PropertyMap<PlotDto, Plot>() {

            @Override
            protected void configure() {
                skip(destination.getId());
            }
        });

        modelMapper.addMappings(new PropertyMap<Plot, PlotDto>() {

            @Override
            protected void configure() {
                map(source.getEnterprise().getId(), destination.getEnterpriseId());
            }
        });
    }


    public Plot toEntity(PlotDto plotDto) {
        Plot plot = modelMapper.map(plotDto, Plot.class);
        if (plotDto.getEnterpriseId() != null) {
            plot.setEnterprise(enterpriseService.getEnterpriseById(plotDto.getEnterpriseId()));
        }
        return plot;
    }

    public PlotDto toDto(Plot plot) {
        return modelMapper.map(plot, PlotDto.class);
    }

    public List<PlotDto> toDto(List<Plot> plots) {
        return plots.stream()
                .map(this::toDto)
                .toList();
    }

    public void update(PlotDto plotDto, Plot plot) {
        if (plotDto.getEnterpriseId() != null) {
            plot.setEnterprise(enterpriseService.getEnterpriseById(plotDto.getEnterpriseId()));
        }
        modelMapper.map(plotDto, plot);
    }
}
