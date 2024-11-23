package boosters.fundboost.company.auth.business.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Map;

import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;

@Service
public class BusinessVerificationService {
    private final RestTemplate restTemplate;
    private final ObjectMapper objectMapper;

    private final String serviceKey;
    private final String apiUrl;

    public BusinessVerificationService(RestTemplate restTemplate, ObjectMapper objectMapper, @Value("${api.businessman.serviceKey}") String serviceKey) {
        this.restTemplate = restTemplate;
        this.objectMapper = objectMapper;

        this.serviceKey = URLDecoder.decode(serviceKey, StandardCharsets.UTF_8);
        this.apiUrl = "https://api.odcloud.kr/api/nts-businessman/v1/status?serviceKey=" + this.serviceKey;
    }

    public Map<String, Object> verifyBusiness(String businessNumber) throws Exception {
        Map<String, Object> requestBody = Collections.singletonMap("b_no", Collections.singletonList(businessNumber));

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setAccept(Collections.singletonList(MediaType.APPLICATION_JSON));

        HttpEntity<Map<String, Object>> requestEntity = new HttpEntity<>(requestBody, headers);

        String response = restTemplate.postForObject(apiUrl, requestEntity, String.class);

        return objectMapper.readValue(response, Map.class);
    }
}