<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
<form action="loginProcess.bod" id="f" method="post">
		<fieldset>
			<legend>로그인 정보</legend>
			<ul>
				<li>
					아이디:<input type="text" id="id" name="id">
				</li>
				
				<li>
					비밀번호:<input type="password" id="pass" name="pass">
					<span id="msg"></span>
				</li>
			</ul>
		</fieldset>		
		
		<input type="submit" value="send">
		<input type="reset" value="clear">
	</form>

</body>
</html>