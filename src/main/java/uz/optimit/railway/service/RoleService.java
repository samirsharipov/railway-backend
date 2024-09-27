package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Role;
import uz.optimit.railway.enums.RoleType;
import uz.optimit.railway.mapper.RoleMapper;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.RoleDto;
import uz.optimit.railway.repository.RoleRepository;

import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;
    private final RoleMapper roleMapper;

    public ApiResponse create(RoleDto roleDto) {
        if (roleDto.getRoleType().equals(RoleType.SUPER_ADMIN.name()) ||
                roleDto.getRoleType().equals(RoleType.ADMIN.name()))
            return new ApiResponse("super admin or admin already exist", false);

        roleRepository.save(roleMapper.toEntity(roleDto));

        return new ApiResponse("role created", true);
    }

    public Role getRoleById(UUID roleId) {
        return roleRepository.findById(roleId).orElse(null);
    }

    public ApiResponse edit(UUID id, RoleDto roleDto) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty())
            return new ApiResponse("role not found", false);

        Role role = optionalRole.get();

        if (roleDto.getRoleType() != null)
            if (roleDto.getRoleType().equals(RoleType.SUPER_ADMIN.name()) || roleDto.getRoleType().equals(RoleType.ADMIN.name()))
                return new ApiResponse("super admin or admin already exist", false);


        roleMapper.updateEntity(roleDto, role);
        roleRepository.save(role);

        return new ApiResponse("role updated", true);
    }


    public ApiResponse getAllRoles() {
        return new ApiResponse("found", true, roleMapper.roleDtoList(roleRepository.findAllByDeletedIsFalse()));
    }

    public ApiResponse getById(UUID id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        return optionalRole.map(role -> new ApiResponse("role found", true, roleMapper
                .roleDto(role))).orElseGet(() -> new ApiResponse("role not found", false));
    }

    public ApiResponse delete(UUID id) {
        Optional<Role> optionalRole = roleRepository.findById(id);
        if (optionalRole.isEmpty())
            return new ApiResponse("role not found", false);

        roleRepository.deleteLogical(id);
        return new ApiResponse("role deleted", true);
    }
}
