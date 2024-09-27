package uz.optimit.railway.mapper;

import jakarta.annotation.PostConstruct;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import uz.optimit.railway.entity.User;
import uz.optimit.railway.payload.UserDto;
import uz.optimit.railway.service.RoleService;

import java.util.List;
@Component
public class UserMapper {

    private final ModelMapper modelMapper;
    private final RoleService roleService;

    public UserMapper(ModelMapper modelMapper, RoleService roleService) {
        this.modelMapper = modelMapper;
        this.roleService = roleService;
    }

    @PostConstruct
    public void init() {
        modelMapper.addMappings(new PropertyMap<User, UserDto>() {
            @Override
            protected void configure() {
                map(source.getRole().getId(), destination.getRoleId());
            }
        });

        modelMapper.addMappings(new PropertyMap<UserDto, User>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                // other property mappings if needed
            }
        });
    }

    public UserDto toDtoUser(User user) {
        return modelMapper.map(user, UserDto.class);
    }

    public User toEntityUser(UserDto userDto) {
        User user = modelMapper.map(userDto, User.class);

        // Qo'lda role ni o'rnatish
        if (userDto.getRoleId() != null) {
            user.setRole(roleService.getRoleById(userDto.getRoleId()));
        }

        return user;
    }

    public List<UserDto> toDtoUsers(List<User> users) {
        return users.stream()
                .map(this::toDtoUser)
                .toList();
    }

    public void updateUser(UserDto userDto, User user) {
        modelMapper.map(userDto, user);

        if (userDto.getRoleId() != null) {
            user.setRole(roleService.getRoleById(userDto.getRoleId()));
        }
    }
}
