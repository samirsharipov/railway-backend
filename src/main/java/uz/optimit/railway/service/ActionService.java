package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Action;
import uz.optimit.railway.entity.Device;
import uz.optimit.railway.mapper.ActionMapper;
import uz.optimit.railway.payload.*;
import uz.optimit.railway.repository.ActionRepository;
import uz.optimit.railway.repository.DeviceRepository;

import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class ActionService {
    private final ActionRepository repository;
    private final ActionMapper mapper;
    private static final double EARTH_RADIUS = 6371000;
    private final DeviceRepository deviceRepository;

    public ApiResponse create(ActionDto actionDto) {
        if (actionDto.getUserId() == null || actionDto.getDeviceId() == null) {
            return new ApiResponse("action or device not null", false);
        }
        repository.save(mapper.toActionEntity(actionDto));
        return new ApiResponse("success", true);
    }


    public ApiResponse getByUserDone(UUID userId) {
        List<Action> allActions =
                repository.findAllByUserIdAndDoneIsFalseOrderByCreatedAtDesc(userId);

        if (allActions.isEmpty())
            return new ApiResponse("Not found actions", false);

        List<ActionGetDto> actionGetDtoList = mapper.actionGetDtoList(allActions);

        return new ApiResponse("found", true, actionGetDtoList);
    }

    public ApiResponse getByUserDoneTrue(UUID userId) {
        List<Action> allActions =
                repository.findAllByUserIdAndDoneIsTrueOrderByCreatedAtDesc(userId);

        if (allActions.isEmpty())
            return new ApiResponse("Not found actions", false);

        List<ActionGetDto> actionGetDtoList = mapper.actionGetDtoList(allActions);

        return new ApiResponse("found", true, actionGetDtoList);
    }

    public ApiResponse edit(UUID id, ActionEditDto actionEditDto) {
        Optional<Action> optionalAction = repository.findById(id);
        if (optionalAction.isEmpty())
            return new ApiResponse("Not found action", false);

        Action action = optionalAction.get();
        action.setDescription(actionEditDto.getDescription());
        action.setDone(true);
        action.setDoneTime(new Timestamp(new Date().getTime()));
        repository.save(action);

        return new ApiResponse("success", true);
    }

    public ApiResponse checkUser(CheckDto checkDto) {

        Optional<Device> optionalDevice = deviceRepository.findById(checkDto.getDeviceId());
        if (optionalDevice.isEmpty()) {
            return new ApiResponse("Not found device", false);
        }

        Device device = optionalDevice.get();
        double distance = calculateDistance(
                checkDto.getLatitude(), checkDto.getLongitude(),
                device.getLatitude(), device.getLongitude());

        double radius = 200; // radius in meters
        if (distance <= radius) {
            return new ApiResponse("Success " + Math.round(distance) + " meters", true);
        } else {
            return new ApiResponse("You are " + Math.round(distance) + " meters away from the device", false);
        }
    }

    public static double calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double latDistance = Math.toRadians(lat2 - lat1);
        double lonDistance = Math.toRadians(lon2 - lon1);

        double a = Math.sin(latDistance / 2) * Math.sin(latDistance / 2)
                + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2))
                * Math.sin(lonDistance / 2) * Math.sin(lonDistance / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c * 1000;
    }

    public ApiResponse delete(UUID id) {
        Optional<Action> optionalAction = repository.findById(id);
        if (optionalAction.isEmpty())
            return new ApiResponse("Not found action", false);

        repository.softDelete(id);
        return new ApiResponse("success", true);
    }

    public ApiResponse getFiler(UUID deviceId) {
        List<Action> all = repository.findAllByDeviceIdAndDoneIsFalseOrderByCreatedAtDesc(deviceId);
        if (all.isEmpty())
            return new ApiResponse("Not found actions", false);
        List<ActionGetDto> actionGetDtoList = mapper.actionGetDtoList(all);
        return new ApiResponse("found", true, actionGetDtoList);
    }
}
