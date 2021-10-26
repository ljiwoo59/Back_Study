# MVC Board
* 로그인, 로그아웃
* 게시글 작성, 선택, 삭제
* 제목, 작성자 검색

## FrontController
* 화면 당 url 지정

```java
// FrontController : 클라이언트로부터 들어오는 요청 받기
// 받은 요청을 구분해서 Controller 에게 작업을 넘김
@WebServlet("*.bod")
public class BoardFrontController extends HttpServlet {
	BoardController con;// 동기 요청 처리 컨트롤러
	BoardAjaxController acon;// 비동기 요청 처리 컨트롤러
	
	public BoardFrontController() { // by tomcat
		con = new BoardController();
		acon = new BoardAjaxController();
	}
	
	// http://localhost:8080/board/list.bod
	private void process(HttpServletRequest request, HttpServletResponse response) {
		String reqString = request.getServletPath();
		System.out.println(reqString);
		
		// jsp 로 포워드하여 화면이 바뀜
		if (reqString.equals("/list.bod")) { // 초기화면 요청
			con.list(request, response);
		} else if (reqString.equals("/read.bod")) { // 읽기화면 요청
			con.read(request, response);
		} else if (reqString.equals("/insertForm.bod")) { // 입력화면 요청
			con.insertForm(request, response);
		} else if (reqString.equals("/insertProcess.bod")) { // db 에 입력 요청
			con.insertProcess(request, response);
		} else if (reqString.equals("/delete.bod")) { // db 에 삭제 요청
			con.delete(request, response);
		} else if (reqString.equals("/loginForm.bod")) { // 로그인 요청
			con.loginForm(request, response);
		} else if (reqString.equals("/loginProcess.bod")) { // 로그인 처리 요청
			con.loginProcess(request, response);
		} else if (reqString.equals("/logout.bod")) { // 로그아웃 요청
			con.logout(request, response);
		} else if (reqString.equals("/search.bod")) { // 검색 요청
			con.search(request, response);
		} else if (reqString.equals("/modify.bod")) { // 수정 요청
			con.modify(request, response);
		}
		
	}
	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		process(request, response);
	}
}
```

## BoardController (동기 요청)
* FC 로부터 요청을 받아 Service 에게 작업 처리 후 결과 jsp 페이지 출력

```java
// FrontController 에게서 요청을 전달 받아 Service 에게 작업을 넘김
public class BoardController {
	BoardService service; // interface 타입으로 선언
	
	public BoardController() {
		service = new BoardServiceImpl();
	}

	public void list(HttpServletRequest request, HttpServletResponse response) { // 목록
		ArrayList<Board> list = service.selectAll();
		request.setAttribute("list", list);
		
		// jsp 로 넘어가기 (forward 방식)
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/list.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void read(HttpServletRequest request, HttpServletResponse response) { // 선택
		String num = request.getParameter("num");
		Board b = service.selectOne(num);
		request.setAttribute("b", b);
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/read.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	// 새글 입력을 위한 화면만 보내주면 된다
	public void insertForm(HttpServletRequest request, HttpServletResponse response) { // 작성
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/insertForm.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void insertProcess(HttpServletRequest request, HttpServletResponse response) { // 작성
		// 0. 한글 처리
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		// 1. 사용자가 입력한 값 받아오기
		String title = request.getParameter("title");
		String name = request.getParameter("name");
		String pass = request.getParameter("pass");
		String content = request.getParameter("content");
		
		// 2. service 에게 넘겨주기
		service.insert(new Board(pass, name, title, content));
		
		// 3. 초기화면으로 바로 넘어가기
		try {
			response.sendRedirect("list.bod");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void delete(HttpServletRequest request, HttpServletResponse response) { // 삭제
		String num = request.getParameter("num");
		service.delete(num);
		try {
			response.sendRedirect("list.bod");
		} catch (IOException e) {
			e.printStackTrace();
		}
		
	}

	public void loginForm(HttpServletRequest request, HttpServletResponse response) { // 로그인
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/loginForm.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void loginProcess(HttpServletRequest request, HttpServletResponse response) { // 로그인
		// 1. 사용자가 입력한 값 받기 -> db
		String id = request.getParameter("id");
		// 2. 세션에 ID 저장
		HttpSession session = request.getSession();
		session.setAttribute("id", id);
		// 3. 초기 화면으로 redirect
		RequestDispatcher dispatcher = request.getRequestDispatcher("list.bod");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void logout(HttpServletRequest request, HttpServletResponse response) { // 로그아웃
		HttpSession session = request.getSession();
		session.setAttribute("id", null);
		RequestDispatcher dispatcher = request.getRequestDispatcher("list.bod");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void search(HttpServletRequest request, HttpServletResponse response) { // 검색
		try {
			request.setCharacterEncoding("utf-8");
		} catch (UnsupportedEncodingException e1) {
			e1.printStackTrace();
		}
		
		String condition = request.getParameter("condition");
		String word = request.getParameter("word");
	
		ArrayList<Board> searched = service.search(condition, word);
		request.setAttribute("searched", searched);
		
		RequestDispatcher dispatcher = request.getRequestDispatcher("/views/search.jsp");
		try {
			dispatcher.forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
```

