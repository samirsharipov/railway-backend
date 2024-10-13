package uz.optimit.railway.entity;

import jakarta.persistence.Entity;
import lombok.*;
import uz.optimit.railway.entity.template.AbsEntity;

import java.sql.Timestamp;


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
}
