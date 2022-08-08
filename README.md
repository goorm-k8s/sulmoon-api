# sulmoon-api

- 쿠버네티스를 활용하여 대규모 분산처리를 적용한 고가용성 설문조사 플랫폼
- 백엔드와 프론트엔트를 분리한 클라이언트 사이드 렌더링 방식
- 개발 인원 총 3명 (프론트엔트 1, 서버/백엔드 1, 인프라 1), 서버/백엔드 담당

### 📒 [개발문서](https://spotless-kingfisher-8db0.notion.site/Sulmoon-606e813e50614570b9ef149f056425d3) (API 설계, DB 설계, 화면 설계, Convention 등)

## 진행 기간: 2022.01 ~ 2022.02

## 시연영상

[https://www.youtube.com/watch?v=rbDvhm1fPlc](https://www.youtube.com/watch?v=rbDvhm1fPlc)

## 사용 기술

- Java 11 - 개발 언어
- React - 프론트엔드단
- Spring Boot - 웹 프레임워크
- Spring Data JPA - JPA(Hibernate) : 자바 ORM 기술 표준
- Spring Security - 인증&인가 프레임워크
- Gradle - 의존성 관리 프로그램
- MariaDB(AWS RDS) - 데이터베이스
- Git - 형상관리
- AWS S3, CloudFront - 프론트엔드 서버 인프라
- AWS Route 53 - 서버 인프라
- JMeter - 부하 테스트
- Docker, EKS -  컨테이너 오케스트레이션
- Jenkins, argoCD - 빌드, 배포 자동화
- Grafana, Prometheus - 모니터링

## ERD

![goorm-sulmoonv5.png](https://user-images.githubusercontent.com/64997244/183346397-f0fabf33-15e0-487e-8d73-87f127039a4b.png)

## 서비스 동작

![Sulmoon서비스 동작.png](https://user-images.githubusercontent.com/64997244/183346379-609e1c2f-77d4-4150-9b02-3d7e6c25f06e.png)

## 애플리케이션 아키텍처

![Sulmoon애플리케이션 아키텍처.png](https://user-images.githubusercontent.com/64997244/183346380-2b80f9c3-bc62-479c-a0d1-65468c424186.png)

## 인프라 아키텍처

![Sulmoon인프라 아키텍처.png](https://user-images.githubusercontent.com/64997244/183346381-52b8ab78-a218-486b-9b8e-070f662af53e.png)

## CI/CD

![SulmoonCICD.png](https://user-images.githubusercontent.com/64997244/183346374-6c408e56-1363-4efb-8000-23851f5d2527.png)

## 모니터링

![Sulmoon모니터링2.png](https://user-images.githubusercontent.com/64997244/183346378-2842118c-76b9-4597-8e23-891ba36db2f5.png)

![Sulmoon모니터링1.png](https://user-images.githubusercontent.com/64997244/183346377-ab9c481a-c5ec-4486-8ef4-dbe19335ac06.png)

## 담당 기능

- git-flow, branch 전략, git/code 컨벤션 등을 사용하여 팀 프로젝트 개발 프로세스 정립
- JPA 엔티티 설계와 구현
- Junit5 & Mockito 70여개의 단위/통합 테스트
- DB 설계 및 ERD 작성
- 도메인 별로 독립적으로 애플리케이션을 구성하여 API로 통신하는 MSA전략 선택
- Spring Security 와 JWT를 이용해 로그인
- OAuth2 서버 구축
- JMeter로 유저 10,000 명까지 부하 테스트
- API 명세서, 기능 명세서, ERD 등 개발 문서 작성
