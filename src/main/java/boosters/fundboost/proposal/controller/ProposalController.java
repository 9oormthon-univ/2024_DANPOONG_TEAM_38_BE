package boosters.fundboost.proposal.controller;

import boosters.fundboost.global.response.BaseResponse;
import boosters.fundboost.global.response.code.status.SuccessStatus;
import boosters.fundboost.global.security.handler.annotation.AuthUser;
import boosters.fundboost.proposal.service.ProposalService;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.dto.request.ProposalRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/proposal")
@Tag(name = "Proposal 🔥", description = "제안서 관련 API")
public class ProposalController {
    private final ProposalService proposalService;

    @Operation(summary = "제안서 작성 API", description = "제안서를 작성합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @PostMapping(name = "/write", consumes = {"multipart/form-data"})
    public BaseResponse<?> createProposal(@Parameter(name = "user", hidden = true) @AuthUser User user,
                                          @ModelAttribute ProposalRequest request) {
        proposalService.writeProposal(user, request);
        return BaseResponse.onSuccess(SuccessStatus._NO_CONTENT, null);
    }
}
