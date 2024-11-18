package uz.optimit.railway.entity;

import jakarta.persistence.*;
import lombok.*;
import uz.optimit.railway.entity.template.AbsEntity;

import java.util.List;

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

    @ManyToMany
    private List<Station> station;

    @ManyToOne
    private Attachment attachment;

    private String jshshir;

    private String role;

    @ManyToOne
    private User user;
}
