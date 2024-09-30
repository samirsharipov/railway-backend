package uz.optimit.railway.factory;

import uz.optimit.railway.entity.Role;
import uz.optimit.railway.entity.User;
import uz.optimit.railway.payload.UserDto;


public class UserFactorySingleton {

    private static UserFactorySingleton instance;

    private UserFactorySingleton() {}

    public static synchronized UserFactorySingleton getInstance() {
        if (instance == null) {
            instance = new UserFactorySingleton();
        }
        return instance;
    }

    public User createUser(String firstName, String lastName, String username, String password, Role role) {
        return User.builder()
                .firstName(firstName)
                .lastName(lastName)
                .username(username)
                .password(password)
                .role(role)
                .enabled(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .build();
    }

    public User createUser(UserDto userDto, Role role) {
        return User.builder()
                .firstName(userDto.getFirstName())
                .lastName(userDto.getLastName())
                .username(userDto.getUsername())
                .password(userDto.getPassword())
                .role(role)
                .enabled(true)
                .accountNonExpired(true)
                .credentialsNonExpired(true)
                .accountNonLocked(true)
                .build();
    }
}
