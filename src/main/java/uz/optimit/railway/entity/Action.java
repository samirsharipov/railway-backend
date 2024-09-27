package uz.optimit.railway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;
import uz.optimit.railway.entity.template.AbsEntity;

import java.sql.Timestamp;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Action extends AbsEntity {

    private String description;

    @ManyToOne(optional = false)
    private Device device;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private User user;

    private Timestamp doneTime;

    private boolean done = false;
}
