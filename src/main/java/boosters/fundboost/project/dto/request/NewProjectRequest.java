package boosters.fundboost.project.dto.request;

import lombok.Getter;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@Getter
@Setter
public class NewProjectRequest {
    private int page = 0;
    private int size = 10;

    public Pageable toPageable() {
        return PageRequest.of(page, size);
    }
}
