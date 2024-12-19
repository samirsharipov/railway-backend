package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Station;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.StationDto;
import uz.optimit.railway.repository.PlotRepository;
import uz.optimit.railway.repository.StationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository repository;
    private final PlotRepository plotRepository;

    public ApiResponse create(StationDto stationDto) {
        Station station = fromDto(stationDto, new Station());
        if (stationDto.getPlotId() != null) {
            plotRepository.findById(stationDto.getPlotId()).ifPresent(station::setPlot);
        }
        repository.save(station);
        return new ApiResponse("Station created", true);
    }

    public ApiResponse edit(UUID id, StationDto stationDto) {
        Optional<Station> optionalStation = repository.findById(id);
        if (optionalStation.isEmpty())
            return new ApiResponse("not found station", false);

        Station station = optionalStation.get();
        if (stationDto.getPlotId() != null) {
            plotRepository.findById(stationDto.getPlotId()).ifPresent(station::setPlot);
        }
        repository.save(fromDto(stationDto, station));

        return new ApiResponse("Station modified", true);
    }

    public ApiResponse getAll() {
        List<Station> stations = repository.findAllByDeletedIsFalseOrderByCreatedAtDesc();
        if (stations.isEmpty())
            return new ApiResponse("not found stations", false);

        return new ApiResponse("stations", true, toDto(stations));
    }

    public ApiResponse getById(UUID id) {
        Optional<Station> optionalStation = repository.findById(id);
        return optionalStation.map(station ->
                        new ApiResponse("station", true, toDto(station)))
                .orElseGet(() -> new ApiResponse("not found station", false));
    }


    public ApiResponse getByPlotId(UUID plotId) {
        List<Station> all = repository.findAllByPlotIdAndDeletedIsFalseOrderByCreatedAtDesc(plotId);

        if (all.isEmpty())
            return new ApiResponse("not found stations", false);

        return new ApiResponse("stations", true, toDto(all));
    }

    private static StationDto toDto(Station station) {
        StationDto stationDto = new StationDto();
        stationDto.setId(station.getId());
        stationDto.setName(station.getName());
        stationDto.setDescription(station.getDescription());
        stationDto.setAddress(station.getAddress());
        stationDto.setLatitude(station.getLatitude());
        stationDto.setLongitude(station.getLongitude());
        stationDto.setPlotId(station.getPlot() != null ? station.getPlot().getId() : null);
        stationDto.setPlotName(station.getPlot() != null ? station.getPlot().getName() : null);
        return stationDto;
    }

    private static Station fromDto(StationDto stationDto, Station station) {
        station.setName(stationDto.getName());
        station.setDescription(stationDto.getDescription());
        station.setAddress(stationDto.getAddress());
        station.setLatitude(stationDto.getLatitude());
        station.setLongitude(stationDto.getLongitude());
        return station;
    }

    private static List<StationDto> toDto(List<Station> stations) {
        List<StationDto> stationDtoList = new ArrayList<>();
        for (Station station : stations) {
            stationDtoList.add(toDto(station));
        }
        return stationDtoList;
    }

    public ApiResponse delete(UUID id) {
        Optional<Station> optionalStation = repository.findById(id);
        try {
            if (optionalStation.isPresent()) {
                repository.deleteById(id);
                return new ApiResponse("Station deleted", true);
            }
        } catch (Exception e) {
            return new ApiResponse("station ga boglangan qo'rilmalar mavjud!", false);
        }
        return new ApiResponse("Station not found", false);
    }
}
