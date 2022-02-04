package io.sulmoon.userservice.util;

import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;


@Component
public class TokenRequestUtil {

    // create auth credentials
    @Value("${goorm.clientId}")
    private String username;
    @Value("${goorm.secret}")
    private String password;

    public String requestToken(String providerId) {

        try {
            // request url
            String url = "http://localhost:10000/oauth/token";

            // create headers
            HttpHeaders headers = new HttpHeaders();
            headers.setBasicAuth(username, password);
            headers.add("grant_type", "client_credentials");

            MultiValueMap<String, String> params = new LinkedMultiValueMap<>();
            params.add("grant_type", "client_credentials");

            // create request
            HttpEntity<MultiValueMap<String, String>> request = new HttpEntity<>(params, headers);

            // make a request
            ResponseEntity<String> response = new RestTemplate()
                    .exchange(
                            url,
                            HttpMethod.POST,
                            request,
                            String.class);

            JSONObject body = new JSONObject(response.getBody());
            String token = String.valueOf(body.getString("access_token"));

            return token;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}
