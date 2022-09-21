# 1. 프로젝트 설명

* 구성
	* 멀티모듈로 구성하였으며, JPA Entity 및 Repository를 core에 별도로 분리하였습니다.
	* 분리 이유는 DB는 공통이므로 core에서 공통으로 Entity를 관리하기 위해서입니다.
* jar 파일은 용량제한으로 업로드가 불하가여 recruit@kakaobank.com 으로 전달드렸습니다.
* 실행
	* java -jar search-1.0.0.jar

------

# 2. 오픈소스 사용

* springfox : API 테스트 및 기능 검증을 위해 swagger-ui를 사용하였습니다.
* swagger-ui URL : http://localhost:8080/swagger-ui.html
    
------


# 3. API 명세서

### 1) 블로그 목록조회 API

* 설명
	* Kakao API 에러 시 Naver API를 조회하고 키워드를 저장합니다.
	* 카카오 및 네이버 블로그 조회 API도 각각 추가로 만들었습니다.
	* 블로그 조회 API는 총 3가지이지만 스펙은 모두 같습니다.

* Request
	* query : 검색어	
	* sort : 정렬 키	
	* page : Page No	
	* size : Per Page

* Response
    * name : 블로거 명
    * title : 블로그 명 (카카오 사용)
    * contents : 블로그 설명
    * link : 블로그 링크
    * bloggerLink : 블로그 명 (네이버 사용)
	* thumbnail : 블로그 썸네일 이미지 링크
    * date : 등록일


```sh
curl -X GET "http://localhost:8080/search/blogs?query=test&sort=accuracy&page=1&size=10" -H "accept: application/json"

curl -X GET "http://localhost:8080/search/kakao/blogs?query=test&sort=accuracy&page=1&size=10" -H "accept: application/json"

curl -X GET "http://localhost:8080/search/naver/blogs?query=fszf&sort=sim&page=1&size=10" -H "accept: application/json"
```
	
------

### 2) 인기 검색어 목록조회 API 

* 설명
	* 블로그 검색 키워드를 누적 많은 순서로 10개를 정렬하여 제공합니다.

* Request
	* 없음

* Response
    * keyword : 검색어
    * search_count : 누적 검색 수


```sh
curl -X GET "http://localhost:8080/search/history" -H "accept: application/json"
```

------
