# JSP
* **Java Server Page**
* HTML 내에 자바 코드를 삽입하여 웹 서버에서 **동적**으로 웹 페이지를 생성하여 웹 브라우저에 돌려주는 언어
* 실행 시 **Servlet** 으로 변환된다
  * 최초 jsp 요청 시
  * jsp file 변경 시 

## Scripting Element
### 선언
* 멤버변수 선언이나 메소드를 선언 하는 영역 (전역)
```jsp
<%! 멤버변수와 method 작성 %>
```
### Scriptlet
* Client 요청 시 매번 호출 영역으로, Servlet 으로 변환 시 service() method 에 해당되는 영역
* *request, response* 관련 코드 구현
```jsp
<% java code %>
```
### 표현식
* 데이터를 출력할 때 사용
```jsp
<%= 문자열 %>** == <% out.print(문자열); %>
```
### 주석
```jsp
<%-- 주석 --%>
```

## JSP 지시자 (Directive)
### page Directive
* 컨테이너에게 현재 JSP 페이지를 어떻게 처리할 것인가에 대한 정보 제공
```jsp
<%@ page attr1="val1" attr2="val2"... %>
```

|속성|기본값|설명|
|---|------|----|
|**language**|java|스크립트에서 사용 할 언어 지정|
|**info**||현재 JSP 페이지에 대한 설명|
|**contentType**|text/html;charset=ISO-8859-1|브라우저로 내보내는 내용의 MIME 형식 지정 및 문자 집합 지정|
|**pageEncoding**|ISO-8859-1|현재 JSP 페이지 문자집합 지정|
|**import**||현재 JSP 페이지에서 사용할 Java 패키지나 클래스 지정|
|**session**|true|세션의 사용 유무 설정|
|**errorPage**||에러가 발생할 때에 대신 처리될 JSP 페이지 지정|
|**isErrorPage**|false|현재 JSP 페이지가 에러 핸들링 하는 페이지인지 지정|
|**buffer**|8KB|버퍼의 크기|
|**autoflush**|true|버퍼의 내용을 자동으로 브라우저로 보낼 지에 대한 설정|
|**isThreadsafe**|true|현재 JSP 페이지가 멀티 쓰레드로 동작해도 안전한지 여부|
|**extends**|javax.servlet.jsp.HttpJspPage|현재 JSP 페이지를 기본적인 클래스가 아닌 다른 클래스로부터 상속하도록 변경|

### include Directive
* 특정 jsp file 을 페이지에 포함
* 여러 jsp 페이지에서 반복적으로 사용되는 부분을 jsp file 로 만든 후 반복 영역에 include
```jsp
<%@ include file="/template/header.jsp" %>
```
### taglib Directive
* JSTL 또는 사용자에 의해서 만든 custom tag 를 이용할 때 사용
```jsp
* **<%@ taglib prefix="c" uri="https://" %>**
```

## 기본 객체
* 객체를 따로 생성하지 않아도 사용할 수 있다

|객체 명|설명|
|-------|----|
|**request**|HTML 폼 요소의 선택 값 등 사용자 입력 정보를 읽어올 때 사용|
|**response**|사용자 요청에 대한 응답을 처리하기 위해 사용|
|**pageContext**|각종 기본 객체를 얻거나 forward 및 include 기능을 활용할 때 사용|
|**session**|클라이언트에 대한 세션 정보를 처리하기 위해 사용|
|**application**|웹 서버의 애플리케이션 처리와 관련된 정보를 레퍼런스하기 위해 사용|
|**out**|사용자에게 전달하기 위한 output 스트림을 처리할 때 사용|
|**config**|현재 JSP 에 대한 초기화 환경을 처리하기 위해 사용|
|**page**|현재 JSP 페이지에 대한 참조 변수에 해당됨|
|**exception**|전달된 오류 정보를 담고 있는 내장 객체|

### Scope
* **pageContext**
  * *하나의 JSP 페이지*를 처리할 때 사용되는 영역
  * 한번의 클라이언트 요청에 대하여 하나의 JSP 페이지가 호출되며, *단 한개의 page 객체*만 대응
  * 페이지 영역에서 저장한 값은 페이지를 벗어나면 사라짐
  * custom tag 에서 새로운 변수를 추가할 때 사용
* **request**
  * *하나의 HTTP 요청*을 처리할 때 사용되는 영역
  * 웹 브라우저가 요청을 할 때 마다 *새로운 request 객체*가 생성
  * request 영역에서 저장한 속성은 그 요청에 대한 응답이 완료되면 사라짐
* **session**
  * *하나의 웹 브라우저*와 관련된 영역
  * 같은 웹브라우저 내에서 요청되는 페이지들은 *같은 session*을 공유
  * 로그인 정보 등 저장할 때 사용
* **application**
  * *하나의 웹 어플리케이션*과 관련된 영역
  * 웹 어플리케이션 당 *한 개의 application 객체*가 생성
  * 같은 웹 어플리케이션에서 요청되는 페이지들은 같은 application 객체를 공유

### 공통 method
* **servlet** 과 **jsp** 페이지 간에 특정 정보를 주고 받거나 공유 하기 위한 메소드 지원

|method|설명|
|**void setAttribute(String name, Object value)**|문자열 name 이름으로 Object형 데이터 저장|
|**Object getAttribute(String name)**|문자열 name 에 해당하는 속성 값이 있다면 Object 형태로 가져오고 없으면 null 리턴|
|**Enumeration getAttributeNames()**|현재 객체에 저장된 속성들의 이름들을 Enumeration 형태로 가져온다|
|**void removeAttribute(String name)**|문자열 name 에 해당하는 속성을 삭제|

## Web Page 이동
### forward(request, response)
```java
RequestDispatcher dispatcher = request.getRequestDispatcher(path);
dispatcher.forward(request, response);
```
* 동일 서버 (project) 내 경로만 가능
* 기존 URL 유지
* 기존 request 와 response 가 그대로 전달
* 비교적 빠른 속도
* request 의 *setAttribute(name, value)* 를 통해 전달

### sendRedirect(location)
```java
response.sendRedirect(location);
```
* 동일 서버 포함 타 URL 가능
* 이동하는 page 로 URL 변경
* 기존의 request 와 response 는 소멸되고, 새로운 request 와 response 생성
* *forward()* 에 비해 느린 속도
* request 로는 데이터 저장 불가능
  * session 이나 cookie 이용

---
# Example
## [방명록 작성](https://github.com/ljiwoo59/Back_Study/blob/main/JSP/articlewrite.jsp)
## [방명록 목록](https://github.com/ljiwoo59/Back_Study/blob/main/JSP/list.jsp)
