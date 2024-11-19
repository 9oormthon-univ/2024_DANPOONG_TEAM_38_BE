package boosters.fundboost.company.auth.email.controller;

import boosters.fundboost.company.auth.email.service.EmailService;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.mail.MessagingException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Tag(name = "Email Verification API", description = "이메일 인증 관련 API")
@RequestMapping("/api/auth")
public class EmailController {
    private final EmailService emailService;

    public EmailController(EmailService emailService) {
        this.emailService = emailService;
    }

    // 인증 코드 요청
    @PostMapping("/send-email")
    public String sendVerificationEmail(@RequestParam String email) throws MessagingException {
        String verificationCode = emailService.generateVerificationCode();
        emailService.saveVerificationCode(email, verificationCode); // Redis에 저장
        emailService.sendEmail(email, "인증 코드", "인증 코드: " + verificationCode);
        return "인증 코드가 이메일로 전송되었습니다.";
    }

    // 인증 코드 검증
    @PostMapping("/verify-email")
    public String verifyEmail(@RequestParam String email, @RequestParam String code) {
        if (emailService.verifyCode(email, code)) {
            return "이메일 인증이 완료되었습니다.";
        }
        return "인증 코드가 올바르지 않습니다.";
    }
}