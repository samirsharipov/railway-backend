package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Station;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.StationDto;
import uz.optimit.railway.repository.StationRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class StationService {

    private final StationRepository repository;

    public ApiResponse create(StationDto stationDto) {
        repository.save(fromDto(stationDto, new Station()));
        return new ApiResponse("Station created", true);
    }

    public ApiResponse edit(UUID id, StationDto stationDto) {
        Optional<Station> optionalStation = repository.findById(id);
        if (optionalStation.isEmpty())
            return new ApiResponse("not found station", false);

        Station station = optionalStation.get();
        repository.save(fromDto(stationDto, station));

        return new ApiResponse("Station modified", true);
    }

    public ApiResponse getAll() {
        List<Station> stations = repository.findAll();
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


    private static StationDto toDto(Station station) {
        StationDto stationDto = new StationDto();
        stationDto.setId(station.getId());
        stationDto.setName(station.getName());
        stationDto.setDescription(station.getDescription());
        stationDto.setAddress(station.getAddress());
        stationDto.setLatitude(station.getLatitude());
        stationDto.setLongitude(station.getLongitude());
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

}
