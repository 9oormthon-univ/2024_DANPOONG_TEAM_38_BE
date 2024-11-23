package boosters.fundboost.company.controller;

import boosters.fundboost.company.dto.request.CompanyLoginRequest;
import boosters.fundboost.company.dto.request.CompanyRankingRequest;
import boosters.fundboost.company.dto.request.CompanyRegisterRequest;
import boosters.fundboost.company.dto.response.CompanyLoginResponse;
import boosters.fundboost.company.dto.response.CompanyRankingResponse;
import boosters.fundboost.company.service.CompanyService;
import boosters.fundboost.global.response.BaseResponse;
import boosters.fundboost.global.response.code.status.SuccessStatus;
import boosters.fundboost.global.security.refreshToken.RefreshTokenRequest;
import boosters.fundboost.global.security.refreshToken.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Tag(name = "Company API", description = "기업 회원 관리 관련 API")
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenDuration;

    public CompanyController(CompanyService companyService) {
        this.companyService = companyService;
    }

    @PostMapping("/register")
    public ResponseEntity<String> registerCompany(@RequestBody CompanyRegisterRequest request) {
        companyService.registerCompany(request);
        return ResponseEntity.ok("기업 회원가입이 완료되었습니다.");
    }

    @PostMapping("/login")
    public ResponseEntity<CompanyLoginResponse> loginCompany(@RequestBody CompanyLoginRequest request) {
        String[] tokens = companyService.loginCompany(request);
        String accessToken = tokens[0];
        String refreshToken = tokens[1];

        companyService.saveRefreshToken(request.getEmail(), refreshToken, refreshTokenDuration);

        return ResponseEntity.ok(new CompanyLoginResponse(accessToken, refreshToken));
    }

    @PostMapping("/refresh")
    public ResponseEntity<RefreshTokenResponse> refreshAccessToken(
            @RequestBody RefreshTokenRequest request) {
        String newAccessToken = companyService.refreshToken(request.getEmail(), request.getRefreshToken());
        return ResponseEntity.ok(new RefreshTokenResponse(newAccessToken));
    }

    @PostMapping("/logout")
    public ResponseEntity<String> logout(@RequestParam String email) {
        companyService.logout(email);
        return ResponseEntity.ok("로그아웃이 완료되었습니다.");
    }

    @Operation(summary = "메인페이지 기업 랭킹 조회 API", description = "메인페이지의 기업 랭킹을 조회합니다. " +
            "SortType은 weekly(주간), monthly(월간), allTime(전체), projects(프로젝트 수)입니다. ._숙희")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "COMMON200", description = "OK, 성공"),
            @ApiResponse(responseCode = "COMMON404", description = " BAD_REQUEST, 요청된 정보가 올바르지 않습니다."),
    })
    @GetMapping("/ranking")
    public BaseResponse<List<CompanyRankingResponse>> getRanking(CompanyRankingRequest companyRankingRequest) {
        List<CompanyRankingResponse> companies = companyService.getRanking(companyRankingRequest);
        return BaseResponse.onSuccess(SuccessStatus._OK, companies);
    }
}