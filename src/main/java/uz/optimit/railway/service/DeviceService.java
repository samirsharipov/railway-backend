package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Action;
import uz.optimit.railway.entity.Category;
import uz.optimit.railway.entity.Device;
import uz.optimit.railway.mapper.ActionMapper;
import uz.optimit.railway.payload.*;
import uz.optimit.railway.repository.*;

import java.sql.Timestamp;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
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
    private final PeregonRepository peregonRepository;

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
            deviceDto.setPlotId(device.getStation().getPlot().getId());
            deviceDto.setStationId(device.getStation().getId());
            deviceDto.setStationName(device.getStation().getName());
        }
        if (device.getLevelCrossing() != null) {
            deviceDto.setPlotId(device.getLevelCrossing().getPlot().getId());
            deviceDto.setLevelCrossingId(device.getLevelCrossing().getId());
            deviceDto.setLevelCrossingName(device.getLevelCrossing().getName());
        }
        if (device.getPeregon() != null) {
            deviceDto.setPlotId(device.getPeregon().getPlot().getId());
            deviceDto.setPeregonId(device.getPeregon().getId());
            deviceDto.setPeregonName(device.getPeregon().getName());
        }

        if (device.getCategory() != null) {
            deviceDto.setCategoryId(device.getCategory().getId());
        }

        List<Action> all
                = actionRepository.findAllByDeviceIdOrderByCreatedAtDesc(device.getId());

        for (Action action : all) {
            Timestamp doneTime = action.getDoneTime();

            LocalDateTime dateTime =
                    Instant.ofEpochMilli(doneTime.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();

            int checkDay = device.getCategory().getCheckDay();
            // 7 kun qo'shamiz
            LocalDateTime newDate = dateTime.plusDays(checkDay);

            // Bugungi sanani olamiz
            LocalDateTime today = LocalDateTime.now();

            deviceDto.setCheck(newDate.isAfter(today));

            break;
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
        if (deviceDto.getPeregonId() != null) {
            peregonRepository.findById(deviceDto.getPeregonId()).ifPresent(device::setPeregon);
        }
        if (deviceDto.getCategoryId() != null) {
            Optional<Category> optionalCategory = categoryRepository.findById(deviceDto.getCategoryId());
            if (optionalCategory.isPresent()) {
                device.setCategory(optionalCategory.get());
                device.setIsStation(optionalCategory.get().isStation());
            }
        }
        device.setLongitude(deviceDto.getLongitude());
        device.setLatitude(deviceDto.getLatitude());
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

    public ApiResponse getByPlot(UUID plotId, boolean isStation) {
        List<Device> all;

        if (isStation)
            all = repository.findAllByStation_PlotIdAndIsStationIsTrueAndDeletedIsFalse(plotId);
        else
            all = repository.findAllByStation_PlotIdAndIsStationIsFalseAndDeletedIsFalse(plotId);

        if (all.isEmpty())
            return new ApiResponse("not found", false);

        return new ApiResponse("found", true, toDto(all));
    }

    public ApiResponse getByCategoryPlot(UUID categoryId, UUID plotId, UUID stationId) {

        List<Device> all = new ArrayList<>();
        if (stationId == null)
            all = repository.findAllByCategory_idAndStation_Plot_IdAndDeletedIsFalse(categoryId, plotId);
        else
            all = repository.findAllByCategory_idAndStation_Plot_IdAndStation_IdAndDeletedIsFalse(categoryId, plotId, stationId);
        if (all.isEmpty())
            return new ApiResponse("not found", false);

        return new ApiResponse("found", true, toDto(all));
    }

    public ApiResponse getByPeregon(UUID peregonId) {

        List<Device> all = repository.findAllByPeregonIdAndDeletedIsFalse(peregonId);
        if (all.isEmpty())
            return new ApiResponse("not found", false);


        return new ApiResponse("found", true, toDto(all));
    }
}