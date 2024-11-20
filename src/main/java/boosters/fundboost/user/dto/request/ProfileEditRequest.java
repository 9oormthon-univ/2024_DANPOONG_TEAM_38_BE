package boosters.fundboost.user.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProfileEditRequest {
    String link;
    String introduceTitle;
    String introduceContent;
    MultipartFile image;

}