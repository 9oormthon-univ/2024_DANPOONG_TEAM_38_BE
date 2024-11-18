package boosters.fundboost.company.auth.business.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.Collections;
import java.util.Map;

@Service
public class BusinessVerificationService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    @Value("${api.businessman.serviceKey}")
    private String serviceKey;

    @Value("${api.businessman.url}")
    private String apiUrl;

    public String getApiUrlWithKey() {
        return apiUrl + "?serviceKey=" + serviceKey;
    }

    public BusinessVerificationService(RestTemplate restTemplate, ObjectMapper objectMapper) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;
    }

    public Map<String, Object> verifyBusiness(String businessNumber) throws Exception {
        // 요청 Body 생성
        Map<String, Object> requestBody = Collections.singletonMap("b_no", Collections.singletonList(businessNumber));

        // HTTP Header 설정
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        // 요청 엔티티 생성
        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        // API 호출
        String response = restTemplate.postForObject(apiUrl, requestEntity, String.class);

        // JSON 응답 변환
        return objectMapper.readValue(response, Map.class);
    }
}