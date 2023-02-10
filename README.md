# 1. 아이템 선정

CRUD 게시판을 만들고 포인트를 이용하여 사용자 권한에 따라 접근할 수 있는 게시판을 제작한다.

검색 기능을 통해 자주 검색되는 단어를 모아볼 수 있으며 추천을 많이 받은 게시글을 조회할 수 있다.

# 2. 개요
- 프로젝트 명 : BoardService
- 개발 인원 : 1명 (허진성)
- 개발 기간 : 2023—01-13 ~ 02-09(진행중)
- 주요 기능 :
    - 게시판 - CRUD 기능, 추천, 조회수, 페이징, 검색 기능, 인기 게시글 조회, 인기 검색어 조회
    - 댓글 - CRUD 기능
    - 사용자 - Oauth 2.0 로그인, 구글,  로그인, 회원 정보 수정, 유효성 및 중복 검사, Security 로그인
    - 포인트 - 포인트를 이용하여 회원 등급에 따른 게시판 접근 기능
- 개발 언어 : JAVA 11
- 개발 환경 : SpringBoot 2.7.5, gradle 7.2, Srping Data Jpa, Spring Security, Spring OAuth 2.0, mustache
- 데이터 베이스 : MySql
- 형상관리 : Github
- 간단 소개 : 웹의 기본기 함양을 키우기 위한 CRUD 게시판

# 3. 요구사항 분석
1. 회원 가입 페이지
- 유효성 검사
    - 닉네임은 최소한 2 ~ 10자 사이이며, 특수문자를 제외한 한글 (ㄱ~ㅎ, 가~힣), 알파벳 대소문자(a~z, A~Z), 숫자(0~9)로 구성
    - 이메일 형식 패턴 적용해 확인
    - 한 개의 칸이라도 공백 혹은 빈칸이 있는지 확인하고 있다면, "OOO는 필수 입력 값입니다."의 메시지 보여주기
    - 비밀번호는 최소 8~16자 이상이며, 영문 대 소문자, 숫자, 특수문자를 사용하게 하기
    
- 중복 확인
    - 데이터베이스에 존재하는 아이디를 입력한 채 회원가입 버튼을 누른 경우 "이미 사용중인 아이디입니다."의 메시지를 보여주기
    - 데이터베이스에 존재하는 이메일을 입력한 채 회원가입 버튼을 누른 경우 "이미 사용중인 이메일입니다."의 메시지를 보여주기
    - 데이터베이스에 존재하는 닉네임을 입력한 채 회원가입 버튼을 누른 경우 "이미 사용중인 닉네임입니다."의 메시지를 보여주기
    - 모든 검사가 통과되었다면 로그인 페이지로 이동시키기
    
2. 로그인 페이지
- 검증되지 않은 회원일 경우 아래의 페이지만 이용 가능
    - 회원가입 페이지
    - 로그인 페이지
    - 자유 게시판 목록 조회 페이지
    - 자유 게시판 상세보기 페이지
    - 게시글 검색 페이지
    - 그 외 로그인을 하지 않거나 올바르지 않은 경로로 접속한 사용자가 로그인이 필요한 경로에 접속한 경우 로그인 페이지로 이동
    
- 로그인 검사
    - 아이디와 비밀번호가 일치하지 않을 시 "아이디 또는 비밀번호가 일치하지 않습니다. "의 메시지를 보여주기
    - 이외의 다른 에러 메시지 또한 처리하기
    - 모든 검사가 통과되었다면 로그인 후 main 페이지로 이동시키기
    
3. 회원 정보 수정
- 회원정보 수정은 닉네임, 비밀번호만 가능
- 수정란이 빈칸 혹은 공백으로 된 경우 “공백 또는 입력하지 않은 부분이 있습니다.”의 메시지 보여주기
- 닉네임이 중복확인을 통해 중복일 경우 “이미 사용중인 닉네임입니다.” 메시지 보여주기
- 닉네임은 최소 2~10자이며, 특수문자를 제외한 한글 (ㄱ~ㅎ, 가~힣), 알파벳 대소문자(a~z, A~Z), 숫자(0~9)만 가능
- 비밀번호 수정 또한 최소 8자~16자이며, 영문 대 소문자, 숫자, 특수문자를 사용하게 하기
- 수정 완료 시 수정 날짜 업데이트해주기

4. 소셜 로그인 기능
- 구글, 네이버를 이용한 로그인
- 데이터베이스에 이미 존재하는 이메일이 있을 시 기존 회원 데이터 정보를 유지하기
- 소셜 로그인 사용자도 닉네임, 비밀번호 설정이 가능하고, 일반 로그인도 가능하게 하기
    
    

5. 게시글 검사

