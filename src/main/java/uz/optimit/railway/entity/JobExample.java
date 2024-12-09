package uz.optimit.railway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.Where;
import uz.optimit.railway.entity.template.AbsEntity;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Where(clause = "deleted = false")
public class JobExample extends AbsEntity {
    @Column(nullable = false, unique = true)
    private String name;
}
