package uz.optimit.railway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.optimit.railway.entity.template.AbsEntity;

@EqualsAndHashCode(callSuper = true)
@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Device extends AbsEntity {

    private String name;

    private String description;

    @ManyToOne
    private Station station;

    @ManyToOne
    private LevelCrossing levelCrossing;

    private double latitude;
    private double longitude;
}
