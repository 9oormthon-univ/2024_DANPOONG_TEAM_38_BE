package boosters.fundboost.company.controller;

import boosters.fundboost.company.dto.request.CompanyLoginRequest;
import boosters.fundboost.company.dto.request.CompanyRegisterRequest;
import boosters.fundboost.company.dto.response.CompanyLoginResponse;
import boosters.fundboost.company.service.CompanyService;
import boosters.fundboost.global.security.refreshToken.RefreshTokenRequest;
import boosters.fundboost.global.security.refreshToken.RefreshTokenResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@Tag(name = "Company API", description = "기업 회원 관리 관련 API")
@RequestMapping("/api/companies")
public class CompanyController {
    private final CompanyService companyService;

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

        if (tokens == null || tokens.length < 2) {
            throw new IllegalArgumentException("로그인 처리 중 오류가 발생했습니다. 토큰 생성 실패");
        }

        return ResponseEntity.ok(new CompanyLoginResponse(tokens[0], tokens[1])); // Access, Refresh Token 반환
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

}