package uz.optimit.railway.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.optimit.railway.entity.template.AbsEntity;

@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Category extends AbsEntity {

    private String name;

    private String description;

    private int checkDay;

    private boolean station;
}
