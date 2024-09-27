package uz.optimit.railway.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.optimit.railway.entity.template.AbsEntity;
import uz.optimit.railway.enums.Permission;
import uz.optimit.railway.enums.RoleType;


import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Role extends AbsEntity {
    private String name;

    @Enumerated(EnumType.STRING)
    private RoleType roleType;

    @Enumerated(EnumType.STRING)
    @ElementCollection(fetch = FetchType.EAGER)
    private List<Permission> permissions;

    private String description;
}
