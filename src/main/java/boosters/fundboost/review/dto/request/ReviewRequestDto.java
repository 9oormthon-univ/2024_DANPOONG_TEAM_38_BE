package boosters.fundboost.review.dto.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;

@Getter
public class ReviewRequestDto {
    @NotBlank(message = "댓글 내용은 필수입니다.")
    private String description;
}