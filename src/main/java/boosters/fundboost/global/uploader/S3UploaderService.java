package boosters.fundboost.global.uploader;

import boosters.fundboost.global.utils.FileValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Arrays;

@Service
@RequiredArgsConstructor
public class S3UploaderService {
    private final S3Uploader s3Uploader;

    public String uploadImage(MultipartFile image, String directory) {
        FileValidator.validateFileExtension(image, Arrays.asList("jpg", "jpeg", "png"));
        return upload(image, directory);
    }

    public String uploadFile(MultipartFile file, String directory) {
        FileValidator.validateFileExtension(file, Arrays.asList("pdf", "doc", "docx", "ppt", "pptx", "xls", "xlsx", "hwp", "zip"));
        return upload(file, directory);
    }

    private String upload(MultipartFile file, String directory) {
        return s3Uploader.upload(file, directory);
    }
}
