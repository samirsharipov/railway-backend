package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Peregon;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.PeregonDto;
import uz.optimit.railway.repository.PeregonRepository;
import uz.optimit.railway.repository.PlotRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class PeregonService {

    private final PeregonRepository repository;
    private final PlotRepository plotRepository;

    public ApiResponse create(PeregonDto peregonDto) {
        Peregon peregon = fromDto(peregonDto, new Peregon());
        if (peregonDto.getPlotId() != null) {
            plotRepository.findById(peregonDto.getPlotId()).ifPresent(peregon::setPlot);
        }
        repository.save(peregon);
        return new ApiResponse("Peregon created", true);
    }

    public ApiResponse edit(UUID id, PeregonDto peregonDto) {
        Optional<Peregon> optionalPeregon = repository.findById(id);
        if (optionalPeregon.isEmpty())
            return new ApiResponse("not found peregon", false);

        Peregon peregon = optionalPeregon.get();
        if (peregonDto.getPlotId() != null) {
            plotRepository.findById(peregonDto.getPlotId()).ifPresent(peregon::setPlot);
        }
        repository.save(fromDto(peregonDto, peregon));

        return new ApiResponse("Peregon modified", true);
    }

    public ApiResponse getAll() {
        List<Peregon> perogons = repository.findAllByDeletedIsFalse();
        if (perogons.isEmpty())
            return new ApiResponse("not found perogons", false);

        return new ApiResponse("perogons", true, toDto(perogons));
    }

    public ApiResponse getById(UUID id) {
        Optional<Peregon> optionalPeregon = repository.findById(id);
        return optionalPeregon.map(peregon ->
                        new ApiResponse("peregon", true, toDto(peregon)))
                .orElseGet(() -> new ApiResponse("not found peregon", false));
    }

    public ApiResponse getByPlotId(UUID plotId) {
        List<Peregon> all = repository.findAllByPlotIdAndDeletedIsFalse(plotId);

        if (all.isEmpty())
            return new ApiResponse("not found perogons", false);

        return new ApiResponse("perogons", true, toDto(all));
    }

    private static PeregonDto toDto(Peregon peregon) {
        PeregonDto peregonDto = new PeregonDto();
        peregonDto.setId(peregon.getId());
        peregonDto.setName(peregon.getName());
        peregonDto.setDescription(peregon.getDescription());
        peregonDto.setAddress(peregon.getAddress());
        peregonDto.setLatitude(peregon.getLatitude());
        peregonDto.setLongitude(peregon.getLongitude());
        peregonDto.setPlotId(peregon.getPlot() != null ? peregon.getPlot().getId() : null);
        peregonDto.setPlotName(peregon.getPlot() != null ? peregon.getPlot().getName() : null);
        return peregonDto;
    }

    private static Peregon fromDto(PeregonDto peregonDto, Peregon peregon) {
        peregon.setName(peregonDto.getName());
        peregon.setDescription(peregonDto.getDescription());
        peregon.setAddress(peregonDto.getAddress());
        peregon.setLatitude(peregonDto.getLatitude());
        peregon.setLongitude(peregonDto.getLongitude());
        return peregon;
    }

    private static List<PeregonDto> toDto(List<Peregon> perogons) {
        List<PeregonDto> peregonDtoList = new ArrayList<>();
        for (Peregon peregon : perogons) {
            peregonDtoList.add(toDto(peregon));
        }
        return peregonDtoList;
    }

    public ApiResponse delete(UUID id) {
        Optional<Peregon> optionalPeregon = repository.findById(id);
        try {
            if (optionalPeregon.isPresent()) {
                repository.deleteById(id);
                return new ApiResponse("Peregon deleted", true);
            }
        } catch (Exception e) {
            return new ApiResponse("peregon ga boglangan qo'rilmalar mavjud!", false);
        }
        return new ApiResponse("Peregon not found", false);
    }
}