package boosters.fundboost.company.service;

import boosters.fundboost.company.converter.CompanyRankingConverter;
import boosters.fundboost.company.domain.Company;
import boosters.fundboost.company.dto.response.CompanyRankingRecord;
import boosters.fundboost.company.dto.request.CompanyLoginRequest;
import boosters.fundboost.company.dto.request.CompanyRankingRequest;
import boosters.fundboost.company.dto.request.CompanyRegisterRequest;
import boosters.fundboost.company.dto.response.CompanyRankingResponse;
import boosters.fundboost.company.repository.CompanyRepository;
import boosters.fundboost.company.auth.email.service.EmailService;
import boosters.fundboost.global.security.jwt.JwtTokenProvider;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.domain.enums.Tag;
import boosters.fundboost.user.domain.enums.UserType;
import boosters.fundboost.user.repository.UserRepository;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class CompanyService {
    private final UserRepository userRepository;
    private final CompanyRepository companyRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;
    private final RedisTemplate<String, String> redisTemplate;
    private final EmailService emailService;

    public CompanyService(UserRepository userRepository, CompanyRepository companyRepository,
                          PasswordEncoder passwordEncoder, JwtTokenProvider jwtTokenProvider,
                          RedisTemplate<String, String> redisTemplate, EmailService emailService) {
        this.userRepository = userRepository;
        this.companyRepository = companyRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtTokenProvider = jwtTokenProvider;
        this.redisTemplate = redisTemplate;
        this.emailService = emailService;
    }

    public void registerCompany(CompanyRegisterRequest request) {
        // EmailService를 통해 이메일 인증 여부 확인
//    if (!emailService.isEmailVerified(request.getEmail())) {
//        throw new IllegalArgumentException("이메일 인증이 필요합니다.");
//    }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new IllegalArgumentException("이미 사용 중인 이메일입니다.");
        }

        if (companyRepository.findByBusinessNumber(request.getBusinessNumber()).isPresent()) {
            throw new IllegalArgumentException("이미 등록된 사업자 번호입니다.");
        }

        // User 생성 및 저장
        User user = User.builder()
                .email(request.getEmail())
                .userType(UserType.COMPANY) // Enum 값 설정
                .tag(Tag.SEASSAK_INVESTOR)
                .build();
        userRepository.save(user);

        // Company 생성 및 저장
        Company company = Company.builder()
                .user(user)
                .businessNumber(request.getBusinessNumber())
                .password(passwordEncoder.encode(request.getPassword())) // 비밀번호 암호화 후 설정
                .build();
        companyRepository.save(company);
    }


    public String[] loginCompany(CompanyLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("잘못된 이메일입니다."));

        Company company = companyRepository.findByUser(user)
                .orElseThrow(() -> new IllegalArgumentException("회사 정보를 찾을 수 없습니다."));

        if (!passwordEncoder.matches(request.getPassword(), company.getPassword())) {
            throw new IllegalArgumentException("잘못된 비밀번호입니다.");
        }

        String accessToken = jwtTokenProvider.createToken(user.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());

        redisTemplate.opsForValue().set(
                user.getEmail(),
                refreshToken,
                jwtTokenProvider.getRefreshTokenValidity(),
                TimeUnit.MILLISECONDS
        );
        return new String[]{accessToken, refreshToken};
    }


    public String refreshToken(String email, String refreshToken) {
        String redisKey = "refresh_token:" + email;
        String storedRefreshToken = redisTemplate.opsForValue().get(redisKey);

        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new IllegalArgumentException("유효하지 않은 리프레시 토큰입니다.");
        }

        return jwtTokenProvider.createToken(email);
    }

    public void logout(String email) {
        String redisKey = "refresh_token:" + email;
        redisTemplate.delete(redisKey);
    }

    public List<CompanyRankingResponse> getRanking(CompanyRankingRequest companyRankingRequest) {
        Map<Company, CompanyRankingRecord> companies = companyRepository.findCompanies(companyRankingRequest.sortType());

        return companies.entrySet().stream()
                .map(entry -> CompanyRankingConverter.toCompanyRankingResponse(entry.getKey(), entry.getValue()))
                .collect(Collectors.toList());
    }
}
