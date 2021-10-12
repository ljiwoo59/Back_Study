<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ include file="/include/header.jsp" %>
<!DOCTYPE html>
<html>

<head>
    <meta charset="UTF-8">
    <title>SSAFY - 글목록</title>
    <meta name="viewport" content="width=device-width, initial-scale=1">
    <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.5.1/jquery.min.js"></script>
    <script src="https://cdnjs.cloudflare.com/ajax/libs/popper.js/1.16.0/umd/popper.min.js"></script>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/js/bootstrap.min.js"></script>
    <script type="text/javascript">
        $(document).ready(function () {
            $("#mvListBtn").click(function () {
                location.href = "<%= root %>/guestbook?act=list";
            });
        });
    </script>
</head>

<body>
    <div class="container text-center mt-3">
        <div class="col-lg-8 mx-auto">
            <div class="jumbotron">
                <h1 class="text-primary">글작성 성공 ^^</h1>
                <p class="mt-4"><button type="button" id="mvListBtn" class="btn btn-outline-dark">글목록 페이지로 이동</button>
                </p>
            </div>
        </div>
    </div>
</body>

</html>