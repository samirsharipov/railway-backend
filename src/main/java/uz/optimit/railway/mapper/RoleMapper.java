package uz.optimit.railway.mapper;

import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.modelmapper.PropertyMap;
import org.springframework.stereotype.Component;
import uz.optimit.railway.entity.Role;
import uz.optimit.railway.enums.RoleType;
import uz.optimit.railway.payload.RoleDto;

import java.util.List;

@Component
@RequiredArgsConstructor
public class RoleMapper {

    private final ModelMapper modelMapper;

    @PostConstruct
    public void initMappings() {
        modelMapper.addMappings(new PropertyMap<RoleDto, Role>() {
            @Override
            protected void configure() {
                skip(destination.getId());
                skip(destination.getRoleType());
            }
        });
    }

    public Role toEntity(RoleDto roleDto) {
        Role role = modelMapper.map(roleDto, Role.class);
        role.setRoleType(RoleType.valueOf(roleDto.getRoleType()));
        return role;
    }

    public void updateEntity(RoleDto roleDto, Role role) {
        modelMapper.map(roleDto, role);
    }

    public RoleDto roleDto(Role role) {
        return modelMapper.map(role, RoleDto.class);
    }

    public List<RoleDto> roleDtoList(List<Role> roles) {
        return roles.stream().map(this::roleDto).toList();
    }
}
