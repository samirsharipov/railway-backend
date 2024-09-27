package uz.optimit.railway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.optimit.railway.entity.template.AbsEntity;


@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class LevelCrossing extends AbsEntity {
    private String name;

    private String description;

    private String address;
    private Double latitude;
    private Double longitude;

    @ManyToOne
    private Plot plot;
}
