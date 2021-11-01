# SpringBoot
* *Spring* 의 경우, Application 을 개발하려면 사전에 많은 작업 필요
	* Library 추가, dependency 설정, Spring Framework 가 처리해야 하는 여러 가지 구성 및 설정파일 등)
* **SpringBoot** 의 장점
	* Project 에 따라 자주 사용되는 *Library* 가 미리 조합되어 있음
	* 복잡한 설정을 자동으로 처리
	* 내장 서버를 포함해서 tomcat 과 같은 *WAS* 를 추가로 설치하지 않아도 개발 가능
	* WAS 에 배포하지 않고도 실행할 수 있는 *JAR* 파일로 Web Application 개발 가능

## SpringBoot Project 생성
* *Spring Starter Project* 를 이용하여 윈도우의 Install Wizard 와 같이 프로젝트 생성 가능
	* 버전 및 dependency 설정 가능

* **주요 구성 폴더/파일**
	* **src/main/java** : java source directory
	* **Application.java** : application 을 시작할 수 있는 main method 가 존재하는 스프링 구성 메인 클래스
	* **static** : css, js, img 등의 정적 resource directory
	* **templates** : SpringBoot 에서 사용 가능한 여러가지  View Template(Thymeleaf, Velocity, FreeMaker 등) 위치
	* **application.properties** : application 및 스프링의 설정 등에서 사용할 여러가지 property 를 정의한 file
	* **src/main** : jsp 등의 resource directory

## application.properties
```properties
# server setting
## context path 설정
server.servlet.context-path=/hello

## server port 설정
server.port=8000


# jsp setting (SpringBoot 는 jsp 를 지원하지 않으므로 dependency(pom.xml) 에 jsp 설정을 추가해야함
spring.mvc.view.prefix=/WEB-INF/views/
spring.mvc.view.suffix=.jsp

# DataBase Setting
# spring.datasource.hikari.maximum-pool-size=4
spring.datasource.driver-class-name=com.mysql.cj.jdbc.Driver
spring.datasource.url=jdbc:mysql://localhost:3306/ssafyweb?serverTimezone=UTC&useUniCode=yes&characterEncoding=UTF-8
spring.datasource.username=ssafy
spring.datasource.password=ssafy

# MyBatis Setting
mybatis.type-aliases-package=com.ssafy.guestbook.vo
mybatis.mapper-locations=mapper/**/*.xml

# File Upload size Setting
spring.servlet.multipart.max-file-size=5MB
spring.servlet.multipart.max-request-size=5MB

# server restart
spring.devtools.restart.additional-paths=.

# log level Setting
logging.level.root=info
logging.level.com.ssafy.guestbook=debug
# logging.level.com.rest.mapper=trace
```

### jsp 설정 (pom.xml)
```xml
<!-- jsp 설정 -->
<dependency>
	<groupId>javax.servlet</groupId>
	<artifactId>jstl</artifactId>
</dependency>
<dependency>
	<groupId>org.apache.tomcat.embed</groupId>
	<artifactId>tomcat-embed-jasper</artifactId>
</dependency>
```

---

# Swagger
* **Swagger 를 이용한 REST API 문서화**
	* BackEnd 개발자가 만든 문서 API 를 보며 FrontEnd 개발자가 데이터 처리를 할 수 있도록 사용
* **간단한 설정으로 프로젝트의 API 목록을 웹에서 확인 및 테스트 할 수 있게 해주는 Library**
	* *Controller* 에 정의되어 있는 모든 URL 을 바로 확인 가능
	* *API 목록 뿐만 아니라 API 명세 및 설명*을 볼 수 있으며, 테스트 가능

## Swagger 적용
### *pom.xml* 에 swagger2 dependency 추가

```xml
<!-- Swagger Setting -->
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger2 -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>2.9.2</version>
</dependency>		
<!-- https://mvnrepository.com/artifact/io.springfox/springfox-swagger-ui -->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>2.9.2</version>
</dependency>
```

### *SwaggerConfiguration.java*

