# EL (Expression Language)
* 표현을 위한 언어로 **JSP** 스크립트의 표현식을 대신하여 *속성 값*을 쉽게 출력하도록 고안된 언어
  * <%= %> 를 대체한다
* 도트 연산자 *왼쪽*은 반드시 **java.util.Map 객체** 또는 **Java Bean 객체**
* 도트 연산자 *오른쪽*은 반드시 **Map 의 key** 또는 **Bean property**
<br/>

* **JSP** 의 네가지 기본 객체가 제공하는 영역을 속성 사용
  * pageContext, request, session, application
  * *pageContext (Java Bean)* 를 제외한 모든 내장 객체는 **Map**
* 자바 클래스 메소드 호출 기능
* 표현 언어만의 기본 객체 제공
* 수치, 관계, 논리 연산 제공

## EL 문법
```el
// ${Map.key} or ${JavaBean.BeanProperty}

<%= ((com.mvc.model.MemberDto)request.getAttribute("userinfo")).getZipDto().getAddress() %>

${userinfo.zipDto.address}
```
<br/>

* **Dot 표기법** 외에 **\[] 연산자**를 사용하여 객체의 값 접근 가능
* **\[] 연산자**의 값이 *문자열*인 경우, **맵의 키**가 될 수 있고 **Bean 프로퍼티**나 **리스트 및 배열의 인덱스**가 될 수 있다
* *배열과 리스트* 인 경우, 문자로 된 인덱스 값은 숫자로 변경하여 처리
```el
// [] 연산자를 이용한 객체 프로퍼티 접근
${userinfo["name"]}

// Dot 표기법을 이용한 객체 프로퍼티 접근
${userinfo.name}

// 리스트나 배열 요소에 접근
// Servlet
String[] names = {"홍길동", "이순신", "임꺽정"};
request.setAttribute("userNames", names);

// JSP
${userNames[0]} // 홍길동
${userNames["1"]} // 숫자로 자동 치환되어 이순신 출력
```

## EL 내장객체
* **JSP** 페이지의 *EL 표현식*에서 사용할 수 있는 객체
* **pageContext** : 현재 페이지의 프로세싱과 상응하는 PageContext instance
  * **Java Bean** 타입
* **pageScope**
* **requestScope** - **request**
* **sessionScope** - **session**
* **applicationScope**
* **param** : ServletRequest.getParameter(String) 을 통해 요청 정보 추출
* **paramValues** : ServletRequest.getParameterValues(String) 을 통해 요청 정보 추출
* **header** : HttpServletRequest.getHeader(String) 을 통해 헤더 정보 추출
* **headerValues** : HttpServletRequest.getHeaders(String) 을 통해 헤더 정보 추출
* **cookie** : HttpServletRequest.getCookies() 를 통해 쿠키 정보 추출
* **initParam** : ServletContext.getInitParameter(String) 을 통해 초기화 파라미터 추출

```el
// property 이름만 사용할 경우 자동으로 pageScope>requestScope>sessionScope>applicationScope 순으로 객체를 찾는다
// request.setAttribute("userinfo", "홍길동");
${requestScope.userinfo}
${pageContext.request.userinfo}
${userinfo}

// url?name=홍길동&fruit=사과&fruit=바나나
${param.name}
${paramValues.fruit[0]}

// request.setAttribute("com.user", memberDto);
${com.user.name} // 에러!!! com 이라는 속성은 존재하지 않음
${requestScope["com.user"].name} // request 내장객체에서 [] 연산자를 통해 속성 접근

// ${cookie.id.value}
// Cookie 가 null 이라면 null 리턴
// id 가 null 이면 null 리턴
// value 가 null 이라면 공백 리턴 (EL 은 값이 null 이더라도 null 을 출력하지 않음)

// 객체 method 호출
${requestScope.users.size()}
${users.size()}
// 주의!! ${users.size} == <%= request.getAttribute("users").getSize() %> (getter 이다)
```

## EL 연산자
* **+, -, \*, / (div), % (mod)**
* **== (eq), != (ne), < (lt), > (gt), <= (le), >= (ge)**
* **condition ? value1 : value2**
* **&& (and), || (or), ! (not)**
* **empty**
  * 값이 null 이면 true
  * 값이 빈 문자열("") 이면 true
  * 길이가 0인 배열(\[]) 이면 true
  * 빈 Map 객체는 true
  * 빈 Collection 객체는 true
