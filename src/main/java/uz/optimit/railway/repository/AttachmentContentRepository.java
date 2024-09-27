package uz.optimit.railway.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import uz.optimit.railway.entity.AttachmentContent;

import java.util.Optional;
import java.util.UUID;

public interface AttachmentContentRepository extends JpaRepository<AttachmentContent, UUID> {
    Optional<AttachmentContent> findByAttachmentId(UUID attachmentId);
}
