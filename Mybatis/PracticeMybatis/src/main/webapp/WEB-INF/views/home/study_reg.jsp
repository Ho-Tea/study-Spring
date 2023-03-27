<%@ page import="java.util.Map" %>
<%@ page import="java.util.List" %>
<%@ page import="com.example.PracticeMybatis.vo.StudyVo" %><%--
  Created by IntelliJ IDEA.
  User: yoonjuho
  Date: 2023/03/24
  Time: 5:22 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<%
    List<StudyVo> list = (List) request.getAttribute("list");
%>

<html>
<head>
    <!-- CSS only -->
    <link href="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/css/bootstrap.min.css" rel="stylesheet" integrity="sha384-rbsA2VBKQhggwzxH7pPCaAqO46MgnOM80zW1RWuH61DGLwZJEdK2Kadq2F9CUG65" crossorigin="anonymous">

    <title>Title</title>
</head>
<body>
<%--헤더 위치--%>
<%@include file="../comm/header.jsp"%>

<main>
    <div class="container">
        <button type="button" onclick="location.href='/study_reg/insert'">등록하기</button>
        <div class="row mb-2">
        <div class="col">key_id</div>
        <div class="col">공부 일자</div>
        <div class="col">공부 내용</div>
            <div class="col">등록 일자</div>
            <div class="col">수정</div>
            <div class="col">삭제</div>
        </div>

    <% for(StudyVo map : list){ %>
    <div class="row mb-2">
        <div class="col"><%= map.getId()%></div>
        <div class="col"><%= map.getStudyDay()%></div>
        <div class="col"><%= map.getContents()%></div>
        <div class="col"><%= map.getRegDay()%></div>
        <div class="col"><a href="/study_reg/modify?id=<%= map.getId()%>"> 수정 </a></div>
        <div class="col"><a href="/study_reg/delete?id=<%= map.getId()%>"> 삭제 </a></div>
    </div>
    <% } %>
</div>
</main>
HII

<%@include file="../comm/footer.jsp"%>
<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>

</html>
