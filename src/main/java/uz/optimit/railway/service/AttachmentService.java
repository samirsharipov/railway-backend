package uz.optimit.railway.service;

import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.util.FileCopyUtils;
import org.springframework.web.multipart.MultipartFile;
import uz.optimit.railway.entity.Attachment;
import uz.optimit.railway.entity.AttachmentContent;
import uz.optimit.railway.payload.ApiResponse;
import uz.optimit.railway.repository.AttachmentContentRepository;
import uz.optimit.railway.repository.AttachmentRepository;

import java.io.IOException;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class AttachmentService {

    private final AttachmentRepository repository;
    private final AttachmentContentRepository attachmentContentRepository;

    public ApiResponse upload(MultipartFile file) throws IOException {
        if (file.isEmpty())
            return new ApiResponse("File is empty", false);

        Attachment attachment = new Attachment();
        attachment.setFileOriginalName(file.getOriginalFilename());
        attachment.setSize(file.getSize());
        attachment.setContentType(file.getContentType());
        Attachment savedAttachment = repository.save(attachment);

        AttachmentContent attachmentContent = new AttachmentContent();
        attachmentContent.setId(attachment.getId());
        attachmentContent.setMainContent(file.getBytes());
        attachmentContent.setAttachment(savedAttachment);
        attachmentContentRepository.save(attachmentContent);

        return new ApiResponse("Attachment saved", true);
    }

    public ApiResponse download(UUID id, HttpServletResponse response) throws IOException {
        Optional<Attachment> optionalAttachment = repository.findById(id);
        if (optionalAttachment.isEmpty())
            return new ApiResponse("Attachment not found", false);

        Attachment attachment = optionalAttachment.get();

        Optional<AttachmentContent> attachmentContentOptional = attachmentContentRepository.findByAttachmentId(attachment.getId());
        if (attachmentContentOptional.isPresent()) {
            AttachmentContent attachmentContent = attachmentContentOptional.get();

            response.setContentType(attachment.getContentType());
            response.setHeader("Content-Disposition", attachment.getFileOriginalName() + "/:" + attachment.getSize());
            FileCopyUtils.copy(attachmentContent.getMainContent(), response.getOutputStream());
        }
        return new ApiResponse("Attachment download", true);
    }
}