## BoardServiceImpl
* Controller 에서 작업을 받아 Dao 에게 처리 후 반환

```java
// Controller 에게서 작업을 전달 받아 Dao 에게 작업을 넘김
// transaction 관리
public class BoardServiceImpl implements BoardService {

	BoardDao dao; // 인터페이스 타입
	public BoardServiceImpl() {
		dao = new BoardDaoImpl();
	}
	
	@Override
	public ArrayList<Board> selectAll() {
		return dao.selectAll();
	}

	@Override
	public Board selectOne(String num) {
		dao.countUp(num);
		return dao.selectOne(num);
	}

	@Override
	public void insert(Board b) {
		dao.insert(b);
	}

	@Override
	public void delete(String num) {
		dao.delete(num);
	}

	@Override
	public ArrayList<Board> search(String condition, String word) {
		return dao.search(condition, word);
	}

}
```

## BoardDaoImpl
* DB 와 직접적 상호 작용 후 결과 반환

```java
// dao 객체 : CRUD
// Service 에서 작업을 전달 받아 DB 작업 수행
public class BoardDaoImpl implements BoardDao {
	// Connection Pool 을 찾아서 빌리고 반납하고
	DataSource ds;
	
	public BoardDaoImpl() {
		// tomcat 이 만들어 놓은 pool 을 찾아다 놓기
		// JNDI (Java Naming & Directory Interface)
		// 공유 자원에 대해 이름을 붙여 놓고 필요할 때 그 이름으로 자원을 찾아 이용하는 기술
		try {
			Context ctx = new InitialContext();
			ds = (DataSource)ctx.lookup("java:comp/env/jdbc/mysql");
		} catch (NamingException e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public ArrayList<Board> selectAll() { // 목록
		ArrayList<Board> list = new ArrayList<>();
		
		try {
			String query = "select num, name, wdate, title, count from board order by num desc";
			
			// 2.
			Connection con = ds.getConnection();
			// 3.
	        Statement stat = con.createStatement();
	        ResultSet rs = stat.executeQuery(query);
	        
	        while (rs.next()) {
	        	String num = rs.getString(1);
	        	String name = rs.getString(2);
	        	String wdate = rs.getString(3);
	        	String title = rs.getString(4);
	        	String count = rs.getString(5);
	        	
	        	Board b = new Board(num, null, name, wdate, title, null, count);
	        	list.add(b);
	        }
	        con.close(); // pool 에 반납
		} catch(Exception e) {
			e.printStackTrace();
		}
		
		return list;
	}

	@Override
	public Board selectOne(String num) { // 선택
		Board b = null;
		try {
			String query = "select name, wdate, title, count, content from board where num = ?";
			
			// 2.
			Connection con = ds.getConnection();
			// 3.
	        PreparedStatement stat = con.prepareStatement(query);
	        stat.setString(1, num);
	        
	        ResultSet rs = stat.executeQuery();
	        rs.next();
        	String name = rs.getString(1);
        	String wdate = rs.getString(2);
        	String title = rs.getString(3);
        	String count = rs.getString(4);
        	String content = rs.getString(5);
        	
        	b = new Board(num, null, name, wdate, title, content, count);

        	con.close(); // pool 에 반납
		} catch(Exception e) {
			e.printStackTrace();
		}
		return b;
	}

	@Override
	public void countUp(String num) { // 게시글 번호 업데이트
		try {
			String query = "update board set count = count + 1 where num = ?";
			
			// 2.
			Connection con = ds.getConnection();
			// 3.
	        PreparedStatement stat = con.prepareStatement(query);
	        stat.setString(1, num);
	        stat.executeUpdate();
	        con.close(); // pool 에 반납
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void insert(Board b) { // 작성
		try {
			String query = "insert into board(pass, name, wdate, title, content, count)";
			query += "values(?, ?, sysdate(), ?, ?, 0)";
			
			// 2.
			Connection con = ds.getConnection();
			// 3.
	        PreparedStatement stat = con.prepareStatement(query);
	        stat.setString(1, b.getPass());
	        stat.setString(2, b.getName());
	        stat.setString(3, b.getTitle());
	        stat.setString(4, b.getContent());
	        stat.executeUpdate();
	        con.close(); // pool 에 반납
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void delete(String num) { // 삭제
		try {
			String query = "delete from board where num = ?";
			
			// 2.
			Connection con = ds.getConnection();
			// 3.
	        PreparedStatement stat = con.prepareStatement(query);
	        stat.setString(1, num);
	        stat.executeUpdate();
	        con.close(); // pool 에 반납
		} catch(Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public ArrayList<Board> search(String condition, String word) { // 검색
		ArrayList<Board> searched = new ArrayList<>();
		try {
			String query = "";
			if (condition.equals("title"))
				query = "select num, name, wdate, title, count from board where title like ?";
			
			else
				query = "select num, name, wdate, title, count from board where name like ?";
			
			// 2.
			Connection con = ds.getConnection();
			// 3.
			PreparedStatement stat = con.prepareStatement(query);
	        word = "%" + word + "%";
	        stat.setString(1, word);
	        ResultSet rs = stat.executeQuery();
	        
	        while (rs.next()) {
	        	String num = rs.getString(1);
	        	String name = rs.getString(2);
	        	String wdate = rs.getString(3);
	        	String title = rs.getString(4);
	        	String count = rs.getString(5);
	        	
	        	Board b = new Board(num, null, name, wdate, title, null, count);
	        	searched.add(b);
	        }
	        con.close(); // pool 에 반납
		} catch(Exception e) {
			e.printStackTrace();
		}
		return searched;
	}

}
```

## BoardDto
* 데이터 객체

```java
// dto (data transfer object)
// vo (value object)
// 테이블 안의 레코드 한건의 정보를 담기 위한 용도.
// 테이블 구조와 동일하게 필드 지정.

public class Board {
	private String num;
	private String pass;
	private String name;
	private String wdate;
	private String title;
	private String content;
	private String count;
	
	public Board() {}

	public Board(String pass, String name, String title, String content) {
		this.pass = pass;
		this.name = name;
		this.title = title;
		this.content = content;
	}

	public Board(String num, String pass, String name, String wdate, String title, String content, String count) {
		this.num = num;
		this.pass = pass;
		this.name = name;
		this.wdate = wdate;
		this.title = title;
		this.content = content;
		this.count = count;
	}

	public String getNum() {
		return num;
	}

	public void setNum(String num) {
		this.num = num;
	}

	public String getPass() {
		return pass;
	}

	public void setPass(String pass) {
		this.pass = pass;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getWdate() {
		return wdate;
	}

	public void setWdate(String wdate) {
		this.wdate = wdate;
	}

	public String getTitle() {
		return title;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCount() {
		return count;
	}

	public void setCount(String count) {
		this.count = count;
	}
	
}
```
