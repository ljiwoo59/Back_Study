# REST API

#### OPEN API (Application Programming Interface)
* 프로그래밍에서 사용할 수 있는 개방되어 있는 상태의 Interface
* 대부분의 OPEN API 는 *REST 방식으로 지원*

## REST (Representational State Transfer)
* 하나의 URI 는 하나의 고유한 Resource 를 대표하도록 설계된다는 개념에 전송방식을 결합해 원하는 작업을 지정
	* URI + *GET|POST|PUT|DELETE*
* 웹의 장점을 최대한 활용할 수 있는 아키텍처
* HTTP URI 를 통해 제어할 Resource 를 명시하고, HTTP Method(GET, POST, PUT, DELETE) 를 통해 해당 Resource 를 제어하는 명령을 내리는 방식의 아키텍처

## 구성
* **자원 (Resource) - URI**
* **행위 (Verb) - HTTP Method**
* **표현 (Representations)**
<br/>

* 잘 표현된 HTTP URI 로 Resource 를 정의하고 HTTP Method 로 Resource 에 대한 행위를 정의
* Resource 는 JSON, XML 과 같은 여러가지 언어로 표현할 수 있다

## 기존 Service 와 REST Service
### 기존 Service
* 요청에 대한 처리를 한 후 가공된 data 를 이용하여 ***특정 플랫폼에 적합한 형태의 View*** 로 만들어서 반환

### REST Service
* data 처리만 한다거나, 처리 후 반환될 data 가 있다면 ***JSON 이나 XML 형식***으로 전달
* View 에 대해서는 신경 쓸 필요가 없다
<br/>

* 기존의 전송방식과는 달리 서버는 요청으로 받은 리소스에 대해 *순수한 데이터를 전송*
* 기존은 *GET/POST* 외에, *PUT/DELETE* 방식을 사용하여 리소스에 대한 CRUD 처리 가능
* 정해진 표준이 없어 암묵적인 표준만 있다
	* "-" 는 사용 가능하나 "\_" 는 사용하지 않음
	* 특별한 경우를 제외하고 대문자 사용은 하지 않음 (대소문자를 구분한다)
	* URI 마지막에 "/" 를 사용하지 않음
	* "/" 로 계층 관계를 나타냄
	* 확장자가 포함된 파일 이름을 직접 포함시키지 않음
	* URI 는 명사를 이용

### 웹 접근 방식
|작업|기존 방식|REST 방식|ex|
|----|---------|---------|--|
|**Create**(Insert)|**POST**  /write.do?id=troment|**POST**  /blog/troment|글쓰기|
|**Read**(Select)|**GET**  /view.do?id=troment&articleno=25|**GET**  /blog/troment/25|글읽기|
|**Update**(Update)|**POST**  /modify.do?id=troment|**PUT**  /blog/troment|글수정|
|**Delete**(Delete)|**GET**  /delete.do?id=troment&articleno=25|**DELETE**  /blog/troment/25|글삭제|

* 기존의 블로그 등은 GET 과 POST 만으로 자원에 대한 CRUD 를 처리하며, URI 는 액션을 나타냄
* REST 로 변경할 경우 4가지 method 를 모두 사용하여 CRUD 를 처리하며, URI 는 제어하려는 자원을 나타냄

## REST API 설정
### Jackson Library
* ***jackson-databing*** 라이브러리는 ***객체를 JSON 포맷의 문자열로 변환시켜 브라우저로 전송***
* ***jackson-dataformat-xml*** 라이브러리는 ***객체를 xml 로 브라우저로 전송***
* *pom.xml* 에 library 추가

### Annotation
* **@RestController**
	* Controller 가 REST 방식을 처리하기 위한 것임을 명시
* **@ResponseBody**
	* JSP 같은 View 로 전달되는 것이 아니라 데이터 자체를 전달
* **@PathVariable**
	* URL 경로에 있는 값을 파라미터로 추출
* **@CrossOrigin**
	* Ajax 의 크로스 도메인 문제를 해결
* **@RequestBody**
	* JSON 데이터를 원하는 타입으로 바인딩

