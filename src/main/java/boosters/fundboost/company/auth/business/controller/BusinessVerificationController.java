package boosters.fundboost.company.auth.business.controller;

import boosters.fundboost.company.auth.business.service.BusinessVerificationService;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@Tag(name = "Business Verification API", description = "사업자 인증 관련 API")
@RequestMapping("/api/business")
public class BusinessVerificationController {
    private final BusinessVerificationService verificationService;

    public BusinessVerificationController(BusinessVerificationService verificationService) {
        this.verificationService = verificationService;
    }

    @PostMapping("/verify")
    public ResponseEntity<Map<String, Object>> verifyBusiness(@RequestParam String businessNumber) {
        try {
            Map<String, Object> result = verificationService.verifyBusiness(businessNumber);
            return ResponseEntity.ok(result);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }
}