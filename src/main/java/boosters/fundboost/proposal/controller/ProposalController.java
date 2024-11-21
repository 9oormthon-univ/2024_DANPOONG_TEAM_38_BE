package boosters.fundboost.proposal.controller;

import boosters.fundboost.global.response.BaseResponse;
import boosters.fundboost.global.response.code.status.SuccessStatus;
import boosters.fundboost.global.security.handler.annotation.AuthUser;
import boosters.fundboost.proposal.dto.response.ProposalPreviewResponse;
import boosters.fundboost.proposal.dto.response.ProposalResponse;
import boosters.fundboost.proposal.service.ProposalService;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.dto.request.ProposalRequest;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
            @ApiResponse(responseCode = "PROJECT400", description = "PROJECT_NOT_FOUND, 프로젝트를 찾을 수 없습니다."),
            @ApiResponse(responseCode = "COMPANY400", description = "COMPANY_NOT_FOUND, 기업을 찾을 수 없습니다."),
            @ApiResponse(responseCode = "S3400", description = "INVALID_EXTENSION, 지원하지 않는 확장자입니다."),
            @ApiResponse(responseCode = "FILE400", description = "FILE_CONVERT_ERROR, 파일링크를 JSON형식으로 변환하는 중 에러가 발생했습니다."),
    })
    @PostMapping(name = "/write", consumes = {"multipart/form-data"})
    public BaseResponse<?> createProposal(@Parameter(name = "user", hidden = true) @AuthUser User user,
                                          @ModelAttribute ProposalRequest request) {
        proposalService.writeProposal(user, request);
        return BaseResponse.onSuccess(SuccessStatus._NO_CONTENT, null);
    }

    @Operation(summary = "제안서 전체 조회 API", description = "기업의 제안서 전체를 조회합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @GetMapping("")
    public BaseResponse<Page<ProposalPreviewResponse>> getProposals(
            @Parameter(name = "user", hidden = true) @AuthUser User user,
            @RequestParam(value = "page", defaultValue = "0", required = false) int page) {
        return BaseResponse.onSuccess(SuccessStatus._OK, proposalService.getProposals(user, page));
    }

    @Operation(summary = "제안서 상세 조회 API", description = "기업의 제안서를 상세조회합니다._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
    })
    @GetMapping("/{proposalId}")
    public BaseResponse<ProposalResponse> getProposal(@Parameter(name = "user", hidden = true) @AuthUser User user,
                                                      @PathVariable(value = "proposalId") long proposalId) {
        return BaseResponse.onSuccess(SuccessStatus._OK, proposalService.getProposal(user, proposalId));
    }
}
