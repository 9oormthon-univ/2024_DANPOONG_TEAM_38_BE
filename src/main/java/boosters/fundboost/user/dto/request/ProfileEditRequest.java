package boosters.fundboost.user.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProfileEditRequest {
    private String link;
    private String introduceTitle;
    private String introduceContent;
    private MultipartFile image;

}