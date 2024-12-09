package uz.optimit.railway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
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

    private String name;

    private String description;

    private Timestamp startTime;

    private Timestamp doneTime;

    @ManyToOne
    private User confirmUser;

    @ManyToOne
    private User doneOrPausedUser;

    @ManyToOne
    private Station station;

    private boolean yearJob;

    private boolean confirm;

    private boolean done;

    private boolean paused;
}