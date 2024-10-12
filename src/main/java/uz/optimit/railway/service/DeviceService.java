package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Action;
import uz.optimit.railway.entity.Device;
import uz.optimit.railway.mapper.ActionMapper;
import uz.optimit.railway.payload.*;
import uz.optimit.railway.repository.ActionRepository;
import uz.optimit.railway.repository.DeviceRepository;
import uz.optimit.railway.repository.LevelCrossingRepository;
import uz.optimit.railway.repository.StationRepository;
import uz.optimit.railway.repository.CategoryRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class DeviceService {
    private final DeviceRepository repository;
    private final StationRepository stationRepository;
    private final ActionRepository actionRepository;
    private final ActionMapper actionMapper;
    private final LevelCrossingRepository levelCrossingRepository;
    private final CategoryRepository categoryRepository;

    public ApiResponse create(DeviceDto deviceDto) {
        repository.save(fromDto(deviceDto, new Device()));
        return new ApiResponse("successfully added device", true);
    }

    public ApiResponse edit(UUID id, DeviceDto deviceDto) {
        Optional<Device> optionalDevice = repository.findById(id);
        if (optionalDevice.isEmpty())
            return new ApiResponse("device with id " + id + " not found", false);
        repository.save(fromDto(deviceDto, optionalDevice.get()));
        return new ApiResponse("successfully edited device", true);
    }

    public ApiResponse getAll() {
        return new ApiResponse("successfully retrieved all devices", true, toDto(repository.findAll()));
    }

    public ApiResponse getById(UUID id) {
        Optional<Device> optionalDevice = repository.findById(id);
        return optionalDevice
                .map(device -> new ApiResponse("successfully retrieved device", true, toDto(device)))
                .orElseGet(() -> new ApiResponse("device with id " + id + " not found", false));
    }

    private DeviceDto toDto(Device device) {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setId(device.getId());
        deviceDto.setName(device.getName());
        deviceDto.setDescription(device.getDescription());
        if (device.getStation() != null) {
            if (device.getStation().getId() != null) {
                deviceDto.setPlotId(device.getStation().getId());
            }
            deviceDto.setStationId(device.getStation().getId());
            deviceDto.setStationName(device.getStation().getName());
        }
        if (device.getLevelCrossing() != null) {
            deviceDto.setLevelCrossingId(device.getLevelCrossing().getId());
            deviceDto.setLevelCrossingName(device.getLevelCrossing().getName());
        }

        if (device.getCategory() != null) {
            deviceDto.setCategoryId(device.getCategory().getId());
        }


        deviceDto.setLongitude(device.getLongitude());
        deviceDto.setLatitude(device.getLatitude());
        return deviceDto;
    }

    private List<DeviceDto> toDto(List<Device> devices) {
        List<DeviceDto> deviceDtoList = new ArrayList<>();
        for (Device device : devices) {
            deviceDtoList.add(toDto(device));
        }
        return deviceDtoList;
    }

    private Device fromDto(DeviceDto deviceDto, Device device) {
        device.setName(deviceDto.getName());
        device.setDescription(deviceDto.getDescription() != null ? deviceDto.getDescription() : "");
        if (deviceDto.getStationId() != null) {
            stationRepository.findById(deviceDto.getStationId()).ifPresent(device::setStation);
        }
        if (deviceDto.getLevelCrossingId() != null) {
            levelCrossingRepository.findById(deviceDto.getLevelCrossingId()).ifPresent(device::setLevelCrossing);
        }
        if (deviceDto.getCategoryId() != null) {
            categoryRepository.findById(deviceDto.getCategoryId()).ifPresent(device::setCategory);
        }
        device.setLongitude(deviceDto.getLongitude());
        device.setLatitude(deviceDto.getLatitude());
        device.setIsStation(deviceDto.isStation());
        return device;
    }

    public ApiResponse getInfoForQR(UUID id) {
        Optional<Device> optionalDevice = repository.findById(id);
        if (optionalDevice.isEmpty())
            return new ApiResponse("device with id " + id + " not found", false);

        ActionGetDto actionGetDto = new ActionGetDto();
        Optional<Action> optionalAction = actionRepository.findLatestDoneActionByDeviceId(id);
        if (optionalAction.isPresent()) {
            actionGetDto = actionMapper.actionGetDto(optionalAction.get());
        }

        DeviceDto deviceDto = toDto(optionalDevice.get());

        return new ApiResponse("successfully retrieved device", true, new DeviceActionInfoDto(deviceDto, actionGetDto));
    }

    public ApiResponse getAllInfoActions(UUID id) {
        Optional<Device> optionalDevice = repository.findById(id);
        if (optionalDevice.isEmpty())
            return new ApiResponse("device with id " + id + " not found", false);

        List<Action> allActions = actionRepository.findAllByDeviceIdOrderByCreatedAtDesc(id);
        if (allActions.isEmpty())
            return new ApiResponse("action with id " + id + " not found", false);

        DeviceDto deviceDto = toDto(optionalDevice.get());
        List<ActionGetDto> actionGetDtoList = actionMapper.actionGetDtoList(allActions);

        return new ApiResponse("successfully retrieved device", true, new DeviceActionListInfoDto(deviceDto, actionGetDtoList));
    }

    public ApiResponse getByIsStationTrue() {
        List<Device> all = repository.findAllByIsStationIsTrueAndDeletedIsFalse();

        if (all.isEmpty())
            return new ApiResponse("not found", false);

        return new ApiResponse("found", true, toDto(all));
    }

    public ApiResponse getByIsStationFalse() {
        List<Device> all = repository.findAllByIsStationIsFalseAndDeletedIsFalse();

        if (all.isEmpty())
            return new ApiResponse("not found", false);

        return new ApiResponse("found", true, toDto(all));
    }

    public ApiResponse getByStationId(UUID stationId) {
        List<Device> all = repository.findAllByStationIdAndDeletedIsFalse(stationId);

        if (all.isEmpty())
            return new ApiResponse("not found", false);

        return new ApiResponse("found", true, toDto(all));
    }


    public ApiResponse getByLevelCrossingId(UUID levelCrossingId) {
        List<Device> all = repository.findAllByLevelCrossingIdAndDeletedIsFalse(levelCrossingId);

        if (all.isEmpty())
            return new ApiResponse("not found", false);

        return new ApiResponse("found", true, toDto(all));
    }

    public ApiResponse getByCategory(UUID categoryId) {

        List<Device> all = repository.findAllByCategoryIdAndDeletedIsFalse(categoryId);

        if (all.isEmpty())
            return new ApiResponse("not found", false);

        return new ApiResponse("found", true, toDto(all));
    }

    public ApiResponse delete(UUID id) {

        Optional<Device> optionalDevice = repository.findById(id);
        if (optionalDevice.isEmpty())
            return new ApiResponse("device with id " + id + " not found", false);

        repository.softDelete(id);
        return new ApiResponse("successfully deleted device", true);
    }

    public ApiResponse getByPlot(UUID plotId) {
        List<Device> all = repository.findAllByStation_PlotIdAndDeletedIsFalse(plotId);
        if (all.isEmpty())
            return new ApiResponse("not found", false);


        return new ApiResponse("found", true, toDto(all));
    }
}
