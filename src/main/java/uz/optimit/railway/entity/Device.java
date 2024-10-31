package uz.optimit.railway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import uz.optimit.railway.entity.template.AbsEntity;

import java.util.Objects;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Device extends AbsEntity {

    private String name;

    private String description;

    @ManyToOne
    private Station station;

    @ManyToOne
    private LevelCrossing levelCrossing;

    @ManyToOne
    private Peregon peregon;

    @ManyToOne
    private Category category;

    private double latitude;
    private double longitude;
    private Boolean isStation;
}
