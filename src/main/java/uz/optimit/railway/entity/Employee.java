package uz.optimit.railway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.optimit.railway.entity.template.AbsEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Employee extends AbsEntity {

    private String fio;

    @ManyToOne
    private Enterprise enterprise;

    private String jshshir;

    private String role;

    @ManyToOne
    private User user;
}
