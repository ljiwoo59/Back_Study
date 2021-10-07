# Servlet
* *웹 컨테이너*에서 실행되며, **동적인 컨텐츠**를 생성하기 위한 자바 클래스
* *자바 코드 안에 HTML 포함*
* 플랫폼, 서버 독립성
* 확장성

## Process
1. Data get
2. Logic - Business, DB
3. Response page

## Life-Cycle
* *main method* 없이 객체의 생성부터 사용의 주체가 **Servlet Container**
* **Client Request** --> **객체 생성과 초기화(한번만)** --> ***service(), doPost(), doGet()*** --> **객체 제거(한번만)**
### 주요 메소드

|method|description|
|-----|------|
|**init()**|서블릿이 메모리에 로드 될때 한번 호출|
|**doGet()**|GET 방식으로 data 전송 시 호출|
|**doPost()**|POST 방식으로 data 전송 시 호출|
|**service()**|모든 요청은 service() 를 통해서 doXXX() 메소드로 이동|
|**destroy()**|서블릿이 메모리에서 해제되면 호출|

### GET vs. POST
* **GET**
  * 전송되는 데이터가 URL 뒤에 QueryString 으로 전달
  * 입력 값이 적은 경우 또는 데이터가 노출이 되도 문제가 없는 경우 사용
  * 간단한 데이터를 빠르게 전송
  * form tag 뿐만 아니라 직접 URL 에 입력하여 전송 가능
  * 데이터 사이즈 제한
* **POST**
  * URL 과 별도로 전송
  * HTTP header 뒤 body 에 입력 스트림 데이터로 전달
  * 데이터의 제한이 없음
  * 보안 유지
  * *GET* 방식보다 느림 

---

# Example
## [방명록 작성](https://github.com/ljiwoo59/Back_Study/blob/main/Servlet/GuestBookWrite.java)
1. *Client* 로부터 id, subject, content 를 받아온다
2. **DB insert Logic**
3. 성공할 시 성공 페이지, 아닐 시 실패 페이지 응답
