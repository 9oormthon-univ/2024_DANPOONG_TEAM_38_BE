package boosters.fundboost.company.service;

import boosters.fundboost.company.converter.CompanyRankingConverter;
import boosters.fundboost.company.domain.Company;
import boosters.fundboost.company.dto.response.CompanyRankingRecord;
import boosters.fundboost.company.dto.request.CompanyLoginRequest;
import boosters.fundboost.company.dto.request.CompanyRankingRequest;
import boosters.fundboost.company.dto.request.CompanyRegisterRequest;
import boosters.fundboost.company.dto.response.CompanyRankingResponse;
import boosters.fundboost.company.exception.CompanyException;
import boosters.fundboost.company.repository.CompanyRepository;
import boosters.fundboost.company.auth.email.service.EmailService;
import boosters.fundboost.global.response.code.status.ErrorStatus;
import boosters.fundboost.global.security.jwt.JwtTokenProvider;
import boosters.fundboost.user.domain.User;
import boosters.fundboost.user.domain.enums.Tag;
import boosters.fundboost.user.domain.enums.UserType;
import boosters.fundboost.user.repository.UserRepository;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${jwt.refresh-token-expiration}")
    private long refreshTokenDuration;

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
        if (!emailService.isEmailVerified(request.getEmail())) {
            throw new CompanyException(ErrorStatus.UNAUTHORIZED_USER);
        }

        if (userRepository.findByEmail(request.getEmail()).isPresent()) {
            throw new CompanyException(ErrorStatus.USER_NOT_FOUND);
        }

        if (companyRepository.findByBusinessNumber(request.getBusinessNumber()).isPresent()) {
            throw new CompanyException(ErrorStatus.COMPANY_NOT_FOUND);
        }

        User user = User.builder()
                .email(request.getEmail())
                .userType(UserType.COMPANY)
                .tag(Tag.SEASSAK_INVESTOR)
                .build();
        userRepository.save(user);

        Company company = Company.builder()
                .user(user)
                .businessNumber(request.getBusinessNumber())
                .password(passwordEncoder.encode(request.getPassword()))
                .build();
        companyRepository.save(company);
    }

    public String[] loginCompany(CompanyLoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new CompanyException(ErrorStatus.USER_NOT_FOUND));

        Company company = companyRepository.findByUser(user)
                .orElseThrow(() -> new CompanyException(ErrorStatus.COMPANY_NOT_FOUND));

        if (!passwordEncoder.matches(request.getPassword(), company.getPassword())) {
            throw new CompanyException(ErrorStatus.UNAUTHORIZED_USER);
        }

        String accessToken = jwtTokenProvider.createToken(user.getEmail());
        String refreshToken = jwtTokenProvider.createRefreshToken(user.getEmail());

        saveRefreshToken(user.getEmail(), refreshToken, refreshTokenDuration);

        return new String[]{accessToken, refreshToken};
    }

    public void saveRefreshToken(String email, String refreshToken, long refreshTokenValidity) {
        String redisKey = "refresh_token:" + email;
        redisTemplate.opsForValue().set(
                redisKey,
                refreshToken,
                refreshTokenValidity,
                TimeUnit.MILLISECONDS
        );
    }

    public String refreshToken(String email, String refreshToken) {
        String redisKey = "refresh_token:" + email;
        String storedRefreshToken = redisTemplate.opsForValue().get(redisKey);

        if (storedRefreshToken == null || !storedRefreshToken.equals(refreshToken)) {
            throw new CompanyException(ErrorStatus.UNAUTHORIZED_USER);
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

    public Company findById(Long companyId) {
        return companyRepository.findById(companyId)
                .orElseThrow(() -> new CompanyException(ErrorStatus.COMPANY_NOT_FOUND));
    }
}
