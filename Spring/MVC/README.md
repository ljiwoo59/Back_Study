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

---

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

### * web.xml
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

### * servlet-context.xml
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

---

# Controller
## @Controller
* **Class** 타입에 적용
* ***servlet-context.xml*** 에 등록

```xml
<!-- 직접적으로 명시 -->
<beans:bean name="boardController" class="com.mvc.controller.BoardController">
	<beans:property id="boardService" ref="boardService" />
</beans:bean>

<!-- 자동 스캔 -->
<annotation-driven />
<context:component-scan base-package="com.board.controller" />
```

## @RequestMapping
* *요청 URL mapping 정보*를 설정
* **Class** 타입과 **method** 에 적용
	* 메소드에 한정, 같은 URL 요청에 대하여 HTTP method(GET, POST..) 에 따라 서로 다른 메소드를 mapping 할 수 있음
	* @RequestMapping(value="/index.do" method=RequestMethod.GET)
* *@GetMapping, @PostMapping* 가능

## Parameter
* **Controller method** 의 *parameter* 로 다양한 Object 를 받을 수 있음

|Parameter Type|설명|
|--------------|----|
|**HttpServletRequest**|필요시 Servlet API 를 사용할 수 있음|
|**HttpServletResponse**||
|**HttpSession**||
|**Java.util.Locale**|현재 요청에 대한 Locale|
|**InputStream, Reader**|요청 컨텐츠에 직접 접근할 때 사용|
|**OutputStream, Writer**|응답 컨텐츠를 생성할 때 사용|
|**@PathVariable 적용 파라미터**|URI 템플릿 변수에 접근할 때 사용|
|**@RequestParam 적용 파라미터**|HTTP 요청 파라미터를 매핑|
|**@RequestHeader 적용 파라미터**|HTTP 요청 헤더를 매핑|
|**@CookieValue 적용 파라미터**|HTTP 쿠키 매핑|
|**@RequestBody 적용 파라미터**|HTTP 요청의 몸체 내용에 접근할 때 사용|
|**Map, Model, ModelMap**|View 에 전달할 Model data 를 설정할 때 사용|
|**커맨드 객체**|HTTP 요청 parameter 를 저장한 객체|
||기본적으로 클래스 이름을 모델명으로 사용|
||**@ModelAttrivute** 설정으로 모델명을 설정할 수 있음|
|**Errors, BindingResult**|HTTP 요청 파라미터를 커맨드 객체에 저장한 결과|
||커맨드 객체를 위한 파라미터 바로 다음에 위치|
|**SessionStatus**|폼 처리를 완료했음을 처리하기 위해 사용|
||**@SessionAttributes** 를 명시한 session 속성을 제거하도록 이벤트 발생|

## Return
* **Controller method** 의 *return type*

|Return Type|설명|
|-----------|----|
|**ModelAndView**|model 정보 및 view 정보를 담고있는 ModelAndView 객체|
|**Model**|view 에 전달할 객체 정보를 담고있는 Model (view 의 이름은 요청 URL 로부터 결정)|
|**Map**|view 에 전달할 객체 정보를 담고 있는 Map (view 의 이름은 요청 URL 로부터 결정)|
|**String**|view 의 이름을 반환|
|**View**|view 객체를 직접 리턴, 해당 view 객체를 이용해 view 생성|
|**void**|method 가 ServletResponse 나 HttpServletResponse 타입의 parameter 를 갖는 경우|
||method 가 직접 응답을 처리한다고 가정, 그렇지 않을 경우 요청 URL 로부터 결정된 view|
|**@ResponseBody 적용**|리턴 객체를 HTTP 응답으로 전송|

---

# View
* *Controller* 에서는 처리 결과를 보여줄 **View 이름**이나 **객체**를 리턴하고, *DispatcherServlet* 은 *View 이름*이나 *객체*를 이용하여 **View** 생성
* **ViewResolver** : 논리적 view 와 실제 JSP 파일 Mapping
	* **servlet-context.xml**
	* InternalResourceViewResolver 는 prefix + 논리뷰 + suffix 로 설정
		* ex) /WEB-INF/views/board/list.jsp 

## 명시적 지정
* **ModelAndView 와 String 리턴 타입**
```java
@Controller
public class HomeController {
	@RequestMapping("/hello.do")
	public ModelAndView hello() {
		ModelAndView mav = new ModelAndView("hello");
		return mav;
	}
	
	@RequestMapping("/hello.do")
	public ModelAndView hello() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("hello");
		return mav;
	}
	
	@RequestMapping("/hello.do")
	public String hello() {
		return "hello";
	}
}
```

## 자동 지정
* *RequestToViewNameTranslator* 를 이용하여 URL 로부터 *View* 이름 결정
* **Model 과 Map 리턴 타입**
* **void 리턴 타입이면서 ServletResponse 나 HttpServletResponse 타입의 parameter 가 없는 경우**

```java
@Controller
public class HomeController {
	@RequestMapping("/hello.do") // hello 가 view 이름이 됨
	public Map<String, Object> hello() {
		Map<String, Object> model = new HashMap<String, Object>();
		return model;
	}
}
```

