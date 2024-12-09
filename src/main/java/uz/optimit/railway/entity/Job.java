package uz.optimit.railway.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.*;
import uz.optimit.railway.entity.template.AbsEntity;

import java.sql.Timestamp;


@Entity
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Job extends AbsEntity {

    @Column(length = 510)
    private String name;

    private Timestamp startTime;

    private Timestamp doneTime;

    private String description;

    private boolean yearJob;

    private boolean done;

    private boolean paused;


}
