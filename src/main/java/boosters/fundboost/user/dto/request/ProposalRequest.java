package boosters.fundboost.user.dto.request;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Getter
@Setter
public class ProposalRequest {
    @NotNull(message = "기업아이디는 필수입니다.")
    private long companyId;
    @NotNull(message = "제목은 필수입니다.")
    private String title;
    @NotNull(message = "내용은 필수입니다.")
    private String content;
    private List<MultipartFile> file;
}
