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
    StudyVo studyVo = (StudyVo) request.getAttribute("vo_study");
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
    <form name = "frm_study_mod" action="/study_reg/ins_exe" method="post">
        <div>keyID : <input type="text" name="auto" value="자동입력" readonly></div><br>
        <div>StudyDay :  <input type="text" name="studyDay" value=" "></div><br>
        <div>Contents : <input type="text" name="contents" value=" "></div>

        <input type="submit" value="추가하기">
    </form>
</main>

<%@include file="../comm/footer.jsp"%>
<!-- JavaScript Bundle with Popper -->
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.2.3/dist/js/bootstrap.bundle.min.js" integrity="sha384-kenU1KFdBIe4zVF0s0G1M5b4hcpxyD9F7jL+jjXkk+Q2h455rYXK/7HAuoJl+0I4" crossorigin="anonymous"></script>
</body>

</html>
