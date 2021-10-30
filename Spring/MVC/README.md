# Spring Web MVC

## Model-View-Controller
* 어플리케이션의 확장을 위해 *Model, View, Controller* 세가지 영역으로 분리
* 컴포넌트의 변경이 다른 영역 컴포넌트에 영향을 미치지 않음 (*유지보수 용이*)
* 컴포넌트 간의 결합성이 낮아 프로그램 수정이 용이 (*확장성이 뛰어남*)
* 화면과 비즈니스 로직을 분리해서 작업 가능
* 영역별 개발로 인하여 확장성이 뛰어남
* 표준화된 코드를 사용하므로 공동작업이 용이하고 유지보수성이 좋음
* 개발과정이 복잡해 초기 개발속도가 늦음
* 초보자가 이해하고 개발하기에 다소 어려움

### Model
* 어플리케이션 상태의 캡슐화
* 상태 쿼리에 대한 응답
* 어플리케이션의 기능 표현
* 변경을 *view* 에 통지

### View
* *모델*을 화면에 시각적으로 표현
* *모델*에게 업데이트 요청
* 사용자의 입력을 *컨트롤러*에 전달
* *컨트롤러*가 *view* 를 선택하도록 허용

### Controller
* 어플리케이션의 행위 정의
* 사용자 액션을 모델 업데이트와 mapping
* 응답에 대한 *view* 선택

