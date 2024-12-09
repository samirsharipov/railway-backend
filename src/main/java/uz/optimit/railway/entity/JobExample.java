package uz.optimit.railway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.optimit.railway.entity.template.AbsEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class JobExample extends AbsEntity {

    @Column(length = 510, unique = true, nullable = false)
    private String name;
}
