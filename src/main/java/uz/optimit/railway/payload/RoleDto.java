package uz.optimit.railway.payload;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.optimit.railway.enums.Permission;

import java.util.List;
import java.util.UUID;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RoleDto {
    private UUID id;

    private String name;

    private String roleType;

    private List<Permission> permissions;

    private String description;
}
