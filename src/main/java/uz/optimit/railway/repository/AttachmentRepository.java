package uz.optimit.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.optimit.railway.entity.Attachment;

import java.util.UUID;

public interface AttachmentRepository extends JpaRepository<Attachment, UUID> {

}

