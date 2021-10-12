<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="com.ssafy.guestbook.dto.MemberDto" %>
<%
String root = request.getContextPath();

MemberDto memberDto = (MemberDto) session.getAttribute("userinfo");
%>