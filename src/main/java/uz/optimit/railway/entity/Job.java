package uz.optimit.railway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.optimit.railway.entity.template.AbsEntity;


@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
@NoArgsConstructor
@AllArgsConstructor
public class Job extends AbsEntity {

    @Column(length = 510)
    private String name;

    private String description;
}
