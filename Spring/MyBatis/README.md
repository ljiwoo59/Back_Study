# MyBatis
* **Java Object 와 SQL 문 사이의 *자동 Mapping* 기능을 지원하는 *ORM Framework***
* *SQL* 을 별도의 파일로 분리해서 관리
* Hibernate 나 JPA(Java Persistence API) 처럼 새로운 DB 프로그래밍 패러다임을 익히지 않아도 됨
* *개발자가 익숙한 SQL 을 그대로 사용하며 JDBC 코드 작성의 불편함을 제거하고, 도메인 객체나 VO 객체를 중심으로 개발 가능*

#### 쉬운 접근성과 코드의 간결함
* 가장 간단한 *persistence framework*
* XML 형태로 서술된 JDBC 코드라 생각해도 될 만큼 *JDBC 의 모든 기능을 제공*
* 복잡한 JDBC 코드를 걷어내며 깔끔한 코드 유지
* 수동적인 parameter 설정과 Query 결과에 대한 mapping 구문을 제거

#### SQL 문과 프로그래밍 코드의 분리
* SQL 에 변경이 있을 때 마다 자바 코드를 수정하거나 컴파일 하지않아도 됨
* SQL 작성과 관리 또는 검토를 DBA 와 같은 개발자가 아닌 다른 사람에게 맡길 수 있음

#### 다양한 프로그래밍 언어로 구현 가능
* Java, C#, .NET, Ruby...

---

