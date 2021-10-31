# Aspect Oriented Programming
![image](https://user-images.githubusercontent.com/54715744/139575312-0f81c994-9e80-4f05-961a-30764ab6b725.png)


* **핵심 관심 사항(core concern)** 과 **공통 관심 사항(cross-cutting concern)** 을 기준으로 프로그래밍함으로서 공통 모듈을 손쉽게 적용
* 핵심적인 기능에서 부가적인 기능을 분리하고, 분리한 부가기능을 ***Aspect*** 라는 모듈 형태로 만들어 설계하고 개발하는 방법
* 핵심기능을 설계하고 구현할 때 *객체지향*적인 가치를 지킬 수 있도록 도와주는 개념

#### 적용 예
* **간단한 메소드의 성능 검사**
	* 개발 도중 DB 에 데이터를 넣고 빼는 등의 배치 작업에 대하여 시간을 측정하고 쿼리를 개선 할 때
	* 해당 메소드의 처음과 끝에 System.currentTimeMillis() 또는 스프링의 StopWatch 코드를 사용하기는 번거로움
	* 해당 작업을 하는 코드를 *밖에서 설정*하고 해당 부분을 사용하는것이 편리
* **트랜잭션 처리**
	* 트랜잭션의 경우, 비즈니스 로직의 전후에 설정
	* 매번 사용하는 트랜잭션 코드는 번거롭고 복잡함
* **예외 반환**
	* 스프링에는 *DataAccessException* 이라는 예외 계층 구조가 있음
	* ***Aspect*** 는 본인의 프레임워크나 애플리케이션에서 별도의 예외 계층 구조로 변환하고 싶을 때 유용
* **아키텍처 검증**
* **기타**
	* *Hibernate* 와 *JDBC* 를 같이 사용할 경우, DB 동기화 문제 해결
	* 멀티스레드 safety 관련하여 작업하는 경우, 메소드들에 일괄적으로 락을 설정하는 Aspect
	* 데드락 등으로 인한 *PessimisticLockingFailureException* 등의 예외를 만났을 때 재시도하는 Aspect
	* 로깅, 인증, 권한 등

## AOP 구조
* *핵심 관심 사항*에 *공통 관심 사항*을 어떻게 적용시킬 것인가가 관건

#### 예시
* *핵심 관심 사항* : BankingService, AccountService, CustomerService
* *공통 관심 사항* : Security, Transaction, Other...

## AOP 용어
### Target
* **핵심 기능을 담고 있는 모듈**로 ***부가기능을 부여할 대상***이 됨

### Advice
* **어느 시점(수행 전/후, 예외 발생 후 등..) 에 어떤 공통 관심 기능(*Aspect*) 을 적용할지 정의**
* ***Target에 제공할 부가기능을 담고 있는 모듈***

### JoinPoint
* ***Aspect* 가 적용 될 수 있는 지점(method, field)**
* ***Target 객체가 구현한 인터페이스의 모든 method***는 **JoinPoint**

### Pointcut
* **공통 관심 사항이 적용될 *JoinPoint***
* ***Advice 를 적용할 Target 의 method 를 선별하는 정규 표현식***
* *execution* 으로 시작하고 method 의 *Signature* 를 비교하는 방법을 주로 이용

|Pointcut|선택된 JoinPoint|
|--------|----------------|
|**execution(public \* \*(..))**|public 메소드 실행|
|**execution(\* set\*(..))**|이름이 set 으로 시작하는 모든 메소드 실행|
|**execution(\* com.test.serviceAccountService.\*(..))**|AccountService 인터페이스의 모든 메소드 실행|
|**execution(\* com.test.service.\*.\*(..))**|service 패키지의 모든 메소드 실행|
|**execution(\* com.test.service..\*.\*(..))**|service 패키지와 하위 패키지의 모든 메소드 실행|
|**within(com.test.service.\*)**|service 패키지 내의 모든 결합점|
|**within(com.test.service..\*)**|service 패키지 및 하위 패키지의 모든 결합점|
|**this(com.test.service.AccountService)**|AccountService 인터페이스를 구현하는 프록시 개체의 모든 결합점|
|**target(com.test.service.AccountService)**|AccountService 인터페이스를 구현하는 대상 객체의 모든 결합점|
|**args(java.io.Serializable)**|하나의 파라미터를 갖고 전달된 인자가 Serializable 인 모든 결합점|
|**@target(org.springframework.transaction.annotation.Transactional)**|대상 객체가 @Transactional 을 갖는 모든 결합점|
|**@within(org.springframework.transaction.annotation.Transactional)**|대상 객체의 선언 타입이 @Transactional 을 갖는 모든 결합점|
|**@annotation(org.springframework.transaction.annotation.Transactional)**|실행 메소드가 @Transactional 을 갖는 모든 결합점|
|**@args(com.test.security.Classified)**|단일 파라미터를 받고, 전달된 인자 타입이 @Classified 를 갖는 모든 결합점|
|**bean(accountRepository)**|accountRepository 빈|
|**!bean(accountRepository)**|accountRepository 빈을 제외한 모든 빈|
|**bean(\*)**|모든 빈|
|**bean(account\*)**|이름이 account 로 시작되는 모든 빈|
|**bean(\*Repository)**|이름이 Repository 로 끝나는 모든 빈|
|**bean(accounting/\*)**|이름이 accounting/ 으로 시작하는 모든 빈|
|**bean(\*dataSource)\|\|bean(\*DataSource)**|이름이 dataSource 나 DataSource 로 끝나는 모든 빈|

### Aspect
* **여러 객체에서 공통으로 적용되는 공통 관심 사항(transaction, logging, security...)**
* ***AOP 의 기본 모듈***
* ***Aspect = Advice + Pointcut***
* **Singleton 형태의 객체**

### Advisor
* ***Advisor = Advice + Pointcut***
* Spring AOP 에서만 사용되는 용어

### Weaving
* **어떤 *Advice* 를 어떤 *Pointcut(핵심사항)* 에 적용시킬 것인지에 대한 설정(*Advisor*)**
* ***Pointcut 에 의해서 결정된 Target 의 JoinPoint 에 부가기능(Advice) 를 삽입하는 과정***
* AOP 의 핵심기능(*Target*) 의 코드에 영향을 주지 않으면서 필요한 부가기능(*Advice*) 을 추가할 수 있도록 해주는 핵심적인 처리과정

---

# Spring AOP
### Proxy 기반 AOP 지원
* ***Target 객체***에 대한 **Proxy** 를 만들어 제공
	* 실행시간에 생성
	* **Proxy** 는 ***Advice 를 Target 객체에 적용하면서 생성되는 객체***

### Proxy 가 호출을 가로챈다 (Intercept)
* **전처리 Advice** : ***Target***에 대한 호출을 가로챈 다음 ***Advice 의 부가기능 로직을 수행하고 난 후에 Target 의 핵심 기능 로직을 호출***
* **후처리 Advice** : ***Target 의 핵심 기능 로직을 호출한 후에 Advice 의 부가기능을 수행***

### 메소드 JoinPoint 만 지원
* **동적 Proxy** 기반으로 AOP 를 구현하므로 **method JoinPoint** 만 지원
* **핵심기능(*Target*) 의 method 가 호출되는 런타임 시점에만 부가기능(*Advice*) 를 적용할 수 있음**
* AspectJ 같은 고급 AOP framework 이용 시, 객체의 생성, 필드값의 조회와 조작, static method 호출 및 초기화 등의 다양한 작업에 부가기능 적용 가능

# Spring AOP 구현 방법
## POJO Class 이용
### XML Schema 확장 기법을 통해 설정파일 작성
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
	xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
	xmlns:aop="http://www.springframework.org/schema/aop"
	xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd
		http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-3.0.xsd">
	
	<bean id="logging" class="com.test.aop.LoggingTest"/>
	
	<aop:config>
		<aop:aspect id="logAspect" ref="logging">
			<aop:pointcut id="logmethod" expression="execution(public * com.test.aop..*(..))"/>
			<aop:around pointcut-ref="logmethod" method="pringlog"/>
		</aop:aspect>
	</aop:config>
	
</beans>
```

* aop namespace 와 XML Schema 추가
* aop namespace 를 이용한 설정
* **\<aop:aspect> 설정**
	* ***한 개의 Aspect(공통 관심 기능) 설정***
	* *ref* 속성을 통해 공통 기능을 가지고 있는 *bean* 을 연결
	* *id* 는 태그의 식별자 설정
	* 자식 태그로 *\<aop:pointcut>, advice* 관련 태그가 올 수 있다
* **\<aop:pointcut> 설정**
	* ***Pointcut(공통 기능이 적용될 곳) 을 지정***
	* *\<aop:config> 나 \<aop:aspect> 의 자식 태그*
		* \<aop:config> 전역적으로 사용
		* \<aop:aspect> 내부에서 사용
	* *AspectJ 표현식*을 통해 pointcut 지정
	* *id* 는 advice 태그의 식별자
	* *expression* 은 pointcut 지정

#### AOP 설정 태그
|Tag|설명|
|---|----|
|**\<aop:config>**|aop 설정의 root 태그 (weaving 들의 묶음)|
|**\<aop:aspect>**|Aspect 설정 (하나의 weaving 에 대한 설정)|
|**\<aop:pointcut>**|Pointcut 설정|

#### Advice 설정 태그
|Tag|설명|
|---|----|
|**\<aop:before>**|method 실행 전 실행될 Advice|
|**\<aop:after-returning>**|method 가 정상 실행 후 실행될 Advice|
|**\<aop:after-throwing>**|method 에서 예외 발생시 실행될 Advice (catch block)|
|**\<aop:after>**|method 가 정상 또는 예외 발생에 상관없이 실행될 Advice (finally block)|
|**\<aop:around>**|모든 시점(실행 전, 후) 에서 적용시킬 수 있는 Advice|

### POJO 기반 Advice Class 작성
* 설정 파일의 *Advice* 관련 태그에 맞게 작성
* \<bean> 으로 등록하며 \<aop:aspect> 의 *ref* 속성으로 참조
* 공통 기능 메소드 : Advice 관련 태그들의 method 속성의 값이 method 의 이름이 된다

#### Before Advice
* **대상 객체의 메소드가 실행되기 전에 실행됨**
* **return type** : 리턴 값을 갖더라도 실제 Advice 의 적용과정에서 사용되지 않기 때문에 ***void*** 를 쓴다
* **parameter** : 없거나 대상객체 및 호출되는 메소드에 대한 정보 또는 파라미터에 대한 정보가 필요하다면 ***JoinPoint*** 에 전달

```xml
<aop:config>
	<aop:aspect id="beforeAspect" ref="userCheckAdvice">
		<aop:pointcut id="publicMethod" expression="execution(public * com.test.spring.aop..*Controller.*(..))"/>
		<aop:before method="before" pointcut-ref="publicMethod"/>
	</aop:aspect>
</aop:config>
```

```java
public void before(JoinPoint joinPoint) {
	String name = joinPoint.getSignature().toShortString();
	System.out.println("Advice : " + name);
}
```

1. Bean 객체를 사용하는 코드에서 스프링이 생성한 AOP 프록시의 메소드를 호출
2. AOP 프록시는 \<aop:before> 에서 지정한 메소드를 호출
	* 메소드에서 exception 을 발생시킬 경우 대상 객체의 메소드가 호출 되지 않음
3. AOP 프록시는 Aspect 기능 실행 후 실제 Bean 객체의 메소드를 호출 

#### After Returning Advice
* **대상 객체의 메소드 실행이 정상적으로 끝난뒤 실행됨**
* **return type** : ***void***
* **parameter** : 없거나 ***JoinPoint*** 또는 ***메소드에서 반환되는 특정 객체 타입***
	* JoinPoint 는 항상 첫번째로 온다

```xml
<aop:config>
	<aop:aspect id="afterAspect" ref="historyAdvice">
		<aop:pointcut id="publicMethod" expression="execution(public * com.test.spring.aop..*Controller.*(..))"/>
		<!-- 메소드가 정상적으로 결과값을 리턴했을 경우 -->
		<aop:after-returning method="history" pointcut-ref="publicMethod" returning="ret"/>
	</aop:aspect>
</aop:config>
```

```java
public void history(JoinPoint joinPoint, Object ret) throws Throwable {
	System.out.println("HistoryAdvice : " + ret);
}
```

1. Bean 객체를 사용하는 코드에서 스프링이 생성한 AOP 프록시의 메소드를 호출
2. AOP 프록시는 실제 Bean 객체의 메소드를 호출 (정상 실행)
3. AOP 프록시는 \<aop:after-returning> 에서 지정한 메소드를 호출

#### After Advice
* **대상 객체의 메소드가 정상적으로 실행 되었는지 아니면 exception 을 발생 시켰는지의 여부와 상관 없이 메소드 실행 종료 후 공통 기능 적용**
* **return type** : *void*
* **parameter** : 없거나 ***JoinPoint***

1. Bean 객체를 사용하는 코드에서 스프링이 생성한 AOP 프록시의 메소드 호출
2. AOP 프록시는 실제 Bean 객체의 메소드 호출(정상 실행, exception 발생 : java 의 finally 와 같음)
3. AOP 프록시는 \<aop:after> 에서 지정한 메소드를 호출

#### Around Advice
* **위의 네가지 Advice 를 모두 구현하는 Advice**
* **return type** : Object
* **parameter** : ***org.aspectj.lang.ProceedingJoinPoint***

1. Bean 객체를 사용하는 코드에서 스프링이 생성한 AOP 프록시의 메소드 호출
2. AOP 프록시는 \<aop:around> 에서 지정한 메소드 호출
3. AOP 프록시는 실제 Bean 객체의 메소드 호출
4. AOP 프록시는 \<aop:around> 에서 지정한 메소드 호출

#### JoinPoint Class 구성 요소
* 대상 객체에 대한 정보를 가지고 있는 객체로 Spring Container 로부터 받는다
* *org.aspectj.lang*
* 반드시 Aspect method 의 첫번째 인자로 와야한다

|주요 Method|설명|
|-----------|----|
|**Object getTarget()**|대상 객체를 리턴|
|**Object[] getArgs()**|파라미터로 넘겨진 값들을 배열로 리턴 (없으면 빈 배열)|
|**Signature getSignature()**|호출되는 메소드의 정보 (Signature: 호출되는 메소드의 정보를 가진 객체)|
|**String getName()**|메소드 이름|
|**String toLongString()**|메소드 전체 syntax 를 리턴|
|**String toShortString()**|메소드를 축약해서 리턴 (기본은 메소드 이름)|

## Spring API 이용

## Annotation 이용
### @Aspect 를 이용하여 Aspect Class 에 직접 Advice 및 Pointcut 설정
* @Aspect : Aspect Class 선언
* @Before("pointcut")
* @AfterReturning(pointcut="", returning="")
* @AfterThrowing(pointcut="", throwing="")
* @After("pointcut")
* @Around("pointcut")
<br/>

* *Around* 를 제외한 나머지 메소드는 첫 인자로 ***JoinPoint*** 를 가질 수 있음
* *Around* 는 ***ProceedingJoinPoint*** 를 인자로 가질 수 있음

```java
@Aspect
public class PerformanceTraceAdvice() {
	
	@Pointcut("execution(public * com.test.spring.aop..*Controller.*(..))")
	public void profileTarget() {}
	
	@Around("profileTarget()")
	public Object trace(ProceedingJoinPoint joinPoint) throws Throwable {
		String signature = joinPoint.getSignature().toShortString();
		long start = System.currentTimeMillis();
		try {
			Object result = joinPoint.proceed();
			return result;
		} finally {
			long finish = System.currentTimeMillis();
			System.out.println("PerformanceTraceAdvice : " + signature + " 실행시간 - " + (finish - start) + "ms");
		}
	}
}
```
### 설정파일에 \<aop:aspectj-autoproxy/> 추가
### Aspect Class 를 \<bean> 으로 등록

```xml
<context:component-scan base-package="com.test.aop" />
	
<aop:aspectj-autoproxy />
```
