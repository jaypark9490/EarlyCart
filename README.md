# EarlyCart
2024 공주대학교 소프트웨어학과 캡스톤디자인프로젝트

# API Reference

## 사용방법
안드로이드 프로젝트에 model 패키지(폴더)를 만들고 [model](https://github.com/jaypark9490/EarlyCart/tree/main/src/main/java/server/earlycart/model) 해당 파일들을 모두 import

[HttpResult.class]() 파일을 안드로이드 프로젝트에 import

HttpResult.class 에 IP는 158.180.94.14 로 변경후 사용

ex)

String session = new HttpResult().execute("user/login?id=user&pw=1234").get();

User user = new ObjectMapper().readValue(new HttpResult().execute("user?session=" + session).get(), User.class);

Item item = new ObjectMapper().readValue(new HttpResult().execute("item?id=10").get(), Item.class);

ArrayList<Item> itemList = new ObjectMapper().readValue(new HttpResult().execute("category").get(), new TypeReference<ArrayList<Item>>() {});


## 회원
### 회원가입

user/register?id=아이디&pw=비밀번호&name=이름&birth=생년월일&phone=전화번호

ex) http://158.180.94.14/user/register?id=user&pw=1234&name=박재정&birth=1999-06-02&phone=010-3562-6667

**Response Code**

0 - 회원가입 실패, 오류

1 - 회원가입 성공

2 - 중복된 아이디

### 로그인 세션
user/login?id=아이디&pw=비밀번호

ex) http://158.180.94.14/user/login?id=user&pw=1234

**Response Code**

0 - 로그인 실패, 오류

세션값 - 로그인 성공, 세션값 반환, 문자열

### 회원정보
user?session=세션값

ex) http://158.180.94.14/user?session=Dg9FdgY2Tsh

**Response Code**

0 - 잘못된 세션, 오류

User - 회원정보 반환, JSON3

## 상품
### id 상품 검색

item/{id}

ex) http://158.180.94.14/item/70

**Response Code**

null - 오류

Item - 상품 반환, JSON

### 카테고리별 상품 리스트

item/category/{category}

ex) http://158.180.94.14/item/category/정육_축산

**Response Code**

null - 오류

Item List - 상품 리스트 반환, JSON

### name 상품 검색

item/search/{name}

ex) http://158.180.94.14/item/search/고기

**Response Code**

null - 오류

Item List - 상품 리스트 반환, JSON