# MyBatis-Spring
## MyBatis 와 MyBatis-Spring 을 사용한 DB Access Architecture
![image](https://user-images.githubusercontent.com/54715744/139578678-25951513-b0b5-44d3-99c7-bbfb43c5879e.png)

## MyBatis 를 사용하는 Data Access Layer
![image](https://user-images.githubusercontent.com/54715744/139578746-a3c547a6-530a-4f2d-bc14-d9f58c8d8aed.png)

## MyBatis 3 의 주요 Component
![image](https://user-images.githubusercontent.com/54715744/139578785-7d84fb17-3000-4ab9-a317-245adfb05c6d.png)

* **MyBatis 설정파일** (sqlMapConfig.xml)
	* 데이터베이스의 접속 주소 정보나 객체의 alias, Mapping 파일의 경로 등의 고정된 환경 정보를 설정
* **SqlSessionFactoryBuilder**
	* MyBatis 설정파일을 바탕으로 SqlSessionFactory 생성
* **SqlSessionFactory**
	* SqlSession 생성
* **SqlSession**
	* 핵심적인 역할을 하는 Class 로 SQL 실행이나 Transaction 관리 실행
	* SqlSession 오브젝트는 Tread-Safe 하지 않으므로 thread 마다 필요에 따라 생성
* **mapping 파일** (member.xml)
	* SQL 문과 ORMapping 설정

## MyBatis-Spring 의 주요 Component
![image](https://user-images.githubusercontent.com/54715744/139579506-acb61a41-e5d4-43e8-a137-f25928dd7c23.png)

* **MyBatis 설정파일** (sqlMapConfig.xml)
	* Dto 객체 정보를 설정 (alias)
* **SqlSessionFactoryBean**
	* MyBatis 설정파일을 바탕으로 SqlSessionFactory 생성
	* Spring Bean 으로 등록해야함
* **SqlSessionTemplate**
	* 핵심적인 역할을 하는 Class 로 SQL 실행이나 Transaction 관리 실행
	* SqlSession Interface 를 구현하며, Thread-Safe 하다
	* Spring Bean 으로 등록해야함
* **mapping 파일** (member.xml)
	* SQL 문과 ORMapping 설정
* **Spring Bean 설정파일** (beans.xml)
	* SqlSessionFactoryBean 을 Bean 에 등록할 때 DataSource 정보와 MyBatis Config 파일 정보, Mapping 파일 정보를 함께 설정
	* SqlSessionTemplate 을 Bean 으로 등록

## Mapper Interface
* Mapping 파일에 기재된 SQL 을 호출하기 위한 Interface
* SQL 을 호출하는 프로그램을 Type Safe 하게 기술
* Mapping 파일에 있는 SQL 을 Java Interface 를 통해 호출할 수 있게 해줌

### Mapper Interface 를 사용하지 않을 경우
* SQL 을 호출하는 프로그램은 SqlSession 의 method 의 argument 에 문자열로 **namespace + "." + SQL ID** 로 지정
* 오타에 의한 버그 가능성, IDE 에서 제공하는 code assist 를 사용할 수 없음
* session.selectOne("com.test.MemberDao.search", userid);

### Mapper Interface 를 사용할 경우
* UserMapper Interface 는 개발자가 작성
* **packagename + "." + InterfaceNmae + "." + methodName** 이 **namespace + "." + SQL ID** 가 되도록 설정
* namespace 속성에는 package 를 포함한 Mapper Interface 이름을 작성
* SQL ID 에는 mapping 하는 method 의 이름을 지정
* userMapper.search(userid);

## MyBatis 와 Spring 연동
* MyBatis 를 Standalone 형태로 사용하는 경우, SqlSessionFactory 객체를 직접 사용
* *Spring 을 사용하는 경우*, 스프링 컨테이너에 MyBatis 관련 Bean 을 등록하여 사용
	* 제공하는 트랜잭션 기능을 사용하면 손쉽게 트랜잭션 처리 가능
	* MyBatis 에서 제공하는 Spring 연동 라이브러리가 필요

```xml
<dependency>
	<groupId>org.mybatis</groupId>
	<artifactId>mybatis-spring</artifactId>
	<version>2.0.3</version>
</dependency>
```

### DataSource 설정
* Spring 을 사용하는 경우, Spring 에서 DataSource 를 관리하므로 MyBatis 설정파일에서는 일부 설정을 생략
* Spring 환경설정 파일(application-context.xml) 에 DataSource 를 설정
	* dataSource 아이디를 가진 Bean 으로 데이터베이스 연결정보를 가진 객체
* 데이터베이스 설정과 트랜잭션 처리는 Spring 에서 관리

#### 일반 설정
```xml
<bean id="dataSource" class="org.springframework.jdbc.datasource.SimpleDriverDataSource">
	<property name="driverClass" value="com.mysql.cj.jdbc.Driver"/>
	<property name="url" value="jdbc:mysql://127.0.0.1:3306/ssafyweb?serverTimezone=UTC&amp;useUniCode=yes&amp;characterEncoding=UTF-8"/>
	<property name="username" value="ssafy"/>
	<property name="password" value="ssafy"/>
</bean>
```

#### ConnectionPoll 설정
```xml
<bean id="dataSource" class="org.springframework.jndi.JndiObjectFactoryBean">
	<property name="jndiName" value="java:comp/env/jdbc/ssafy"></property>
</bean>
```

### 트랜잭션 관리자 설정
* *transactionManager* 아이디를 가진 Bean 은 트랜잭션을 관리하는 객체
* MyBatis 는 JDBC 를 그대로 사용하기 때문에 DataSourceTransactionManager 타입의 Bean 을 사용
* *tx:annotation-driven* 요소는 트랜잭션 관리방법을 Annotation 으로 선언하도록 설정
* Spring 은 메소드나 클래스에 **@Transactional** 이 선언되어 있으면, AOP 를 통해 트랜잭션 처리

#### 트랜잭션 관리자 설정
```xml
<bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
	<property name="dataSource" ref="ds"/>
</bean>
```

#### Annotation 기반 트랜잭션 설정
```xml
<tx:annotation-driven />
<!-- or -->
<tx:annotation-driven transaction-manager="transactionManager"/>
```

### SqlSessionFactoryBean 설정
* MyBatis 애플리케이션은 SqlSessionFactory 중심으로 수행
* Spring 에서 SqlSessionFactory 객체를 생성하기 위해서는 SqlSessionFactoryBean 을 Bean 으로 등록해야 함
	* 사용할 DataSource 와 MyBatis 설정파일 정보가 필요

#### 직접 설정
```xml
<bean id="sqlSessionFactoryBean" class="org.mybatis.spring.SqlSessionFactoryBean">
	<property name="dataSource" ref="ds"></property>
	<property name="configLocation" value="classpath:mybatis-config.xml"></property>
	<property name="mapperLocations">
		<list>
			<value>classpath:mapper/guestbook.xml</value>
			<value>classpath:mapper/member.xml</value>
		</list>
	</property>
</bean>
```

#### MyBatis Config 파일을 사용하지 않고 자동 설정
```xml
<bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
	<property name="dataSource" ref="ds"/>
	<property name="typeAliasesPackage" value="com.mvc.vo"/>
	<property name="mapperLocations" value="classpath*:mapper/**/*.xml"></property>	
</bean>
```

### Mapper Bean 등록
* Mapper Interface 를 사용하기 위해 *Scanner 를 사용하여 자동으로 등록하거나, 직접 등록*
* *mapperScannerConfigurer* 을 설정하면, Mapper Interface 를 자동으로 검색하여 Bean 으로 등록
	* *basePackage* 로 패키지로 설정하면, 해당 패키지 하위의 모든 Mapper Interface 가 등록
* *MapperFactoryBean class* 는 직접 등록할 때 사용

#### Mapper Scanner 사용
```xml
<!-- MapperScannerConfigurer:java mapper를 해당 패키지에서 찾아서 proxy 객체를 생성한 후 ServiceImpl에 주입시킴 -->
<bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
	<property name="basePackage" value="com.mvc.mapper"/>
</bean>
```

#### 직접 등록
```xml
<bean id="authorMapper" class="org.mybatis.spring.mapper.MapperFactoryBean">
	<property name="mapperInterface" value="com.mvc.mapper.AuthorMapper" />
	<property name="sqlSessionFactory" ref="sqlSessionFactory" />
</bean>
```

### MyBatis Configuration 설정
* Spring 을 사용하면 DB 접속정보 및 Mapper 관련 설정은 Spring Bean 으로 등록하여 관리
* MyBatis 환경설정 파일에는 Spring 에서 관리하지 않는 일부 정보만 설정
	* typeAlias, typeHandler 등

```xml
<?xml version="1.0" encoding="UTF-8" ?>
<!DOCTYPE configuration PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
"http://mybatis.org/dtd/mybatis-3-config.dtd">

<configuration>
	
	<typeAliases>
		<typeAlias type="com.ssafy.guestbook.model.MemberDto" alias="member"/>
		<typeAlias type="com.ssafy.guestbook.model.GuestBookDto" alias="guestbook"/>
		<typeAlias type="com.ssafy.guestbook.model.FileInfoDto" alias="fileinfo"/>
	</typeAliases>
	
</configuration>
```

### 데이터 접근 객체 구현
* 데이터 접근 객체는 특정한 기술을 사용하여 데이터 저장소에 접근하는 방식을 구현한 객체
* **@Repository** 는 데이터 접근 객체를 Bean 에 등록하기 위해 사용하는 Spring 에서 제공하는 Annotation
* **@Autowired** 를 통해, 사용하려는 Mapper Interface 를 데이터 접근 객체와 의존관계를 설정
