# Spring Framework
* 엔터프라이즈 급 애플리케이션을 만들기 위한 모든 기능을 종합적으로 제공하는 경량화 된 솔루션
  * Low level 에 신경 쓰지 않고 Business Logic 개발에 전념할 수 있도록 해준다
* Java Enterprise Edition(JEE) 이 제공하는 다수의 기능을 지원
* *Dependency Injection(DI)*, *Aspect Oriented Programming(AOP)* 지원

## 구조
![image](https://user-images.githubusercontent.com/54715744/139255736-6357e3ed-5748-4d2b-82fb-c629fb5a65fd.png)
* Enterprise Application 개발 시 복잡함을 해결하는 Spring 의 핵심

### POJO (Plain Old Java Object)
* 특정 환경이나 기술에 종속적이지 않은 객체지향 원리에 충실한 자바 객체
* 테스트하기 용이하며, 객체지향 설계를 자유롭게 적용 가능

### PSA (Portable Service Abstraction)
* 환경과 세부기술의 변경과 관계 없이 일관된 방식으로 기술에 접근할 수 있게 해주는 설계 원칙
* Low Level 의 기술 구현 부분과 기술을 사용하는 인터페이스로 분리
  * 트랜잭션 추상화, OXM 추상화, 데이터 액세스의 Exception 변환기능
  * ex) 데이터베이스에 관계없이 동일하게 적용 할 수 있는 트랜잭션 처리방식

### IoC (Inversion of Control) / DI (Dependency Injection)
* 유연하게 확장 가능한 객체를 만들어 두고 객체 간의 의존관계는 외부에서 다이나믹하게 설정

### AOP (Aspect Oriented Programming)
* 관심사의 분리를 통해서 소프트웨어의 모듈성을 향상
* 공통 모듈을 여러 코드에 쉽게 적용 가능

---

## 특징
### 경량 컨테이너
* 자바 객체를 담고 있는 컨테이너
  * 자바 객체의 생성과 소멸과 같은 라이프사이클 관리
* 언제든지 스프링 컨테이너로부터 필요한 객체를 가져와 사용 가능

### DI (의존성 지원)
* 설정 파일이나, 어노테이션을 통해서 객체 간의 의존 관계 설정 가능
  * 객체는 의존하고 있는 객체를 직접 생성하거나 검색할 필요가 없음

### AOP (관점 지향 프로그래밍)
* 문제를 해결하기 위한 *핵심관심 사항*과 전체에 적용되는 *공통관심 사항* 기준으로 프로그래밍
  * 공통 모듈을 여러 코드에 쉽게 적용 가능
* 프록시 기반의 **AOP** 지원 
  * 트랜잭션, 로깅, 보안 같은 공통 기능을 분리하여 각 모듈에 적용 가능

### POJO
* 특정한 인터페이스를 구현하거나 클래스를 상속 없이도 사용 가능

### IoC (제어의 반전)
* *Servlet, EJB* 에 대한 제어권은 개발자가 담당하지 않음

### 트랜잭션 처리를 위한 일관된 방법 제공
* 설정파일을 통해 *트랜잭션 관련정보*를 입력하기 때문에 구현에 상관 없이 동일한 코드 사용 가능

### 영속성과 관련된 다양한 API 지원
* *JDBC* 를 비롯하여 *iBatis, MyBatis, Hibernate, JPA* 등 DB 처리를 위한 라이브러리 연동 지원

### 다양한 API 연동 지원
* *JMS, 메일, 스케쥴링* 등 엔터프라이즈 애플리케이션 개발에 필요한 API 를 설정파일과 어노테이션을 통해 사용 가능

---

