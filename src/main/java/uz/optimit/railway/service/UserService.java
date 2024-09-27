package uz.optimit.railway.service;

import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import uz.optimit.railway.entity.Role;
import uz.optimit.railway.entity.User;
import uz.optimit.railway.mapper.UserMapper;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.payload.UserDto;
import uz.optimit.railway.repository.RoleRepository;
import uz.optimit.railway.repository.UserRepository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final RoleService roleService;

    public ApiResponse create(UserDto userDto) {
        if (userRepository.existsByUsername(userDto.getUsername())) {
            return new ApiResponse("Username already exists", false);
        }
        Optional<Role> optionalRole = roleRepository.findById(userDto.getRoleId());
        if (optionalRole.isEmpty()) {
            return new ApiResponse("Role not found", true);
        }
        userDto.setPassword(passwordEncoder.encode(userDto.getPassword()));
        User user = userMapper.toEntityUser(userDto);
        user.setRole(roleService.getRoleById(userDto.getRoleId()));
        userRepository.save(user);
        return new ApiResponse("User created", true);
    }


    public ApiResponse getAllUser() {
        List<UserDto> dtoList = userMapper.toDtoUsers(userRepository.findAllByDeletedIsFalse());
        return new ApiResponse("All users retrieved", true, dtoList);
    }


    public ApiResponse getById(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new ApiResponse("User not found", true);
        }
        UserDto userDto = userMapper.toDtoUser(optionalUser.get());
        return new ApiResponse("User retrieved", true, userDto);
    }


    public ApiResponse edit(UUID id, UserDto userDto) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new ApiResponse("User not found", true);
        }
        User user = optionalUser.get();

        if (userDto.getRoleId() != null) {
            Optional<Role> optionalRole = roleRepository.findById(userDto.getRoleId());
            if (optionalRole.isEmpty()) {
                return new ApiResponse("Role not found", true);
            }
            user.setRole(optionalRole.get());
        }
        userMapper.updateUser(userDto, user);

        if (userDto.getPassword() != null && !userDto.getPassword().isEmpty()) {
            user.setPassword(passwordEncoder.encode(userDto.getPassword()));
        }
        userRepository.save(user);
        return new ApiResponse("User updated", true);
    }

    public ApiResponse delete(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isEmpty()) {
            return new ApiResponse("User not found", true);
        }
        userRepository.logicalDelete(id);
        return new ApiResponse("User deleted", true);
    }


    public User getUserById(UUID id) {
        Optional<User> optionalUser = userRepository.findById(id);
        return optionalUser.orElse(null);
    }
}
