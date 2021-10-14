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


## Docker

* 도커 컨테이너 실행하기
```bash
$ cd $PROJECT_DIR/docker
$ docker-compose up -d
```

* 컨테이너 종료
  * Volumn을 초기화 하지 않으면 남은 데이터는 저장됨

```bash
$ docker-compose down
```
* 볼륨 마운트까지 제거시 ```-v``` 옵션 추가
  * 이러면 DB에 저장된 값도 날라감.

```bash
$ docker-compose down -v
```

## mysql
* 로컬에 Mysql 서버를 이용 하지 않을 분들은 docker Container를 사용해주세요
* 포트는 mysql 로컬 포트는 3306 입니다.
* 로컬에 Mysql 서버를 이미 쓰시는 분은 3306포트가 선점되어 있어서 Container가 정상적으로 뜨지 않을 수 있습니다.
  * application.yml이나 docker-compose 스크립트의 host port를 바꾸지 마시고, 둘중 하나를 사용하시는걸 권장드립니다.
  
-------

# 프로젝트 관련 정보 
## 요구 사항
* https://docs.google.com/spreadsheets/d/1oOJCVsUSJKi5_rX3wWXLPg0Vkb6J2mU7tJAOGybK0zc/edit?usp=sharing