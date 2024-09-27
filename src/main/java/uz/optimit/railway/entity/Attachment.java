package uz.optimit.railway.entity;

import jakarta.persistence.Entity;
import lombok.*;
import org.hibernate.proxy.HibernateProxy;
import uz.optimit.railway.entity.template.AbsEntity;

import java.util.Objects;


@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Attachment extends AbsEntity {

    private String name;

    private String fileOriginalName;

    private long size;

    private String contentType;
}
