package uz.optimit.railway.mapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import uz.optimit.railway.entity.Action;
import uz.optimit.railway.payload.ActionDto;
import uz.optimit.railway.payload.ActionGetDto;
import uz.optimit.railway.service.DeviceGetService;
import uz.optimit.railway.service.UserService;

import java.util.List;

@Component
@RequiredArgsConstructor
public class ActionMapper {
    private final ModelMapper modelMapper;
    private final UserService userService;
    private final DeviceGetService deviceService;


    @PostConstruct
    public void init() {
        modelMapper.addMappings(new PropertyMap<Action, ActionGetDto>() {

            @Override
            protected void configure() {
                map(source.getUser().getId(), destination.getUserId());
                map(source.getDevice().getId(), destination.getDeviceId());
                map(source.getDevice().getName(), destination.getDeviceName());
                map(source.getUser().getLastName(), destination.getUserLastName());
                map(source.getUser().getFirstName(), destination.getUserFirstName());
            }
        });
    }

    public Action toActionEntity(ActionDto actionDto) {
        Action action = modelMapper.map(actionDto, Action.class);
        action.setUser(userService.getUserById(actionDto.getUserId()));
        action.setDevice(deviceService.getDeviceById(actionDto.getDeviceId()));
        return action;
    }

    public ActionGetDto actionGetDto(Action action) {
        return modelMapper.map(action, ActionGetDto.class);
    }

    public List<ActionGetDto> actionGetDtoList(List<Action> actions) {
        return actions.stream()
                .map(this::actionGetDto)
                .toList();
    }
}
