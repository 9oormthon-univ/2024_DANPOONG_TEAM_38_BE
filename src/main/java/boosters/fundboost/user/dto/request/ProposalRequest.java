package boosters.fundboost.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProposalRequest {
    @NotNull(message = "프로젝트 아이디는 필수입니다.")
    private long projectId;
    @NotNull(message = "제목은 필수입니다.")
    private String title;
    @NotNull(message = "내용은 필수입니다.")
    private String content;
    private MultipartFile file;
}
