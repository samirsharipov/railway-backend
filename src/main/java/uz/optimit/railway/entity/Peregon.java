package uz.optimit.railway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.optimit.railway.entity.template.AbsEntity;

@Getter
@Setter
@Entity
@AllArgsConstructor
@NoArgsConstructor
public class Peregon extends AbsEntity {
    private String name;
    private String description;
    private String address;
    private double latitude;
    private double longitude;

    @ManyToOne
    private Plot plot;
}
