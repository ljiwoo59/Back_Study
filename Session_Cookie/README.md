# Cookie & HttpSession

## Http Protocol
* *Client* 가 *Server* 에 요청
* *Server* 는 요청에 대한 처리를 한 후 *Client* 에 응답
* 응답 후 *연결 해제* (stateless)
  * 지속적인 연결로 인한 자원낭비 줄임
  * *Client* 와 *Server* 가 연결 상태를 유지해야 하는 경우 (로그인 정보 등) 문제 발생
  * ***Client* 단위로 상태 정보를 유지해야 하는 경우 *Cookie* 와 *Session* 이 사용됨**

# Cookie : javax.servlet.http.Cookie
* ***Server* 에서 사용자의 컴퓨터에 저장하는 정보 파일**
* 사용자가 별도의 요청을 하지 않아도 브라우저는 *request* 시 *Request Header* 를 넣어 자동으로 *Server* 에 전송
* *key* 와 *value* 로 구성되고 *String* 형태
* 브라우저마다 저장되는 **Cookie** 는 다름 (다른 사용자로 인식)

## 사용 목적
* **세션 관리** : 사용자 아이디, 접속시간, 장바구니 등의 *서버*가 알아야 할 정보 저장
* **개인화** : 사용자마다 다르게 그 사람에 적절한 페이지를 보여준다
* **트래킹** : 사용자의 행동과 패턴을 분석하고 기록

## 구성 요소
* **이름** : 각 *쿠키*를 구별하는 이름
* **값** : *쿠키*의 이름과 매핑되는 값
* **유효기간** : *쿠키*의 유효기간
* **도메인** : *쿠키*를 전송할 도메인
* **경로** : *쿠키*를 전송할 요청 경로

## 동작 순서
1. *Client* 가 페이지를 요청
2. *WAS* 는 **Cookie** 를 생성
3. *HTTP Header* 에 **Cookie** 를 넣어 응답
4. 브라우저는 넘겨받은 **Cookie** 를 PC 에 저장하고, 다시 *WAS* 가 요청할 때 요청과 함께 **Cookie** 전송
5. 브라우저가 종료되어도 **Cookie** 의 만료 기간이 남아있다면 계속 보관
6. 동일 사이트 재방문 시 **Cookie** 가 있는 경우, 요청 페이지와 함께 전송

## 주요 기능
* **생성**
```java
Cookie cookie = new Cookie(String name, String value);
```
* **값 변경/얻기**
```java
cookie.setValue(String value);
String value = cookie.getValue();
```
* **사용 도메인지정/얻기**
```java
cookie.setDomain(String domain);
String domain = cookie.getDomain();
```
* **값 범위지정/얻기**
```java
cookie.setPath(String path);
String path = cookie.getPath();
```
* **Cookie의 유효기간지정/얻기**
```java
cookie.setMaxAge(int expiry);
int expiry = cookie.getMaxAge();

// cookie 삭제
cookie.setMaxAge(0);
```
* **생성된 Cookie를 Client에 전송**
```java
response.addCookie(cookie);
```
* **Client에 저장된 Cookie 얻기**
```java
Cookie cookies[] = request.getCookies();
```

---

# Session : javax.servlet.http.HttpSession
* **방문자가 웹 서버에 접속해 있는 상태**
* *WAS* 의 메모리에 *Object* 형태로 저장
* *WAS* 에 웹 컨테이너의 상태를 **유지**하기 위한 정보를 저장
  * 저장되는 *Cookie* (= 세션 쿠키)
* 브라우저를 닫거나, 서버에서 *Session* 을 삭제 했을 때만 삭제 되므로, *Cookie* 보다 보안이 좋다
* 각 *Client* 당 고유 **Session ID**

## 동작 순서
1. *Client* 가 페이지를 요청
2. *Server* 는 접근한 *Client* 의 *Request-Header* 필드인 **Cookie** 를 확인하여, *Client* 가 해당 **session-id** 를 보냈는지 확인
3. **session-id** 가 존재하지 않는다면 새로 생성해 *Client* 에 돌려준다
4. **session-id** 를 *Cookie* 를 사용해 *Server* 에 저장 (쿠키 이름 : JSESSIONID)
5. *Client* 는 재접속시, 이 *Cookie* 를 이용하여 **session-id** 값을 *Server* 에 전달

## 주요 기능
* **생성**
```java
HttpSession session = request.getSession();
HttpSession session = request.getSession(false);
```
* **값 저장**
```java
session.setAttribute(String name, Object value);
```
* **값 얻기**
```java
Object obj = session.getAttribute(String name_;
```
* **값 제거**
```java
session.removeAttribute(String name); // 특정 이름의 속성 제거
session.invalidate(); // binding 되어 있는 모든 속성 제거
```
* **생성시간**
```java
long ct = session.getCreationTime();
```
* **마지막 접근시간**
```java
long lat = session.getLastAccessedTime();
```

---

# Session vs. Cookie
* 전역에 저장하기 때문에 project 내의 모든 JSP 에서 사용가능
* *Map* 형식으로 관리하기에 *key* 값의 중복을 허용하지 않음
* **Session**
  * javax.servlet.http.HttpSession (Interface)
  * *Server* 의 메모리에 *Object* 로 저장
  * 로그인 시 사용자 정보, 장바구니 등에 사용
  * 용량 제한 없음
  * 만료시점 알 수 없음
    * *Client* 가 로그아웃 하거나, 일정 시간동안 접근하지 않을 경우
* **Cookie**
  * javax.servlet.http.Cookie (Class)
  * *Client* 컴퓨터에 *file* 로 저장
    * *String* 형태
  * 최근 본 상품 목록, 아이디 저장(자동 로그인), 오늘은 그만 보기 등에 사용
  * 도메인 당 20개, 1쿠키 당 4KB 용량 제한
  * 만료시점은 쿠키 저장 시 설정
    * 설정이 없을 경우 브라우저 종료 시 만료