- 게시글 작성, 수정 시 제목과 내용은 공백 혹은 빈칸으로 작성하지 않도록 하기
- 내가 작성한 글만 수정, 삭제 가능하게 하기
- 로그인을 하지 않고 글 작성 버튼을 누른 경우 로그인 페이지로 이동
- 내가 작성한 게시글에 대해서는 조회수 증가하지 않게하기
    
    
6. 댓글 검사
- 댓글은 로그인한 사용자만 작성 가능하게 하기
- 댓글 작성 및 수정시 빈칸 혹은 공백으로 된 경우 “공백 또는 입력하지 않은 부분이 있습니다”의 메시지 보여주기
- 댓글 수정 및 삭제는 해당 댓글 작성자만 가능하게 하기
- 게시글 삭제 시 해당 댓글 데이터도 같이 삭제되게 하기

7. 포인트
- 일반 유저의 경우 모두 포인트 0점으로 시작
- 게시글 작성 시 + 100점, 댓글 작성시 + 50점
- 포인트가 총 200점이 넘으면 일반 유저에서 VIP 유저로 승급되며 비밀 게시판 이용 가능하게 하기

8. 게시글 추천/비추천
- 추천 및 비추천은 각 게시글당 1번씩만 누를 수 있게 하기
- 추천/비추천을 누른 상태에서 추천/비추천을 누르면 추천/비추천을 감소하기
- 추천/비추천을 누른 상태에서 비추천/추천 을 누르면 무효

9. 인기 게시판
- 추천수를 많이 받은 게시글이 상위에 정렬되게 하기
- 만약 추천수가 같을 경우 비추천수가 적은 순으로 정렬되게 하기
- 만약 비추천수가 같을 경 생성일을 오름차순으로 정렬되게 하기
- 10개씩 페이징하여 출력

10. 검색어 순위
- 검색어 순위는 검색량이 많은 단어에 대해 상위 10개만 페이징 없이 출력하기
- 검색한 단어가 10개 미만일 경우 해당 갯수만큼 랭킹 번호 삽입하기
- (현재는 MySql로 구현했으나 Redis를 조금 더 공부해서 구현하고 싶은 기능)

# 4. DB 설계

![Untitled](https://user-images.githubusercontent.com/61839458/217808842-ecdccaba-2bcc-4867-90e2-44310cfb1741.png)

# 5. API 설계
https://www.notion.so/5-API-85fc5b625484446bbc3bea6631401058
![image](https://user-images.githubusercontent.com/61839458/218073483-33e1517e-96e2-4933-af0f-f4ca8fb696db.png)

![image](https://user-images.githubusercontent.com/61839458/218073552-91a5986b-4091-473b-9422-fd5503f3db86.png)

![image](https://user-images.githubusercontent.com/61839458/218073606-300ae731-d184-4ed3-9e26-9ae189629ec7.png)

![image](https://user-images.githubusercontent.com/61839458/218073658-aee28141-2f99-4aa1-84a9-80a198d99e53.png)


# 6. 화면설계서
https://www.notion.so/6-e8de440614224155b6e79c13f79f79e6

![image](https://user-images.githubusercontent.com/61839458/218073836-e765ad5d-4160-41da-a573-927fc85bddaf.png)
![image](https://user-images.githubusercontent.com/61839458/218073877-33dfdf5f-a8f3-416a-920b-24afdfe1efeb.png)
![image](https://user-images.githubusercontent.com/61839458/218073927-4aaf5543-25dd-4992-9ce5-33a114d8e9b0.png)
![image](https://user-images.githubusercontent.com/61839458/218073998-e8335a0b-ca79-4278-8c5e-9face658d86c.png)
![image](https://user-images.githubusercontent.com/61839458/218074046-dc8aaa2b-f88f-485b-8b3e-71f18356c8a6.png)
![image](https://user-images.githubusercontent.com/61839458/218074084-4afc326b-5291-4334-b81b-8293a21ccc16.png)
![image](https://user-images.githubusercontent.com/61839458/218074108-edf6d537-f920-4fdd-85f2-fc83429bed18.png)
![image](https://user-images.githubusercontent.com/61839458/218074145-d2d67547-2350-43ea-8927-37cc30eed4e9.png)
![image](https://user-images.githubusercontent.com/61839458/218074189-a6c7b64d-7af5-49b2-a0da-529a965d2f4d.png)
![image](https://user-images.githubusercontent.com/61839458/218074230-9a6ec9b6-63ec-4eaf-9b9a-42dc4d721d26.png)
![image](https://user-images.githubusercontent.com/61839458/218074265-88aa69b9-f29a-48bf-b215-9f24eff9eeb4.png)
![image](https://user-images.githubusercontent.com/61839458/218074297-03801897-316e-407c-85b8-4c1ace06f186.png)
![image](https://user-images.githubusercontent.com/61839458/218074332-8286f85d-2df3-40be-a8b9-a7882446505d.png)
![image](https://user-images.githubusercontent.com/61839458/218074369-41da7397-f4a7-4770-8e49-2c39483cdda3.png)

# 7. 에러 사항 및 해결법과 고민들
https://www.notion.so/43dd746d01cd45d2824339b361442c34?v=c58ec3dec8aa4497ad00a08cb41a67c1
![image](https://user-images.githubusercontent.com/61839458/218074736-530576f8-3f86-4b5f-a7ca-37028fe637ae.png)