## Redirect
* *View 이름*에 **redirect:** 접두어를 붙이면, 지정한 페이지로 redirect 됨

```java
@Controller
public class BoardRegisterController {
	@Autowired
	private BoardService boardService;
	
	// 글 등록후 1페이지 글리스트로 이동
	@RequestMapping(value="board/register.html", method=RequestMethod.POST)
	public String register(@ModelAttribute("article") BoardDto boardDto) {
		boardService.registerArticle(boardDto);
		return "redirect:board/list.html?pg=1";
	}
}
```

---

# Model
* *View* 에 전달하는 데이터
	* **@RequestMapping** 이 적용된 메소드의 ***Map, Model, ModelMap***
	* **@RequestMapping** 이 적용된 메소드가 리턴하는 **ModelAndView**
	* **@ModelAttribute** 이 적용된 메소드가 리턴하는 객체 

## Map, Model, ModelMap
* 메소드의 *argument* 로 받는 방식

```java
@Controller
public class HomeController {
	@RequestMapping("/hello.do")
	public String hello([Map|ModelMap|Model] model) {
		model.[put|addAttribute]("msg", "hi");
		return "hello";
	}
}
```

### Model Interface 주요 메소드
* Model addAttribute(String name, Object value);
* Model addAttribute(Object value);
* Model addAllAttributes(Collection\<?> values);
* Model addAllAttributes(Map\<String, ?> attributes);
* Model mergeAttributes(Map\<String, ?> attributes);
* boolean containsAttribute(String name);

## ModelAndView
* *Controller* 에서 처리결과를 보여줄 *View* 와 *View 에 전달할 값(model)* 을 저장하는 용도로 사용
* ***setViewName(String viewname);***
* ***addObject(String name, Object value)***

```java
@Controller
public class HomeController {
	@RequestMapping("/hello.do")
	public String hello() {
		ModelAndView mav = new ModelAndView();
		mav.setViewName("hello");
		mav.addObject("msg", "hi");
		return mav;
	}
}
```

## @ModelAttribute
* *@RequestMapping* 이 적용되지 않은 별도 메소드로 모델이 추가될 객체를 생성

```java
@Controller
public class HomeController {
	@ModelAttribute("modelAttrMessage")
	public String maMessage() {
		return "bye";
	}

	@RequestMapping("/hello.do")
	public String hello(Model model) {
		model.addAttribute("msg", "hi");
		return "hello";
	}
}
```

```jsp
<!-- hello.jsp -->
${msg}
${modelAttrMessage}
```

## 요청 URL 매칭
* **@RequestMapping 값으로 {템플릿변수} 사용**
	* 클래스와 메소드 모두 사용 가능
* **@PathVariable 을 이용해서 {템플릿변수}와 동일한 이름을 갖는 *parameter* 추가**
	* 클래스에 정의된 템플릿변수 값도 *parameter* 로 추가해야 함
* Ant 스타일의 URI 패턴 지원
	* **?** : 하나의 문자열과 대치
	* **\*** : 하나 이상의 문자열과 대치
	* **\**** : 하나 이상의 디렉토리와 대치 

```java
@Controller
public class BoardViewController {
	@Autowired
	private BoardService boardService;
	
	@RequestMapping("/blog/{userId}/board1/{articleSeq}")
	public String viewArticle(@PathVariable String userId, @PathVariable int articleSeq, Model model) {
		BoardDto boardDto = boardService.getBlogArticle(userId, articleSeq);
		model.addAttribute("article", boardDto);
		return "view";
	}
}
```

---

# Spring Web Application 동작
![image](https://user-images.githubusercontent.com/54715744/139538277-3117601b-c3e6-425a-9416-57b73dacbb32.png)

1. 웹 어플리케이션이 실행되면 *Tomcat(WAS)* 에 의해 **web.xml** 이 loading
2. *web.xml* 에 등록되어 있는 **ContextLoaderListener (Java Class)** 가 생성.
	* *ContextLoaderListener class* 는 ServletContextListener interface 를 구현하고 있음
	* **ApplicationContext** 를 생성하는 역할 수행
3. 생성된 *ContextLoaderListener* 는 **root-context.xml** 을 loading
4. *root-context.xml* 에 등록되어 있는 **Spring Container** 가 구동
	* **Business Logic (Service), Database Logic (DAO), VO 객체**들이 생성
5. *Client* 로부터 **요청**이 들어옴
6. **DispatcherSerlvet (Servlet)** 이 생성
	* **FrontController** 역할 수행
	* *Clinet* 로부터 요청 온 메시지를 분석하여 알맞은 **PageController** 에게 전달하고 요청에 따른 응답을 어떻게 할지 결정
	* 실질적인 작업은 *PageController* 에서 이루어짐
	* *HandlerMapping, ViewResolver class*
7. *DispatcherServlet* 은 **servlet-context.xml** 을 loading
8. 두번째 **Spring Container** 가 구동되며 응답에 맞는 **PageController** 들이 동작
	* 첫번째 *Spring Container* 가 구동되며 생성된 **DAO, VO, Service class** 들과 협업하여 알맞은 작업 처리 


