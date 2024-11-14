package uz.optimit.railway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Index;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.*;
import uz.optimit.railway.entity.template.AbsEntity;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(indexes = {@Index(columnList = "enterpriseId")})
public class Employee extends AbsEntity {

    private String fio;

    @ManyToOne
    private Enterprise enterprise;

    private String jshshir;

    private String role;

    @ManyToOne
    private User user;
}