## index.jsp
```jsp
<script type="text/javascript">
	$(document).ready(function(){
		customerList(); // 서버로부터 모든 고객 정보 받아오는 함수
		
		customerSelect(); // 한사람 선택시 정보 받아올 이벤트 등록
		customerDelete(); // 삭제버튼 클릭시 이벤트 등록
		customerUpdate(); // 수정버튼 클릭시 이벤트 등록
		customerInsert(); // 추가버튼 클릭시 이벤트 등록
		customerSearch();
		customerAll();
		init(); // 입력칸 지우는 이벤트 등록
	});
	
	function customerList(){
		// 서버로 ajax 요청 보내서 데이터 받아오기
		$.ajax({
			url: 'http://localhost:8080/rest/customers',
			type: 'get',
			dataType: 'json', // 서버가 보내주는 데이터 타입
			success: function(result){
				customerListResult(result);
			},
			error: function(xhr, status, msg){
				alert("상태값: " + status + " 에러메시지: " + msg);
			}
		});
	}
	
	function customerSelect(){
		// tr 클릭시 선택정보가 위쪽의 text칸에 들어가도록 처리
		$('body').on('click', 'tr', function(){
			var num = $(this).find('#hidden_num').val();
			$.ajax({
				url: 'customers/' + num,
				type: 'get',
				dataType: 'json',
				success: customerSelectResult
			})
		})
	}
	function customerDelete(){
		$('#btnDelete').on('click', function(){
			var num = $('#num').val();
			if (num != "") {
				$.ajax({
					url: 'customers/' + num,
					type: 'delete',
					dataType: 'json',
					success: function() {
						clear();
						customerList(); // 화면 리프레쉬
					}
				})
			} else {
				alert("삭제할 정보를 입력해 주세요!");
			}
		})
	}
	
	function customerUpdate(){
		$('#btnUpdate').on('click', function() {
			var num = $('#num').val();
			var address = $('#address').val();
	
			if (num != "" && address != "") {
				$.ajax({
					url: 'customers',
					type: 'put',
					data: JSON.stringify({ // json 객체를 문자열 형식으로
						num: num,
						address: address
					}),
					contentType: 'application/json', // 서버로 보내는 데이터 형식
					success: function() {
						clear();
						customerList();
					}
				})
			} else {
				alert("수정할 정보를 입력해 주세요!");
			}
		})
	}
	
	function customerInsert(){
		$('#btnInsert').on('click', function() {
			// 1. 입력값 알아오기
			var num = $('#num').val();
			var name = $('#name').val();
			var address = $('#address').val();
			
			if (num != "" && name != "" && address !="") {
			// 2. ajax 요청
				$.ajax({
					url: 'customers',
					type: 'post',
					data: JSON.stringify({ // json 객체를 문자열 형식으로
						num: num,
						name: name,
						address: address
					}),
					contentType: 'application/json', // 서버로 보내는 데이터 형식
					success: function() {
						clear();
						customerList();
					}
				})
			} else {
				alert("추가할 정보를 입력해 주세요!");
			}
		})
	}
	
	function customerSearch(){
		$('#btnSearch').on('click', function() {
			var address = $('#address').val();
			
			if (address != "") {
				$.ajax({
					url: 'customers/find/' + address,
					type: 'get',
					dataType: 'json',
					success: function(result) {
						clear();
						customerListResult(result);
					}
				})
			} else {
				alert("검색할 정보를 입력해 주세요!")
			}
		})
	}
	
	function customerAll(){
		$('#btnAll').on('click', function() {
			$.ajax({
				url: 'http://localhost:8080/rest/customers',
				type: 'get',
				dataType: 'json', // 서버가 보내주는 데이터 타입
				success: function(result){
					customerListResult(result);
				},
				error: function(xhr, status, msg){
					alert("상태값: " + status + " 에러메시지: " + msg);
				}
			});
		})
	}
	
	function init(){
		$('#btnInit').click(function(){
			$('#num').val('');
			$('#name').val('');
			$('#address').val('');
		})
	}
	
	// 서버에서 받은 결과를 테이블에 넣어 보여주는 함수
	function customerListResult(result) {
		$('tbody').empty();
		$.each(result, function(index, item){
			// $('<tr>') : tr 태그를 하나 생성 <tr><td>num</td><td>name</td><td>address</td></tr>
			$('<tr>')
			.append($('<td>').text(item.num))
			.append($('<td>').text(item.name))
			.append($('<td>').text(item.address))
			.append($('<input type="hidden" id="hidden_num">').val(item.num))
			.appendTo('tbody');
		})
		
	}
	
	function customerSelectResult(result) {
		$('#num').val(result.num);
		$('#name').val(result.name);
		$('#address').val(result.address);
	}
	
	function clear(){
		$('#num').val('');
		$('#name').val('');
		$('#address').val('');
	
	}
</script>
```

## RestController
```java
//@RestController: @Controller + @ResponseBody (java->json 으로 변환해줌)
//@CrossOrigin: 다른 origin 에서 요청이 와도 서비스 해주겠다
//@CrossOrigin(origins = {"http://127.0.0.1:8080", "http://127.0.0.1:9090"})
@CrossOrigin("*")
@RestController
public class CustomerRestController {
	
	@Autowired
	CustomerService service;
	
	//Get:http://localhost:8080/rest/customers,  모든데이터
	@GetMapping(value="/customers")
	public List<Customer> selectAll(){		
		return service.selectAll();
	}
	
	
	//get:http://localhost:8080/rest/customers/1 ,  한개 데이터	
	@GetMapping(value="/customers/{num}")
	public Customer selectOne(@PathVariable String num) {
		return service.selectOne(num);
	}
	
	
	//post:http://localhost:8080/rest/customers ,  데이터 추가	
	@PostMapping(value="/customers")
	public Map<String, String> insert(@RequestBody Customer c) { //@RequestBody: json -> java
		service.insert(c);
		
		Map<String, String> map = new HashMap<>();
		map.put("result", "추가 성공!");
		return map;
	}
	
	//put:http://localhost:8080/rest/customers ,  데이터 수정	
	@PutMapping(value="/customers")
	public Map<String, String> update(@RequestBody Customer c) { //@RequestBody: json -> java
		service.update(c);
		
		Map<String, String> map = new HashMap<>();
		map.put("result", "수정 성공!");
		return map;
	}
	
	//delete:http://localhost:8080/rest/customers/1 ,  한개 데이터 삭제
	@DeleteMapping(value="customers/{num}")
	public Map<String, String> delete(@PathVariable String num) {
		service.delete(num);
		
		Map<String, String> map = new HashMap<>();
		map.put("result", "삭제 성공!");
		return map;
	}
	
	//get:http://localhost:8080/rest/customers/find/la, 검색
	@GetMapping(value="/customers/find/{address}")
	public List<Customer> search(@PathVariable String address) {
		return service.findByAddress(address);
	}
	

}
```
