package uz.optimit.railway.factory;

import uz.optimit.railway.entity.Role;
import uz.optimit.railway.enums.Permission;
import uz.optimit.railway.enums.RoleType;

import java.util.List;

public class RoleFactorySingleton {

    private static RoleFactorySingleton instance;

    private RoleFactorySingleton() {
    }

    public static synchronized RoleFactorySingleton getInstance() {
        if (instance == null) {
            instance = new RoleFactorySingleton();
        }
        return instance;
    }

    public Role createRole(String name, RoleType roleType, List<Permission> permissions, String description) {
        return Role.builder()
                .name(name)
                .roleType(roleType)
                .permissions(permissions)
                .description(description)
                .build();
    }
}
