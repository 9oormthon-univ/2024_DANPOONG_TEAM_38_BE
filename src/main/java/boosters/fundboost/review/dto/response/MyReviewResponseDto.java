package boosters.fundboost.review.dto.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class MyReviewResponseDto {
    private String author;
    private String profileImage;
    private LocalDateTime createdAt;
    private String description;
}