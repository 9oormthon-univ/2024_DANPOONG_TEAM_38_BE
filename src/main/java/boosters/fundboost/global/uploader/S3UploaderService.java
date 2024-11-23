package boosters.fundboost.global.uploader;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

@Service
@RequiredArgsConstructor
public class S3UploaderService {
    private final S3Uploader s3Uploader;

    public String uploadImage(MultipartFile image, String directory) {
        return upload(image, directory);
    }

    public String uploadFile(MultipartFile file, String directory) {
        return upload(file, directory);
    }

    private String upload(MultipartFile file, String directory) {
        return s3Uploader.upload(file, directory);
    }
}
