# 1. 아이템 선정

은행 서비스를 통해 계좌를 개설하고 유저간에 돈을 거래할 수 있으며 입,출금 내역을 조회할 수 있다.

인기 게시판을 통해 추천을 많이 받은 게시글을 조회할 수 있다.

CRUD 게시판을 만들고 포인트를 이용하여 사용자 권한에 따라 접근할 수 있는 게시판을 제작한다.

검색 기능을 통해 자주 검색되는 단어를 모아볼 수 있다.

쪽지를 주고 받으며 삭제 시 DB에서는 삭제 안되지만 사용자의 UX에서는 삭제할 수 있다.

신고를 받은 유저는 ADMIN이 제재내역을 검토 후 제재할 수 있다.

# 2. 개요
- 프로젝트 명 : 은행 커뮤니티
- 개발 인원 : 1명 (허진성)
- 개발 기간 : 2023—01-13 ~ 진행중
- 주요 기능 :
    - 은행 - 계좌 개설(3개 은행), 계좌 이체, 입출금 내역 조회, 계좌 찾기, 이체 유효성 검사, 타은행 이체시 수수료 발생
    - 게시판 - CRUD 기능, 추천 및 비추천, 조회수, 페이징, 검색 기능, 인기 게시글 조회, 인기 검색어 조회
    - 댓글 - CRUD 기능
    - 사용자 - Oauth 2.0 로그인, 구글,  로그인, 회원 정보 수정, 유효성 및 중복 검사, Security 로그인, 비밀번호 찾기
    - 포인트 - 포인트를 이용하여 회원 승급이 가능하며 회원 등급에 따른 게시판 접근 기능
    - 쪽지 - 사용자 간의 쪽지를 주고받을 수 있으며 수신함과 발신함 구현, 쪽지 삭제 구현
    - 신고 - 불량 유저에 대해 신고를 할 수 있으며 그에 따른 포인트 차감 및 유저 등급 강등 기능
- 개발 언어 : JAVA 11, mustache, javascript, css
- 개발 환경 : SpringBoot 2.7.5, gradle 7.2, Srping Data Jpa, Spring Security, Spring OAuth 2.0
- 데이터 베이스 : MySql, AWS RDS
- 형상관리 : Github, Notion
- 배포 : AWS EC2
- 간단 소개 : 구현하고싶은 기능이 떠오를 때마다 구현하기 위한 CRUD 게시판

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
    
 - 비밀번호 찾기
    - 비밀번호를 분실했을 경우 이메일과 아이디를 통한 검증으로 비밀번호 변경
    - 입력된 비밀번호와 기존의 비밀번호가 같을 경우 동일한 비밀번호라고 안내 메세지 보여주기
    - 입력한 두 비밀번호가 서로 다를 경우 비밀번호가 다르다고 안내 메세지 보여주기
    
2. 로그인 페이지
- 검증되지 않은 회원일 경우 아래의 페이지만 이용 가능
    - 회원가입 페이지
    - 로그인 페이지
    - 자유 게시판 목록 조회 페이지
    - 자유 게시판 상세보기 페이지
    - 게시글 검색 페이지
    - 비밀번호 찾기 지페이지
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

11. 은행
- 3개의 은행 : 기업은행, 부산은행, 경남은행 중 3개의 계좌 중 한 군데에서만 계좌 신설 가능
- 계좌 신설시 10000원의 포인트를 해당 은행의 ADMIN에서 신설 축하금으로 보내줌
- 계좌 찾기를 통해 본인의 계좌번호 확인 가능
- 계좌 이체로 상대방의 계좌에 입금 가능하며 타 은행과 거래시 1500원의 수수료를 ADMIN의 은행에 지불
- 이체 후 잔액 표시
- 입출금 내역 조회 가능하며 시간순으로 정렬
- 계좌 유효성 검사를 통해 계설된 계좌에 한해서만 거래 가능

12. 쪽지
- 게시글을 통해 다른 사용자에게 쪽지를 보낼 수 있음
- 유저 목록에서 다른 사용자에게 쪽지를 보낼 수 있음
- 자신에게 보내는 쪽지는 못보내게 하기 위해 알림 메세지 보여주기
- 보낸 쪽지는 나와 받는 사람만 열람 가능
- 받은 쪽지는 나와 보낸 사람만 열람 가능
- 쪽지 수신함, 발신함은 최신순으로 페이징하여 정렬
- 쪽지를 읽고 삭제시 사용자 UX에서만 삭제되고 DB에는 그대로 잔재
- 쪽지 작성시 제목과 내용은 빈칸으로 작성할 수 없게 하기

