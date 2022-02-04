package io.sulmoon.userservice.application.provider;

import org.json.JSONObject;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

@Component
public class OAuth2 {

    public OAuth2UserInfo getUserInfo(String token, String provider) {
        return getUserInfoByToken(token, provider);
    }

    private OAuth2UserInfo getUserInfoByToken(String accessToken, String provider) {
        // HttpHeader 오브젝트 생성
        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken);
        headers.add("Content-type", "application/x-www-form-urlencoded;charset=utf-8");

        // HttpHeader와 HttpBody를 하나의 오브젝트에 담기
        RestTemplate rt = new RestTemplate();
        HttpEntity<MultiValueMap<String, String>> profileRequest = new HttpEntity<>(headers);

        // Http 요청하기 - Post방식으로 - 그리고 response 변수의 응답 받음.
        ResponseEntity<String> response = null;

        JSONObject body;
        String id;
        String email = "";


        switch (provider) {

            case "kakao":

                response = rt.exchange(
                        "https://kapi.kakao.com/v2/user/me",
                        HttpMethod.POST,
                        profileRequest,
                        String.class
                );
                body = new JSONObject(response.getBody());
                id = "kakao_" + String.valueOf(body.get("id"));
                if (body.getJSONObject("kakao_account").has("email")) {
                    email = body.getJSONObject("kakao_account").getString("email");
                }
                break;

            case "naver":

                response = rt.exchange(
                        "https://openapi.naver.com/v1/nid/me",
                        HttpMethod.POST,
                        profileRequest,
                        String.class
                );
                body = new JSONObject(response.getBody());
                id = "naver_" + String.valueOf(body.getJSONObject("response").get("id"));
                if (body.has("email")) {
                    email = body.getString("email");
                }
                break;

            case "google":

                response = rt.exchange(
                        "https://oauth2.googleapis.com/tokeninfo",
                        HttpMethod.POST,
                        profileRequest,
                        String.class
                );
                body = new JSONObject(response.getBody());
                id = "google_" + String.valueOf(body.get("sub"));
                if (body.has("email")) {
                    email = body.getString("email");
                }
                break;

            default:
                throw new RuntimeException("로그인 제공자가 올바르지 않습니다.");
        }

        return new OAuth2UserInfo(id, email);
    }
}