# Spring MVC
![image](https://user-images.githubusercontent.com/54715744/139532914-b29105fe-625f-44e8-8cea-44efd831b543.png)


* *Spring* 은 *DI* 나 *AOP* 같은 기능 뿐만 아니라, **Servlet 기반의 WEB 개발을 위한 MVC Framework** 제공
* *Model2 Architecture* 과 *Front Controller Pattern* 을 제공
* *Transaction 처리나 DI 및 AOP* 등을 손쉽게 사용

## 구성 요소
### DispatcherServlet (Front Controller)
* **모든 클라이언트의 요청을 전달받음**
* *Controller* 에게 클라이언트의 요청을 전달하고, Controller 가 리턴한 결과를 *View* 에게 전달하여 알맞은 응답 생성

### HandlerMapping
* **클라이언트의 요청 URL 을 어떤 Controller 가 처리할지를 결정**
* *URL 과 요청 정보*를 기준으로 어떤 핸들러 객체를 사용할지 결정하는 객체
* *DispatcherServlet* 은 하나 이상의 핸들러 매핑을 가질 수 있음

### Controller
* **클라이언트의 요청을 처리한 뒤, Model 을 호출하고 그 결과를 DispactherServlet 에게 알려줌*

### ModelAndView
* **Controller 가 처리한 데이터 및 화면에 대한 정보를 보유한 객체**

### ViewResolver
* **Controller 가 리턴한 View 이름을 기반으로 Controller 의 처리 결과를 보여줄 View 결정**

### View
* **Controller 의 처리 결과를 보여줄 응답화면 생성**

## 실행 순서
1. **DispatcherServlet** 이 요청을 수신
	* *단일 Front Controller Servlet*
	* 요청을 수신하여 처리를 다른 컴포넌트에 위임
	* 어느 Controller 에 요청을 전송할지 결정
2. **DispatcherServlet** 은 ***Handler Mapping*** 에 어느 *Controller* 를 사용할 것인지 문의
	* URL 과 Mapping
3. **DispatcherServlet** 은 요청을 **Controller** 에게 전송하고 **Controller** 는 요청을 처리한 후 결과 리턴
	* Business Logic 수행 후 결과 정보(Model) 가 생성되어 JSP 와 같은 *View* 에서 사용됨
4. **ModelAndView Object** 에 수행결과가 포함되어 ***DispatcherServlet*** 에 리턴
5. ***ModelAndView*** 는 실제 JSP 정보를 갖고 있지 않으며, **ViewnResolver** 가 논리적 이름을 실제 JSP 이름으로 변환
6. **View** 는 결과정보를 사용하여 화면을 표현

## 구현
1. **web.xml** 에 *DispatcherServlet* 등록 및 *Spring 설정파일* 등록
2. **설정 파일**에 *HandlerMapping* 설정
3. **Controller** 구현 및 **Context 설정 파일(servlet-context.xml)** 에 등록
	* 좋은 디자인은 *Controller* 가 많은 일을 하지 않고 ***Service*** 에 처리를 위임
4. *Controller* 와 *JSP* 연결을 위해 **View Resolver** 설정
5. **JSP** 작성

### web.xml
```xml
<!-- The definition of the Root Spring Container shared by all Servlets and Filters -->
<context-param>
	<param-name>contextConfigLocation</param-name>
	<param-value>/WEB-INF/spring/root-context.xml</param-value>
</context-param>

<!-- Creates the Spring Container shared by all Servlets and Filters -->
<listener>
	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>

<!-- Processes application requests -->
<servlet>
	<servlet-name>appServlet</servlet-name>
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	<init-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/spring/appServlet/servlet-context.xml</param-value>
	</init-param>
	<load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
	<servlet-name>appServlet</servlet-name>
	<url-pattern>*.mvc</url-pattern>
</servlet-mapping>
```

#### DispatcherServlet 설정
* **\<init-param>** 을 설정하지 않으면 \<servlet-name>-context.xml 파일에서 ApplicationContext 정보를 로드
* *Spring Container* 는 설정파일의 내용을 읽고 ApplicationContext 객체를 생성
* **\<url-pattern>** 은 *DispatcherServlet* 이 처리하는 URL Mapping pattern 을 정의
* 1개 이상의 *DispatcherServlet* 설정 가능
	* 각 DispatcherServlet 마다 각각의 *ApplicationContext* 생성
* **\<load-on-startup>1\</load-on-startup>** 설정 시 *WAS startup* 시 초기화 작업진행

```xml
<!-- Processes application requests -->
<servlet>
	<servlet-name>appServlet</servlet-name>
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	<init-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/spring/appServlet/servlet-context.xml</param-value>
	</init-param>
	<load-on-startup>1</load-on-startup>
</servlet>

<servlet>
	<servlet-name>appServlet2</servlet-name>
	<servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
	<init-param>
		<param-name>contextConfigLocation</param-name>
		<param-value>/WEB-INF/spring/appServlet/servlet-context2.xml</param-value>
	</init-param>
	<load-on-startup>1</load-on-startup>
</servlet>

<servlet-mapping>
	<servlet-name>appServlet</servlet-name>
	<url-pattern>*.mvc</url-pattern>
</servlet-mapping>

<servlet-mapping>
	<servlet-name>appServlet2</servlet-name>
	<url-pattern>*.action</url-pattern>
</servlet-mapping>
```

#### 최상위 Root ContextLoader 설정
* *Context 설정파일*을 로드하기 위해 **Listener** 설정 (ContextLoaderListener)
* *Listener* 설정이 되면 **/WEB-INF/spring/root-context.xml** 파일을 읽어서 공통적으로 사용되는 최상위 Context 를 생성

```xml
<!-- The definition of the Root Spring Container shared by all Servlets and Filters -->
<context-param>
	<param-name>contextConfigLocation</param-name>
	<param-value>/WEB-INF/spring/root-context.xml</param-value>
</context-param>

<!-- Creates the Spring Container shared by all Servlets and Filters -->
<listener>
	<listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
</listener>
```

#### Application Context 분리
* 어플리케이션 레이어에 따라 어플리케이션 컨텍스트 분리

|Layer|설정파일|
|-----|--------|
|**Security Layer**|board-security.xml|
|**Web Layer**|board-servlet.xml|
|**Service Layer**|board-service.xml|
|**Persistence Layer**|board-dao.xml|

### servlet-context.xml
```xml
<!-- Enables the Spring MVC @Controller programming model -->
<annotation-driven />

<!-- Handles HTTP GET requests for /resources/** by efficiently serving up static resources in the ${webappRoot}/resources directory -->
<resources mapping="/resources/**" location="/resources/" />

<!-- Resolves views selected for rendering by @Controllers to .jsp resources in the /WEB-INF/views directory -->
<beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
	<beans:property name="prefix" value="/WEB-INF/views/" />
	<beans:property name="suffix" value=".jsp" />
</beans:bean>
```

#### Controller 등록

```java
@Controller
public class BoardController {
	@Autowired
	BoardService service;
		
	//@RequestMapping(value = "/list.bod", method = RequestMethod.GET)
	@GetMapping(value = "/list.bod")
	public String list(Model model) {
		ArrayList<Board> list = service.selectAll();	
		model.addAttribute("list", list);		
		return "list";//논리적 view(jsp) 이름
	}
}
```

```xml
<!-- Enables the Spring MVC @Controller programming model -->
<beans:bean class="com.mvc.controller.BoardController"/>

<!-- @Controller Annotation 을 자동으로 찾아서 등록할 수도 있다 -->
<annotation-driven />
```

#### ViewResolver 설정
* *Controller* 와 *response page* 연결

```xml
<!-- Resolves views selected for rendering by @Controllers to .jsp resources in the /WEB-INF/views directory -->
<beans:bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">
	<beans:property name="prefix" value="/WEB-INF/views/" />
	<beans:property name="suffix" value=".jsp" />
</beans:bean>
```
