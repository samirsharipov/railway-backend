package uz.optimit.railway.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import uz.optimit.railway.entity.template.AbsEntity;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class AttachmentContent extends AbsEntity {
    private byte[] mainContent;

    @OneToOne
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Attachment attachment;
}
