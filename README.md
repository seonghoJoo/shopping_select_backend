# shopping_select_backend
select to shop backend server
네이버 쇼핑에서 회원이 필요한 물품을 검색하고, 관심상품 등록시 최저가 갱신

## DB
![image](https://user-images.githubusercontent.com/32606456/153007632-8d1099d9-5bd3-45dd-9b6f-f4f6b3544950.png)

## 활용 기술
1. JPA
2. Spring Security
3. 카카오 로그인
4. AOP
5. Spring Batch - 최저가 갱신 

# JPA
Spring Data 활용
fetch join : N+1 문제 해결

# Spring Security
Role 부여 User, Admin에 따른 접근 controller가 다름

# 카카오 로그인
소셜 로그인 기능 구현, 일반 로그인도 함께 구현

# AOP
1. 유저별 API 활용 시간 체크
2. 예외처리로 인해 500대 에러 아닌 400대 에러로 client에게 다른 값을 받도록 유도할 수 있게 함. 

# Spring Batch
최저가 갱신 (개발 예정)
