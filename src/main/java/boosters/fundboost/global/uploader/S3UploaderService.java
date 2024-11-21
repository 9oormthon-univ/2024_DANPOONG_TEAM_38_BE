package boosters.fundboost.global.uploader;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3UploaderService {
    private final S3Uploader s3Uploader;

    @Transactional
    public String upload(MultipartFile image) {
        if (image != null) {
            return s3Uploader.upload(image, "project-images");
        }
        return null;
    }
}
