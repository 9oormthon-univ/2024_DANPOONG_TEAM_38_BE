package boosters.fundboost.project.dto.request;

import boosters.fundboost.project.domain.enums.ProjectCategory;
import boosters.fundboost.project.domain.enums.Region;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class ProjectBasicInfoRequest {
    @NotBlank(message = "메인 제목은 필수 입력 항목입니다.")
    private String mainTitle;

    @NotBlank(message = "서브 제목은 필수 입력 항목입니다.")
    private String subTitle;

    @NotNull(message = "프로젝트 카테고리는 필수 입력 항목입니다.")
    private ProjectCategory category;

    @NotNull(message = "프로젝트 지역은 필수 입력 항목입니다.")
    private Region region;

    @NotBlank(message = "계정 정보는 필수 입력 항목입니다.")
    private String account;

    @NotBlank(message = "예산 설명은 필수 입력 항목입니다.")
    private String budgetDescription;

    @NotBlank(message = "일정 설명은 필수 입력 항목입니다.")
    private String scheduleDescription;

    @NotBlank(message = "팀 설명은 필수 입력 항목입니다.")
    private String teamDescription;

    @NotNull(message = "목표 금액은 필수 입력 항목입니다.")
    private Long targetAmount;

    @NotBlank(message = "프로젝트 소개는 필수 입력 항목입니다.")
    private String introduction;

    @NotNull(message = "이미지 파일은 필수 입력 항목입니다.")
    private MultipartFile image;
}
