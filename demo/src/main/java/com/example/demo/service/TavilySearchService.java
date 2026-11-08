package com.example.demo.service;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
public class TavilySearchService {

    @Value("${tavily.enabled:false}")
    private boolean enabled;

    @Value("${tavily.apiKey:}")
    private String apiKey;

    private final RestTemplate restTemplate = new RestTemplate();

    public Map<String, String> search(String query) {
        Map<String, String> results = new LinkedHashMap<>();

        if (!enabled) {
            return results;
        }

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(apiKey);

        Map<String, Object> body = new LinkedHashMap<>();
        body.put("query", query);
        body.put("max_results", 10);

        HttpEntity<Map<String, Object>> request = new HttpEntity<>(body, headers);

        ResponseEntity<Map<String, Object>> resp =
                restTemplate.postForEntity("https://api.tavily.com/search", request, (Class<Map<String, Object>>) (Class<?>) Map.class);
        Map<String, Object> respBody = resp.getBody();
        if (respBody == null) {
            return results;
        }

        Object itemsObj = respBody.get("results");
        if (itemsObj instanceof List) {
            List<?> items = (List<?>) itemsObj;
            for (Object itemObj : items) {
                if (itemObj instanceof Map) {
                    Map<String, Object> item = (Map<String, Object>) itemObj;
                    Object titleObj = item.get("title");
                    Object urlObj = item.get("url");
                    String title = titleObj == null ? null : titleObj.toString();
                    String url = urlObj == null ? null : urlObj.toString();
                    if (title != null && !title.isEmpty() && url != null && !url.isEmpty()) {
                        results.put(title, url);
                    }
                }
            }
        }

        return results;
    }
}