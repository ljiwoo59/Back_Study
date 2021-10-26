# JSTL (JSP Standard Tag Library)
* **자바서버 페이지 표준 태그 라이브러리**
* Java EE 기반의 웹 애플리케이션 개발 플랫폼을 위한 컴포넌트 모음
<br />

* **custom tag** : 개발자가 직접 태그를 작성할 수 있는 기능 제공
  * 많이 사용되는 tag 를 모아 **JSTL** 이라는 규약을 만듬
* 논리적 판단, 반복문 처리, 데이터베이스 처리 가능
* *EL* 과 함께 사용하여 코드를 간결하게 작성 가능

```jsp
<%@ taglib prefix="prefix" uri="uri" %>
```

## Library
* **core**
  * prefix : *c*
  * 변수 지원, 흐름제어, URL 처리
  * uri : *http://java.sun.com/jsp/jstl/core*
* **xml**
  * prefix : *x*
  * XML 코어, 흐름제어, XML 변환
  * uri : *http://java.sun.com/jsp/jstl/xml*
* **국제화**
  * prefix : *fmt*
  * 지역, 메시지 형식, 숫자 및 날짜 형식
  * uri : *http://java.sun.com/jsp/jstl/fmt*
* **database**
  * prefix : *sql*
  * SQL
  * uri : *http://java.sun.com/jsp/jstl/sql*
* **함수**
  * Collection, String 처리
  * uri : *http://java.sun.com/jsp/jstl/functions*

## Core Tag
### set
* jsp page 에서 사용 할 **변수 설정**
* 변수나 특정 객체의 프로퍼티에 값을 할당
* value 속성의 값이나 액션의 Body content 로 값 설정
* *var 속성*은 변수를 나타내며, 변수의 생존범위는 *scope 속성*으로 설정 (디폴트는 *page*)
* 특정 객체의 프로퍼티에 값을 할당할 때는 *target 속성*에 객체를 설정하고 *property* 에 프로퍼티명을 설정

```jsp
<!-- value 속성을 이용하여 생존범위 변수 값 할당 -->
<c:set value="value" var="varName" [scope="{page|request|session|application}"]/>

<!-- 액션의 Body content 를 사용하여 생존범위 변수 값 할당 -->
<c:set var="varName" [scope="{page|request|session|application}"]>
   body content
</c:set>

<!-- value 속성을 이용하여 대상 객체의 프로퍼티 값 할당 -->
<c:set value="value" target="target" property="propertyName"/>

<!-- 액션의 Body content 를 사용하여 대상 객체의 프로퍼티 값 할당 -->
<c:set target="target" property="propertyName">
   body content
</c:set>
```

### remove
* 설정한 변수 제거

### if
* 조건에 따른 코드 실행
* *var 속성*은 표현식의 Boolean 값을 담을 변수 (scope 범위)

```jsp
<c:if test="${userType eq 'admin'}" var="accessible">
   <jsp:include page="admin.jsp"/>
</c:if>
```

### choose, when, otherwise
* 다중 조건을 처리할 때 사용 (if ~ else if ~ else)

```jsp
<c:choose>
   <c:when test="${userType == 'admin'}">
       화면1...
   </c:when>

   <c:when test="${userType == 'member'}">
       화면2...
   </c:when>
   
   <c:otherwise>
       화면3...
   </c:otherwise>
</c:choose>
```

### forEach
* array 나 collection 의 각 항목을 처리할 때 사용
* *var 속성*에는 반복에 대한 현재 항목을 담는 변수, *items 속성*에는 반복할 항목들을 갖는 컬렉션 지정
* *varStatus 속성*에 지정한 변수를 통해 현재 반복의 상태를 알 수 있음

```jsp
<!-- 리스트를 반복하며 상태와 항목 출력 -->
<c:forEach var="course" items="${courses}" varStatus="varStatus">
   ${varStatus.count}. ${course.name}<br>
</c:forEach>

<!-- 숫자 1부터 5까지 출력 -->
<c:forEach var="value" begin="1" end="5" step="1">
   ${value}<br>
</c:forEach>
```

### forTokens
* 구분자로 분리된 각각의 토큰을 처리할 때 사용

### import
* URL 을 사용하여 다른 자원의 결과를 삽입

### redirect
* 지정한 경로로 redirect

### url
* URL 작성

### catch
* Exception 처리에 사용
* 기본적으로 JSP 페이지는 예외가 발생하면 지정된 오류페이지를 통해 처리
* **<c:catch>** 는 오류페이지로 넘기지 않고 직접 처리할 때 사용
* *var 속성*에는 발생한 예외를 담을 page 생존범위 변수를 지정
* *<c:catch>* 와 *<c:if>* 를 함께 사용하여 try~catch 와 같은 기능 구현

```jsp
<c:catch var="ex">
<%
   String str = null;
   out.println(str.length()); // 예외 발생
%>
</c:catch>

<c:if test="${ex != null}">
   예외 발생. ${ex.message}
</c:if>
```

### out
* JspWriter 에 내용을 처리한 후 출력
