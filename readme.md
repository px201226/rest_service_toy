# readme

## Introduction

> Spring boot 를 사용하여 REST API를 설계 및 구축하고 문서화하여, Vue.js 와 연동하여 랜덤 소개팅 웹 어플리케이션을 제작한다.

## 1. Entity Relationship Diagram

![readme%20da68657836ec4ff89723b46bbaf27ba3/erd.png](readme%20da68657836ec4ff89723b46bbaf27ba3/erd.png)

## 2. Class Diagram

![readme%20da68657836ec4ff89723b46bbaf27ba3/rest.png](readme%20da68657836ec4ff89723b46bbaf27ba3/rest.png)

## 3. Deploy Flow

![readme%20da68657836ec4ff89723b46bbaf27ba3/Untitled.png](readme%20da68657836ec4ff89723b46bbaf27ba3/Untitled.png)

## 4. 기술

### **SpringBoot**

- Java 8
- Gradle
- SpringBoot
- Spring Data JPA
- Spring Web MVC
- Spring Security Oauth2
- Spring Rest HATEOAS
- Spring Rest Docs

### **Vue**

- Vue
- Vuex
- Vue-Router
- Axios
- Vuetify

### CI/CD

- Travic-CI
- AWS CodeDeploy

## 5. 구현

### [1] 보안 및 인증

- Spring Security 와 OAuth2 Server 구축으로 Access Token 및 Refresh Token을 사용하여 사용자 인증 및 인가
- JWT Token을 사용한 인증 시스템

### [2] 데이터 처리

- Spring Data JPA 와 Maria DB를 사용하여 데이터를 저장하고 처리

### [3] REST API 구축

- Vue 와 Spring boot 서버가 통신하기 위해 REST API 설계
- /v1/posts, /v1/comments, /v1/matching 와 같이 API 버전 관리
- Spring Rest Docs과 테스트 코드를 이용하여 최신화된 API Document 제공
- [Rest API Document 바로가기](http://ec2-13-125-170-210.ap-northeast-2.compute.amazonaws.com:8080/docs/index.html#overview)

### [4] 매칭 알고리즘 구현

- `MatchingScoreComparable`인터페이스를 통해 매칭알고리즘과 도메인 객체 분리
- 매칭 알고리즘은 현재는 데모버전이므로 랜덤으로 매칭되게 한다.
- `Spring Scheduler` 을 이용하여 일주일에 한번 매칭데이터를 일괄 처리

### [5] Vue.js로 프론트엔드 개발

- `Vuetify`를 이용한 UI 개발
- `Vuex`로 어플리케이션 상태 관리
- `Axios`로 REST API와 HTTP 통신

### [6] CI/CD 구축

- Github Repository 로 버전 관리
- Travis-CI 를 사용해 Git의 Master branch가 push 될 때 마다, 자동으로 빌드되는 환경 구현
- Travis-CI 와 AWS S3, CodeDeploy와 연동
- 리눅스 쉘스크립트를 작성 후 EC2 인스턴스에 자동으로 배포되도록 구현

### [7] 무중단 배포 설정

- Nginx 와 스프링 서버 연동
- 무중단 배포를 하기 위한 profiles 설정 및 스크립트 작성

# 6. 설명

## [1] 회원가입

![readme%20da68657836ec4ff89723b46bbaf27ba3/1.png](readme%20da68657836ec4ff89723b46bbaf27ba3/1.png)

![readme%20da68657836ec4ff89723b46bbaf27ba3/2.png](readme%20da68657836ec4ff89723b46bbaf27ba3/2.png)

소개팅에 필요한 프로필 및 원하는 이상형을 입력받아 회원가입을 합니다.

## [2] 메인 게시판

![readme%20da68657836ec4ff89723b46bbaf27ba3/3.png](readme%20da68657836ec4ff89723b46bbaf27ba3/3.png)

![readme%20da68657836ec4ff89723b46bbaf27ba3/4.png](readme%20da68657836ec4ff89723b46bbaf27ba3/4.png)

연애 및 소개팅과 관련된 내용을 자유롭게 게시할 수 있도록 하였습니다. 

`댓글 시스템`과 `게시물 좋아요` 기능을 구현하였습니다.

## [3] 매칭 서비스

![readme%20da68657836ec4ff89723b46bbaf27ba3/5.png](readme%20da68657836ec4ff89723b46bbaf27ba3/5.png)

소개팅은 **1주일에 한번씩 일괄적**으로 진행되며, 소개팅 신청을 한 회원들 사이에서 이루어집니다.

`소개팅 신청하기→` 버튼을 눌러 소개팅을 신청할 수 있고, 소개팅이 진행되는 날까지 남은 시간이 상단 보라색 패널에 표시됩니다.

후 에, `결과확인→` 버튼을 통해 소개팅 결과를 조회할 수 있고, 소개팅이 매칭된다면 상대방 카카오톡 ID를 알 수 있습니다.

## [4] 마이페이지

![readme%20da68657836ec4ff89723b46bbaf27ba3/6.png](readme%20da68657836ec4ff89723b46bbaf27ba3/6.png)

회원의 프로필 수정 및 활동 이력을 조회할 수 있습니다.

## 7. 해야할 일

- [ ]  매칭 알고리즘 고도화
- [ ]  Spring Scheduler 를 통해 데이터를 일괄처리하는 것을 Spring Batch로 변경

(일괄처리 하는 도중 예외 및 에러처리, 신뢰성 등의 이유로)

- [ ]  게시물 좋아요 기능에 cache 구현

(현재는 DB에 바로 입출력하기 때문에 부하가 많다)