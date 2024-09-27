package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Plot;
import uz.optimit.railway.mapper.PlotMapper;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.repository.PlotRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PlotService {

    private final PlotRepository repository;
    private final PlotMapper plotMapper;

    public ApiResponse create(PlotDto plotDto) {
        if (plotDto.getEnterpriseId() == null)
            throw new IllegalArgumentException("Enterprise id cannot be null");

        repository.save(plotMapper.toEntity(plotDto));
        return new ApiResponse("Plot created", true);
    }


    public ApiResponse edit(UUID id, PlotDto plotDto) {
        Optional<Plot> optionalPlot =
                repository.findById(id);

        if (optionalPlot.isEmpty())
            throw new IllegalArgumentException("Plot with id " + id + " not found");

        Plot plot = optionalPlot.get();
        plotMapper.update(plotDto, plot);
        repository.save(plot);

        return new ApiResponse("Plot updated", true);
    }


    public ApiResponse getAll() {
        List<Plot> all = repository.findAll();
        if (all.isEmpty())
            throw new IllegalArgumentException("Plot list is empty");

        return new ApiResponse("success", true, plotMapper.toDto(all));
    }


    public ApiResponse getById(UUID id) {
        Optional<Plot> optionalPlot = repository.findById(id);
        if (optionalPlot.isEmpty())
            throw new IllegalArgumentException("Plot with id " + id + " not found");

        return new ApiResponse("success", true, plotMapper.toDto(optionalPlot.get()));
    }


    public ApiResponse getByEnterprise(UUID enterpriseId) {
        List<Plot> all = repository.findAllByEnterpriseId(enterpriseId);
        if (all.isEmpty())
            throw new IllegalArgumentException("Plot list is empty");

        return new ApiResponse("success", true, plotMapper.toDto(all));
    }

    public Plot findById(UUID id) {
        return repository.findById(id).orElse(null);
    }
}