```java
@Configuration
@EnableSwagger2
public class SwaggerConfiguration {

//	Swagger 설정 확인
//	http://localhost:9999/{your-app-root}/v2/api-docs
//	Swagger-UI 확인
//	http://localhost:9999/{your-app-root}/swagger-ui.html

	private String version = "V1";
	private String title = "SSAFY WordCloud API " + version;
	
	@Bean
	public Docket api() {
		List<ResponseMessage> responseMessages = new ArrayList<ResponseMessage>();
		responseMessages.add(new ResponseMessageBuilder().code(200).message("OK !!!").build());
		responseMessages.add(new ResponseMessageBuilder().code(500).message("서버 문제 발생 !!!").responseModel(new ModelRef("Error")).build());
		responseMessages.add(new ResponseMessageBuilder().code(404).message("페이지를 찾을 수 없습니다 !!!").build());
		return new Docket(DocumentationType.SWAGGER_2).consumes(getConsumeContentTypes()).produces(getProduceContentTypes())
					.apiInfo(apiInfo()).groupName(version).select()
					.apis(RequestHandlerSelectors.basePackage("com.ssafy.word.controller"))
					.paths(postPaths()).build()
					.useDefaultResponseMessages(false)
					.globalResponseMessage(RequestMethod.GET,responseMessages);
	}
	
	private Set<String> getConsumeContentTypes() {
        Set<String> consumes = new HashSet<>();
        consumes.add("application/json;charset=UTF-8");
        consumes.add("application/x-www-form-urlencoded");
        return consumes;
    }

    private Set<String> getProduceContentTypes() {
        Set<String> produces = new HashSet<>();
        produces.add("application/json;charset=UTF-8");
        return produces;
    }
	
	private Predicate<String> postPaths() {
		return regex("/word/.*");
	}

	private ApiInfo apiInfo() {
		return new ApiInfoBuilder().title(title)
				.description("<h3>SSAFY API Reference for Developers</h3>Swagger를 이용한 WordCloud API<br><img src=\"img/ssafy_logo.png\" width=\"150\">") 
				.contact(new Contact("SSAFY", "https://edu.ssafy.com", "ssafy@ssafy.com"))
				.license("SSAFY License")
				.licenseUrl("https://www.ssafy.com/ksp/jsp/swp/etc/swpPrivacy.jsp")
				.version("1.0").build();

	}
}
```

### Swagger 가 적용 될 *Controller*
```java
@CrossOrigin(origins = { "*" }, maxAge = 6000)
@RestController
@RequestMapping("/word")
@Api("Word Cloud Controller API")
public class WordCloudController {

	private static final Logger logger = LoggerFactory.getLogger(WordCloudController.class);
	
	@Autowired
	private WordService wordService;
	
	@ApiOperation(value = "관심단어 목록", notes = "회원들의 <b>관심단어의 목록</b>을 리턴합니다.")
	@ApiResponses({
		@ApiResponse(code = 200, message = "성공!!!"),
		@ApiResponse(code = 404, message = "Page Not Found!!!"),
		@ApiResponse(code = 500, message = "Server Error!!!")
	})
	@GetMapping("/")
	public ResponseEntity<List<WordDto>> listWord() {
		logger.debug("listWord - 호출");
		return new ResponseEntity<>(wordService.listWord(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "관심단어 등록", notes = "회원들의 <b>관심단어</b>를 등록합니다.")
	@PostMapping("/")
	public ResponseEntity<List<WordDto>> registWord(@RequestParam(value = "concerns[]") List<String> concerns) {
		logger.debug("registWord - 호출");
		wordService.registWord(concerns);
		return new ResponseEntity<>(wordService.listWord(), HttpStatus.OK);
	}
	
	@ApiOperation(value = "관심단어 수정", notes = "회원들의 <b>관심단어</b>를 수정합니다.")
	@PostMapping("{word}")
	public ResponseEntity<List<WordDto>> updateWordCount(@PathVariable("word") String word) {
		logger.debug("updateWordCount - 호출");
		wordService.updateCount(word);
		return new ResponseEntity<>(wordService.listWord(), HttpStatus.OK);
	}
	
}
```

### Swagger 가 적용 될 *Model(Dto)*
```java
@ApiModel(value = "WordDto : 관심단어", description = "관심단어와 비중을 가진 domain class 입니다.")
public class WordDto {

	@ApiModelProperty(value = "관심단어")
	private String text;
	@ApiModelProperty(value = "비중")
	private double weight;

	public String getText() {
		return text;
	}

	public void setText(String text) {
		this.text = text;
	}

	public double getWeight() {
		return weight;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}

}
```