## Module
![image](https://user-images.githubusercontent.com/54715744/139258551-e34ecca5-f063-4198-adae-38078f2ff3f4.png)

### Spring Score
* *Spring Framework* 의 핵심 기능을 제공하며, *Core 컨테이너*의 주요 컴포넌트는 **Bean Factory**
  * *Bean Factory* 기반으로 *Bean 클래스*를 제어할 수 있는 기능 지원
* *IoC/DI* 기능을 지원하는 영역 담당

### Spring Context
* *Spring* 을 Framework 로 만든 모듈
* *Bean Factory* 의 개념 확장
  * 국제화된 메시지, Application 생명주기 이벤트, 유효성 검증 지원

### Spring AOP
* 설정 관리 기능을 통해 AOP 기능을 *Spring Framework* 와 직접 통합

### Spring DAO
* *Spring JDBC DAO 추상레이어*는 다른 데이터베이스 벤더들의 예외 핸들링과 오류 메시지를 관리하는 중요한 예외계층 제공

### Spring ORM (Object Relational Mapping)
* *JDO, Hibernate, iBatis* 제공

### Spring Web
* *Application Context module* 상위에 구현되어 *Web 기반 Application* 에 context 제공

### Spring Web MVC
* 자체적으로 *MVC 프레임워크* 제공

---

# IoC & Container

## IoC
![image](https://user-images.githubusercontent.com/54715744/139260958-0c5f7125-af83-41a1-b463-5df2715dbfd8.png)

* 객체 생성을 *Container* 에게 위임하여 처리
* 객체지향 언어에서 Object 간의 연결 관계를 런타임에 결정
* 객체 간의 관계가 느슨하게 연결됨 (결합도가 낮음)
  * 결합도가 높으면 클래스가 유지보수 될 때 그 클래스와 결합된 다른 클래스도 같이 유지보수 해야함 
* *IoC* 의 구현 방법 중 하나가 **DI**

### Dependency Lookup
* 컨테이너가 *lookup context* 를 통해서 필요한 *Resource* 나 *Object* 를 얻는 방식
* *JNDI* 이외의 방법을 사용한다면 JNDI 관련 코드를 오브젝트 내에서 일일히 변경해야 함
* Lookup 한 Object 를 필요한 타입으로 casting 해주어야 함
* Naming Exception 을 처리하기 위한 로직 필요

### Dependency Injection
* 컨테이너가 직접 의존 구조를 Object 에 설정 할 수 있도록 지정해주는 방식
* Object 가 컨테이너의 존재 여부를 알 필요가 없음
* *Setter Injection* 과 *Constructor Injection*

## Container
* 객체의 생성, 사용, 소멸에 해당하는 라이프사이클 담당
* 라이프사이클을 기본으로 애플리케이션 사용에 필요한 주요 기능 제공
  * Dependency 객체 제공
  * Thread 관리
  * 기타 애플리케이션 실행에 필요한 환경
* 비즈니스 로직 외에 부가적인 기능들에 대해서는 독립적으로 관리
* 서비스 look up 이나 Configuration 에 대한 일관성
* 서비스 객체를 사용하기 위해 Factory 또는 Singleton 패턴을 구현하지 않아도 됨

### IoC Container
* 오브젝트의 생성과 관계설정, 사용, 제거 등의 작업 담당
* 코드 대신 오브젝트에 대한 제어권을 갖고 있음 -> 스프링 컨테이너 == Ioc 컨테이너
* 스프링에서 IoC 를 담당하는 컨테이너 : ***Bean Factory, ApplicationContext***

### Spring DI Container
* 관리하는 객체를 **Bean**, *Bean* 의 생명주기를 관리하는 의미로 **Bean Factory** 라 한다
* 여러 가지 컨테이너 기능을 추가하여 **ApplicationContext** 라 한다

#### BeanFacotry
* Bean 등록, 생성, 조회, 반환 관리
* 일반적으로 확장한 *ApplicationContext* 사용
* *getBean()*

#### ApplicationContext
* *BeanFacotry* 와 같은 기능 제공
* Spring 의 각종 부가 서비스 제공

---

# DI
* *Spring Bean* 은 기본적으로 **싱글톤**
  * 컨테이너가 제공하는 모든 빈의 인스턴스는 *동일*
  * 항상 새로운 인스턴스를 반환하고 싶은 경우 scope 를 *prototype* 으로 설정
    * @scope("prototype")
    * \<bean id="id" class="class" scope="prototype"/>
  * Http Request 별로 새로운 인스턴스 생성 : *request*
  * Http Session 별로 새로운 인스턴스 생성 : *Session*

## Bean 설정
### XML
* 빈의 설정 메타 정보를 기술
  * \<bean>
```xml
<bean id="memberDao" class="com.mvc.test.dao.MemberDaoImpl" />
<bean id="memberService" class="com.mvc.test.service.MemberServiceImpl">
  <property name="memberDao" ref="memberDao" />
</bean>
```

### Annotation
* XML 파일을 관리하는 것이 번거로워 사용
* 빈으로 사용될 클래스에 특별한 annotation 을 부여해 자동으로 빈 등록 가능
* 기본적으로 클래스 이름을 *빈의 아이디*로 사용
* 반드시 **component-scan** 을 설정

```xml
<context:component-scan base-package="com.mvc.test.*"/>
```

#### Stereotype Annotation
* 빈 자동등록에 사용할 수 있는 annotation
* 빈 자동인식을 위한 annotation
  * 계층별로 빈의 특성이나 종류 구분
  * AOP Pointcut 표현식을 사용하면 특정 annotation 이 달린 클래스만 설정 가능
  * 특정 계층의 빈에 부가기능 부여

|Annotation|적용 대상|
|----------|---------|
|**@Repository**|Data Access Layer 의 DAO 또는 Repository 클래스에 사용|
||DataAccessException 자동변환과 같은 AOP 적용 대상을 선정하기 위해 사용|
|**@Service**|Service Layer 의 클래스에 사용|
|**@Controller**|Presentation Layer 의 MVC Controller 에 사용|
||스프링 웹 서블릿에 의해 웹 요청을 처리하는 컨트롤러 빈으로 선정|
|**@Component**|위의 Layer 구분을 적용하기 어려운 일반적인 경우에 설정|

## Spring 설정
### XML
* Application 에서 사용할 Spring 자원을 설정하는 파일
* Root tag 는 \<beans>
* 파일명은 상관 없음

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans:beans xmlns="http://www.springframework.org/schema/mvc"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:beans="http://www.springframework.org/schema/beans"
	xmlns:context="http://www.springframework.org/schema/context"
	xsi:schemaLocation="http://www.springframework.org/schema/mvc https://www.springframework.org/schema/mvc/spring-mvc.xsd
		http://www.springframework.org/schema/beans https://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/context https://www.springframework.org/schema/context/spring-context.xsd">
 
 </beans>
```

### 빈 객체 생성 및 주입
* 주입할 객체를 설정 파일에 설정
  * \<bean> : 스프링 컨테이너가 관리할 Bean 객체를 설정
* 기본 속성
  * **name** : 주입 받을 곳에서 호출 할 이름 설정
  * **id** : 주입 받을 곳에서 호출 할 이름 설정 (유일 값)
  * **class** : 주입 할 객체의 클래스
  * **factory-method** : Singleton 패턴으로 작성된 객체의 factory 메소드 호출 

### 빈 객체 얻기
* 설정 파일에 설정한 bean 을 컨테이너가 제공하는 주입기 역할의 api 를 통해 주입 받음

```java
/*
Resource resource = new ClassPathResource("com/mvc/test/controller/applicationContext.xml");
BeanFactory factory = new XmlBeanFactory(resource);
CommonService memberService = (MemberService) factory.getBean("memberService);
*/

ApplicationContext context = new ClassPathXmlApplicationContext("com/mvc/test/controller/applicationContext.xml");
CommonService memberService = context.getBean("memberService", MemberService.class);
```

## 스프링 빈 의존 관계 설정
### Constructor 이용
* 객체 또느 값을 생성자를 통해 주입 받는다