13. 신고
- 게시글을 통해 게시글 작성자 신고하기
- 신고 내역은 ADMIN 권한을 가진 사용자만 접근 가능하게 하기
- 신고 내역 작성시 제목과 내용은 빈칸으로 작성할 수 없게 하기
- 신고 내역 삭제 시 사용자 UX에서만 삭제되며 DB에는 그대로 잔재
- 신고 내역 열람 후 검토하여 해당 사용자 포인트 차감 및 회원 등급 강등

14. 비밀게시판
- 회원 포인트가 200점 이상인 유저는 VIP 회원으로 자동 승급되며 비밀 게시판 열람 가능
- 비밀 게시판의 댓글도 작성 가능
- 일반 게시판과 똑같이 제목과 내용은 빈칸으로 작성할 수 없게 하기
- 비밀게시판은 인기 검색어, 인기 게시판에 조회되지 않게 하기


# 4. DB 설계

![image](https://user-images.githubusercontent.com/61839458/223393461-a734df7f-bd62-4166-9c75-4389c5e6242a.png)
![image](https://user-images.githubusercontent.com/61839458/223393535-f7d67c03-1496-40d7-9c72-bb0782343760.png)

# 5. API 설계
https://www.notion.so/5-API-85fc5b625484446bbc3bea6631401058
![image](https://user-images.githubusercontent.com/61839458/223393807-270a1267-6e76-4561-9b31-e75f4a227541.png)
![image](https://user-images.githubusercontent.com/61839458/223393868-b80a4717-ab4c-4ffc-82de-d26a9c80884d.png)
![image](https://user-images.githubusercontent.com/61839458/223393922-33cc8825-661e-4dbf-b117-fbd1c60b3081.png)
![image](https://user-images.githubusercontent.com/61839458/223393942-5b560e29-ad91-4db2-b9c3-2c258d963064.png)
![image](https://user-images.githubusercontent.com/61839458/223393967-54527153-04d4-4bd5-bb1b-24a919b000f1.png)
![image](https://user-images.githubusercontent.com/61839458/223394152-1a3fb3f2-f9b7-40ee-a004-fffcfb2c57e3.png)
![image](https://user-images.githubusercontent.com/61839458/223394293-c0158dde-ff0b-49e3-8246-7623b9507f84.png)
![image](https://user-images.githubusercontent.com/61839458/223394218-192472d0-ee39-41a1-8a95-5f8d08f56c70.png)
![image](https://user-images.githubusercontent.com/61839458/223394379-c92afd8d-523d-408e-b0f8-1e39619acb4d.png)

# 6. 화면설계서 (와이어 프레임)
https://www.notion.so/6-e8de440614224155b6e79c13f79f79e6

1. 첫 홈 화면 로그인 페이지
![image](https://user-images.githubusercontent.com/61839458/223395939-6d8f2748-dbad-465d-a7ea-fe3aab063f21.png)

2. 회원가입 페이지
![image](https://user-images.githubusercontent.com/61839458/223396098-0acb2a47-2ffb-4790-b731-988540750175.png)

3. 로그인 후 메인 페이지
![image](https://user-images.githubusercontent.com/61839458/223396722-8123b967-7417-4e17-b721-81b389c221a1.png)

4. 게시글 작성 페이지
![image](https://user-images.githubusercontent.com/61839458/223396838-6a2f8516-66ec-4313-85e2-4b8eb96ea0bd.png)

5. 게시글 작성 후 페이지
![image](https://user-images.githubusercontent.com/61839458/223397543-46fbdf1a-72aa-4305-a7b6-9fe886513a3c.png)

6. 게시글 추천 / 비추천
![image](https://user-images.githubusercontent.com/61839458/223397690-3583d0ae-091e-4928-9a57-75ce048d1162.png)

7. 게시글 추천 취소
![image](https://user-images.githubusercontent.com/61839458/223397770-1dc40da0-34b7-4da2-b9e4-5fe3d520c0ed.png)

8. 댓글 작성 후 페이지
![image](https://user-images.githubusercontent.com/61839458/223397937-f811ff9a-017c-4c9d-962e-4bf82de3aecd.png)

9. 게시글 수정
![image](https://user-images.githubusercontent.com/61839458/223398007-27007722-8931-4402-aaed-b1cc8b3d3ce2.png)

10. 수정된 게시글
![image](https://user-images.githubusercontent.com/61839458/223398087-f1c0848c-6154-4ef9-af93-aa7db7ac2752.png)

11. 게시글 삭제 안내
![image](https://user-images.githubusercontent.com/61839458/223398225-7ae5692e-37bd-415d-bcd4-7298723b6170.png)

12. 삭제 후 페이지
![image](https://user-images.githubusercontent.com/61839458/223399128-5e1865bb-13a4-4f5e-9216-bed71a2aed7f.png)
(작성하고 보니 댓글이 같이 삭제될 때 포인트 차감을 해야겠다..)

13. 인기 게시판 조회 (추천 -> 조회 -> 생성일순)
![image](https://user-images.githubusercontent.com/61839458/223399532-9653ad50-091f-4013-97fb-35f291707415.png)

14. 회원정보 페이지
![image](https://user-images.githubusercontent.com/61839458/223399673-5698643f-81d0-40b6-9a1b-65436e532963.png)

15. 회원정보 수정
![image](https://user-images.githubusercontent.com/61839458/223399776-7c49ed0f-fa85-4ebb-96db-23e198128c78.png)

16. '인' 이라는 단어를 검색하기 전 페이지 정렬
![image](https://user-images.githubusercontent.com/61839458/223399958-fc8cc366-2ea7-4c91-860d-5a12649d1fdf.png)

17. 검색 후 페이지 정렬
![image](https://user-images.githubusercontent.com/61839458/223400047-1a8fe6a0-d4a9-45d3-bde9-ddb318cfd499.png)

18. 검색어 순위 상위 10개 정렬
![image](https://user-images.githubusercontent.com/61839458/223400325-b317cc5c-2d93-4ba5-a04c-7601899bea84.png)

19. 유저 목록 페이지
![image](https://user-images.githubusercontent.com/61839458/223400405-3d2e8e3c-87ca-4348-85cc-4de563bb1a06.png)

20. 유저 목록에서 쪽지 보내기
![image](https://user-images.githubusercontent.com/61839458/223400524-ba07a92a-223e-4b03-9ef5-d4074689a452.png)

21. 쪽지 보내기 페이지
![image](https://user-images.githubusercontent.com/61839458/223403891-7616ee28-667e-41e4-b18a-5eb5c3e57c4a.png)

22. 보낸 쪽지 조회
![image](https://user-images.githubusercontent.com/61839458/223404255-a0ba6280-f06c-4cf1-b959-2e50a3d7a743.png)

23. 보낸 쪽지 상세 페이지
![image](https://user-images.githubusercontent.com/61839458/223404396-858582d8-0d97-4763-869c-40b5a1637a19.png)

24. 쪽지 삭제
![image](https://user-images.githubusercontent.com/61839458/223404497-b5285848-3245-4730-88de-d5d4412d27a8.png)

25. 사용자의 UX에서는 삭제되지만 DB에는 그대로 잔재
![image](https://user-images.githubusercontent.com/61839458/223404638-69128cd9-4346-42aa-a119-fca0c65c9c18.png)

26. 비밀게시판 접근
![image](https://user-images.githubusercontent.com/61839458/223404823-beba7e17-50a9-41c7-baa8-045d56f870e9.png)

27. 비밀게시판 게시글 작성 후 페이지
![image](https://user-images.githubusercontent.com/61839458/223406637-34aae80b-382f-4568-b755-779c25d322ea.png)

28. 은행 첫 이용페이지
![image](https://user-images.githubusercontent.com/61839458/223406984-a0b1db2f-d5ff-4d7c-8fd6-8e6d476d1fea.png)

29. 은행 계좌 개설 및 이름 유효성 검사
![image](https://user-images.githubusercontent.com/61839458/223407056-a945ac80-e438-4972-b492-aa20d211f9b6.png)
(뭔가를 더 입력 받고 싶은데 마땅히 받을 요소가 없음.....)

30. 유효성 검사
![image](https://user-images.githubusercontent.com/61839458/223407263-6980aef6-c786-4edc-b95d-5f701ad2497a.png)

31. 계좌 개설 후 페이지
![image](https://user-images.githubusercontent.com/61839458/223407343-8102cc56-71b8-4525-bf31-c5343e42fe72.png)

32. 개설 축하금 입금 내역 확인
![image](https://user-images.githubusercontent.com/61839458/223407448-c3694135-967b-4e66-95e5-2934c0bf5797.png)

33. 계좌이체 페이지
![image](https://user-images.githubusercontent.com/61839458/223407539-7988b68b-62fd-4ead-9462-6bab07d96fe0.png)

34. 유효하지 않은 계좌 번호 또는 은행명 또는 사용자명을 입력시 안내 메시지
![image](https://user-images.githubusercontent.com/61839458/223407711-09538f97-eb2d-41a6-8868-378bcf18f344.png)

35. 세 개의 입력 정보가 모두 유효한 정보면 뜨는 안내 메시지
![image](https://user-images.githubusercontent.com/61839458/223407850-ce95ba49-0066-43f7-a41a-abfea70f4d3a.png)

36. 잔액 부족일 경우 뜨는 안내 메시지
![image](https://user-images.githubusercontent.com/61839458/223407940-05e657a6-d747-45e6-bb8c-5f4c95e9fa72.png)

37. 이체 성공할 경우 뜨는 안내 메시지
![image](https://user-images.githubusercontent.com/61839458/223408038-d73e602e-2eb4-4355-828b-f852256bb55b.png)

38. 이체 성공 후 이동하는 페이지
![image](https://user-images.githubusercontent.com/61839458/223408120-c9a7ac2c-4dee-4cc0-bacc-e9a5175bfa60.png)

39. 게시글 신고 페이지
![image](https://user-images.githubusercontent.com/61839458/223408251-42ae58dd-e100-4067-9702-4bca07835887.png)

40. 신고 목록 조회하기(ADMIN 전용 페이지)
![image](https://user-images.githubusercontent.com/61839458/223408320-f0d2d1c5-f5e9-4cdc-914f-09929261c688.png)

41. 신고 내역 상세 조회하기(ADMIN 전용 페이지)
![image](https://user-images.githubusercontent.com/61839458/223408432-4bf6f9fa-9fa8-4955-a384-5c1caada25a5.png)

42. 포인트 회수하기
![image](https://user-images.githubusercontent.com/61839458/223408536-7e99272d-836a-4c5c-8fe2-cb99e40e3abf.png)

43. 포인트 회수 후 0원된 포인트 (회원 등급은 VIP -> 일반 유저로 강등)
![image](https://user-images.githubusercontent.com/61839458/223408673-e6c09523-96f4-47d9-9fbc-54ad83f21721.png)

44. 비밀번호 찾기 페이지
![image](https://user-images.githubusercontent.com/61839458/223408824-10cfc44b-077f-433e-afd3-be5c787f0e4b.png)

45. 유효성 검사 실패시 뜨는 안내 메세지
![image](https://user-images.githubusercontent.com/61839458/223408899-86d21f7f-56c8-4eda-a8c8-776cbb915fc5.png)

46. 회원 유효성 검사 통과 후 뜨는 안내 메세지
![image](https://user-images.githubusercontent.com/61839458/223410288-f1d55ed7-8a87-4221-a20b-ffb636edfbd5.png)

47. 비밀번호 변경 시 이전의 비밀번호와 같은 비밀번호 입력 시 뜨는 안내 메세지
![image](https://user-images.githubusercontent.com/61839458/223410430-ba1f2017-f2ff-4183-b932-90af676a44c6.png)

48. 비밀번호가 서로 다를경우 뜨는 안내 메세지
![image](https://user-images.githubusercontent.com/61839458/223410896-08c6674d-8415-47cb-aa61-2fc7f189193b.png)

49.유효성 검사 통과 후 비밀번호 변경
![image](https://user-images.githubusercontent.com/61839458/223411096-9412fb5e-ce80-4248-8971-2a32d5f8f770.png)











# 7. 에러 사항 및 해결법과 고민들
https://www.notion.so/43dd746d01cd45d2824339b361442c34?v=c58ec3dec8aa4497ad00a08cb41a67c1
![image](https://user-images.githubusercontent.com/61839458/223415930-5c054aa6-87a9-4fed-bc35-e87761460607.png)
![image](https://user-images.githubusercontent.com/61839458/223415959-eb4c6c73-914f-4c0d-b171-9e295ddb60ea.png)


