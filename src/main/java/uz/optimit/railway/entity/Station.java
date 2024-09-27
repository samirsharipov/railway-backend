package uz.optimit.railway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import uz.optimit.railway.entity.template.AbsEntity;


@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Station extends AbsEntity {
    private String name;
    private String description;
    private String address;
    private double latitude;
    private double longitude;

    @ManyToOne
    private Plot plot;

}
