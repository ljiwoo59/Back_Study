# MVC (Model-View-Controller)
![캡처](https://user-images.githubusercontent.com/54715744/138873211-8fe711ad-72d3-49a4-8472-229b7d9b62b4.PNG)

## Web Application Architecture
### Model 1
* ***View*** 와 ***Logic*** 을 ***JSP* 페이지 하나에서 처리**하는 구조
* *Client* 로부터 요청이 들어오면 *JSP* 페이지는 *java beans* 나 별도의 *service class* 를 이용하여 작업을 처리, 결과를 출력
* 간단한 page 를 구성하기 위해 과거에 주로 사용 되던 구조
* 출력을 위한 *view (html)* 코드와 로직을 위한 *java* 코드가 섞여있어 복잡함
* 확장성, 유지보수성이 낮음

<br>

### Model 2
* **Model-View-Controller pattern** 을 웹개발에 도입한 구조

#### Model : Service, Dao, Java Beans
* **Logic (Business & DB Logic) 을 처리**하는 모든 것
* *Controller* 에서 넘어온 data 를 이용하여 이를 수행하고 그에 대한 결과를 다시 *Controller* 에게 리턴

#### View : JSP
* 모든 **화면 처리**
* *Client* 의 요청에 대한 결과 뿐만 아니라 *Controller* 에게 요청을 보내는 화면도 처리
* Logic 처리를 위한 java code 는 사라지고 결과 출력을 위한 code 만 존재

#### Controller : Servlet
* *Client* 의 요청을 분석하여 **Logic 처리를 위한 Model 을 호출**
* 리턴 받은 결과 data 를 필요에 따라 *request, session* 등에 저장
* *redirect, forward* 방식으로 **JSP page** 를 이용하여 출력

---

## MVC Design Pattern
* 비즈니스 처리 로직과 사용자 인터페이스 요소를 분리시켜 개발 편의성을 높인 방법론

### Eclipse Step
**1.**  Dynamic web project 생성
<br>

**2.**  *JDBC* 연동을 위한 MySQL driver 추가
<br>

**3.**  *server.xml* 에서 **context-root** 수정 (url 에 나타날 프로젝트 주소)
<br>

**4.**  *Client* 요청 URL 정하기 (화면 당 URL ex. list.bod, read.bod, insert.bod ...)
<br>

**5.**  *FrontController, Controller, Service, Dao, Dto(VO)* 패키지 생성
<br>

**6.**  *WebContent* 폴더 아래 *views* 폴더 생성 (화면 출력을 위한 **JSP**)
<br>

**7.**  *Dto* 작성 (데이터 객체)
<br>

**8.**  *Service* 작성 (Interface, Impl 객체 - Dao 에게 보내 데이터 처리 후 Controller 로 반환)
<br>

**9.**  *Dao* 작성 (Interface, Impl 객체 - DB 와 상호작용 후 결과 반환)
<br>

**10.**  *FrontController* 작성 (*Servlet* : url-mapping 수정 (url 에 나타날 주소))
<br>

**11.**  *Controller* 작성
<br>

**12.**  *view (JSP)* 작성
<br>


#### 동작 순서
**Front Controller -> Controller -> Service -> Dao -> Service -> Controller -> View**

### [게시판](https://github.com/ljiwoo59/Back_Study/tree/main/MVC/MVCBoard)
