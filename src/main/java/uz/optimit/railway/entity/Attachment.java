package uz.optimit.railway.entity;

import jakarta.persistence.Entity;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import uz.optimit.railway.entity.template.AbsEntity;


@Data
@Entity
@NoArgsConstructor
@AllArgsConstructor
public class Attachment extends AbsEntity {

    private String name;

    private String fileOriginalName;

    private long size;

    private String contentType;
}
