package uz.optimit.railway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.hibernate.proxy.HibernateProxy;
import uz.optimit.railway.entity.template.AbsEntity;

import java.util.Objects;


@Getter
@Setter
@ToString
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentContent extends AbsEntity {
    private byte[] mainContent;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Attachment attachment;
}
