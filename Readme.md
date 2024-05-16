<h2 align="center">📚[ 도서 쇼핑몰 ] BookHub 📚</h2>



<br>

### 🐥 목차.
- [소개](#소개)
- [기술 스택](#기술-스택)
- [핵심 기능](#핵심-기능)
- [개선 사항](#개선-사항)

-----

<br>

### 📃 소개 <a name="소개"></a>

<br>

- 도서 쇼핑몰  BOOK HUB
- 프로젝트 기간 : 2024.04.8 ~ 2024.05.16
- 벤치마킹 사이트 :
  <a href = "https://www.yes24.com/main/default.aspx">  예스24  </a> ,
  <a href = "https://www.kyobobook.co.kr/" > 교보문고  </a>  ,
  <a href = "https://www.aladin.co.kr/home/welcome.aspx"> 알라딘  </a>  

  <br>

---

<br>

### ⚙ 기술 스택 <a name="기술-스택"></a>

<br>

<img src="https://img.shields.io/badge/Java_17-ED8B00?style=for-the-badge&logo=openjdk&logoColor=white"> &nbsp;
<img src="https://img.shields.io/badge/MySQL-00000F?style=for-the-badge&logo=mysql&logoColor=white"> &nbsp;
<img src="https://img.shields.io/badge/My Batis-색상?style=for-the-badge&logo=My Batis&logoColor=white"/> &nbsp;
<img src="https://img.shields.io/badge/Spring Boot-6DB33F.svg?style=for-the-badge&logo=Spring boot&logoColor=white" /> &nbsp;
<img src="https://img.shields.io/badge/Spring_Security-6DB33F?style=for-the-badge&logo=Spring-Security&logoColor=white" /> &nbsp;
<img src="https://img.shields.io/badge/Gradle-02303A.svg?style=for-the-badge&logo=Gradle&logoColor=white"/> &nbsp;

<img src="https://img.shields.io/badge/JavaScript-F7DF1E?style=for-the-badge&logo=Javascript&logoColor=white"> &nbsp;
<img src="https://img.shields.io/badge/jQuery-0769AD?style=for-the-badge&logo=jquery&logoColor=white"/> &nbsp;
<img src="https://img.shields.io/badge/bootstrap-7952B3?style=for-the-badge&logo=bootstrap&logoColor=white"> &nbsp;


<img src="https://img.shields.io/badge/draw.io-F08705?style=for-the-badge&logo=diagramsdotnet&logoColor=white"> &nbsp;
<img src="https://img.shields.io/badge/figma-F24E1E?style=for-the-badge&logo=figma&logoColor=white"> &nbsp;
<img src="https://img.shields.io/badge/fontawesome-528DD7?style=for-the-badge&logo=fontawesome&logoColor=white"> &nbsp;
<img src="https://img.shields.io/badge/notion-000000?style=for-the-badge&logo=notion&logoColor=white">

<br>

---

<br>

### 💡 핵심 기능 <a name="핵심-기능"></a>

- 로그인 / 로그아웃 
  - 스프링 시큐리티
  

- 회원가입 
  - Regex 정규표현식 
  - @valid 검증  
  - 비동기 아이디 중복체크 
  - 중복체크 사용자예외처리 클래스
  - 회원가입 완료시 이메일 전송
  

- 마이페이지 
  - 회원정보 조회 , 수정
  - 비동기 비밀번호 변경 
  - 주문내역 조회 , 주문상세내역 조회
  - 찜 목록
  - 회원 탈퇴
    - 회원 탈퇴시 로그인 불가 처리 
  - 1 : 1 문의 내역 조회
  - 비밀번호 찾기 , 임시비밀번호 생성 및 이메일 전송


<br>

--- 

<br>

### 💡 개선사항 <a name="개선-사항"></a><br>



- 회원 가입 완료시 `비동기 이메일 전송`  <a href="https://gi-dor.tistory.com/255" target="blank">스레드 풀 설정</a>
  - 비동기 처리 이후  `TPS  :  3231ms → 110ms`


- 1:1 문의사항 `INDEX 조회성능 개선`   <a href="https://gi-dor.tistory.com/252" target="blank">자세히 보기</a>
  - 카디널리티 수치 INDEX 설정
  - 평균 `TPS : 8.8ms  → 62.5ms` 로 개선


- 중요정보 암호화 처리  <a href="https://gi-dor.tistory.com/250" target="blank">자세히 보기</a>
  - application.properties에 저장된 DB 연결 정보를 `jasypt` 암호화를 통한 보안강화
