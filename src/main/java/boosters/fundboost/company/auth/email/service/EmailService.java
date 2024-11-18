package boosters.fundboost.company.auth.email.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Random;
import java.util.concurrent.TimeUnit;

@Service
public class EmailService {
    private final JavaMailSender mailSender;
    private final RedisTemplate<String, String> redisTemplate;

    public EmailService(JavaMailSender mailSender, RedisTemplate<String, String> redisTemplate) {
        this.mailSender = mailSender;
        this.redisTemplate = redisTemplate;
    }

    // 인증 코드 생성
    public String generateVerificationCode() {
        Random random = new Random();
        int code = 100000 + random.nextInt(900000); // 6자리 랜덤 숫자
        return String.valueOf(code);
    }

    // 인증 코드 저장 (Redis)
    public void saveVerificationCode(String email, String code) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        valueOperations.set(email, code, 5, TimeUnit.MINUTES); // 5분 만료 시간 설정
    }

    // 인증 코드 확인
    public boolean verifyCode(String email, String code) {
        ValueOperations<String, String> valueOperations = redisTemplate.opsForValue();
        String storedCode = valueOperations.get(email);
        return storedCode != null && storedCode.equals(code);
    }

    // 이메일 전송
    public void sendEmail(String to, String subject, String text) throws MessagingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, "UTF-8");
        helper.setTo(to);
        helper.setSubject(subject);
        helper.setText(text, true); // HTML 형식 가능
        mailSender.send(message);
    }

    public boolean isEmailVerified(String email) {
        return redisTemplate.hasKey(email); // 이메일이 Redis에 존재하면 인증됨
    }
}