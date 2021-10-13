# point-shop

포인트 기반 온라인 기반상점 토이프로젝트

## 사용 기술

* Java 11
* Spring Boot 2.5.5
* Spring Data JPA
* Spring Security
* MYSQL
* MAVEN
* DOCKER

## 깃 브랜칭 전략

* forking workflow   (
  링크 : [Forking Workflow](https://gmlwjd9405.github.io/2017/10/28/how-to-collaborate-on-GitHub-2.html))

* Team repository에 대한 TMI
    * team organization의 브랜치는 ```develop```과 ```master```를 운용합니다.
    * team organization의 default branch는 ```develop``` 를 운용하고, feature 개발과 관련된 Commit은 해당 브랜치에 머지 합니다.
    * ```2021-study:point-shop/develop``` pr의 경우 merge 전에 Code Review가 강제 됩니다.
    * PR에 대해서 동료들은 Approve나 comment를 수행할 수 있습니다.
    * ```2021-study:point-shop/develop``` PR merge는 PR 본인이 직접 peer review가 끝나고 직접 Merge를 하는걸 원칙으로 합니다

## 코딩 컨벤션

* Google Coding Convention 을 참고한다 ( 링크 : [Google Coding Convention](https://google.github.io/styleguide/javaguide.html))
* **[intelliJ에 Google Coding Format 적용하기](https://github.com/2021-study/point-shop/wiki/IntelliJ-Google-Code-Style-%EC%A0%81%EC%9A%A9-%EB%B0%A9%EB%B2%95)**

## 슬랙

* Webhok 기능을 통한 연동
