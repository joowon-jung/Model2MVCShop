# Model2MVCShop

> MVC 모델2 패턴 (2 Layer Architecture) 으로 구현하는 쇼핑몰 미니 프로젝트

---

# **📍 Refactoring 11단계**

## **01. 전체적인 코드 구현**

- 구현 되어 있는 공통 모듈과 회원 모듈 코드 분석 후, 상품 모듈 구현
- 구매 모듈은 Refactoring 단계를 거치며 틈틈히 구현 할 것

---

💡 20-10-29 ~ 20-10-30 : 회원 관리 코드 분석 완료

💡 20-10-30 ~ 20-11-02 : 상품 관리 구현 완료

💡 20-11-03 ~ 20-11-08 : 구매 관리 구현 완료

## **02. 검색조건 유지 & 페이지 나누기 & ROWNUM 이용한 효율적인 Query**

- 검색어 입력 시, 현재 페이지 말고 다음 페이지로 넘어갔을 때에도 검색 조건이 유지되도록 구현
- 현재 상태 : 모든 페이지가 한 줄로 출력됨. (ex 1 2 3 4 5 6 7 ...) 적당한 갯수 지정하여 페이지 나누기
- SQL ROWNUM 을 사용하여 효율적인 Query문 작성하기

---

💡 20-11-03 ~ 20-11-04 : 회원 관리 & 상품 관리 Refactoring 완료

💡 20-11-08 : 구매관리 Refactoring 완료

## **03. JSP에 EL, JSTL 적용 + @**

- JSP 파일 안의 자바코드를 EL, JSTL 을 이용하여 태그 중심으로 간결하게 구현
- 사용자/관리자 입장에서 UI 개선, 원하는 기능 틈틈히 추가 할 것

---

💡 20-11-06 : 회원 관리 & 상품 관리 Refactoring 완료

💡 20-11-08 : 구매관리 Refactoring 완료

💡 20-11-12 : 상품 삭제, 리뷰 관리 기능 추가

## **04. Spring, MyBatis 사용하여 Business Logic 구현 + JUnit**

- Spring Framework, MyBatis Framework 사용하여 Business Logic 구현
- JUnit Framework 사용하여 각 Component 별 단위 테스트 실행 (TDD)

---

💡 20-11-15 : 회원 관리 & 상품 관리 Refactoring 완료

💡 20-11-21 : 구매 관리 Refactoring 완료

## **05. AOP 적용, Transaction 처리하여 Business Logic 구현**

- Spring Framework 에서 지원하는 AOP 적용하여 로그 남기기, 선언적 Transaction 처리
- 관리 측면에서 Meta-Data (aspect/common/mybatis/transaction) 구분

---

💡 20-11-17 : 회원 관리 & 상품 관리 Refactoring 완료

💡 20-11-21 : 구매 관리 Refactoring 완료

## **06. Spring Web MVC 기반 Presentation Layer, Business Logic 구현**

- Spring Web MVC 적용하여 Annotation 기반 하나의 Controller로 구현
- 단위 테스트 되어있는 Business Logic (Model) 과 Controller 연결

---

💡 20-11-21 : 회원 관리 & 상품 관리 Refactoring 완료

💡 20-11-22 : 구매 관리 Refactoring 완료 

## **07. 다양한 뷰를 지원하기 위해 URL mapping 방식 .do에서 /로 변경**

- 기존의 *.do 매핑 방식은 웹 서비스만을 위한 매핑 방식이므로, 많은 뷰를 표현하기엔 부족
- 웹을 플랫폼화 하여 다양한 뷰를 지원하기 위해 매핑 방식 /로 변경 ex)  [http://ip:port](http://ip:port)/user/addUser

---

💡 20-11-24 : 회원 관리 & 상품 관리 & 구매 관리 Refactoring 완료

## **08. JSON 이용, RestFul Server-Client 구현**

- 단순히 Data 만을 JSON 형식으로 전달하여 현재 구축되어 있는 웹 서버내의 기능을 여러 뷰에 재활용 가능
- 다양한 브라우저와 안드로이폰, 아이폰과 같은 모바일 디바이스에서도 통신을 할 수 있게 Rest System 구현

---

💡 20-11-27 ~ :