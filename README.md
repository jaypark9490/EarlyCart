# EarlyCart
2024 공주대학교 소프트웨어학과 캡스톤디자인프로젝트

# API Reference

## 회원가입
user/register?id=아이디&pw=비밀번호&name=이름&birth=생년월일&phone=전화번호

ex) http://123.123.123.123/register?id=user&pw=1234&name=박재정&birth=1999-06-02&phone=010-3562-6667

**Response Code**

0 - 회원가입 실패, 오류

1 - 회원가입 성공

2 - 중복된 아이디

## 로그인
### 로그인 세션
user/login?id=아이디&pw=비밀번호

ex) http://123.123.123.123/login?id=user&pw=1234

**Response Code**

0 - 로그인 실패, 오류

세션값 - 로그인 성공, 세션값 반환, 문자열

### 회원정보
user?session=세션값

ex) http://123.123.123.123/user?session=Dg9FdgY2Tsh

**Response Code**

null - 잘못된 세션, 오류

User - 회원정보 반환, JSON