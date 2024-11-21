package boosters.fundboost.global.utils;

import boosters.fundboost.global.exception.S3Exception;
import boosters.fundboost.global.response.code.status.ErrorStatus;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

public class FileValidator {
    public static void validateFileExtension(MultipartFile file, List<String> allowedExtensions) {
        String fileName = file.getOriginalFilename();
        String fileExtension = getFileExtension(fileName);

        if (!allowedExtensions.contains(fileExtension.toLowerCase())) {
            throw new S3Exception(ErrorStatus.INVALID_EXTENSION);
        }
    }

    private static String getFileExtension(String fileName) {
        if (fileName != null && fileName.contains(".")) {
            return fileName.substring(fileName.lastIndexOf(".") + 1);
        }
        return "";
    }
}
