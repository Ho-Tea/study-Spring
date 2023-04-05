<%--
  Created by IntelliJ IDEA.
  User: yoonjuho
  Date: 2023/04/05
  Time: 3:10 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page import="hello.servlet.domain.member.MemberRepository" %>
<%@ page import="hello.servlet.domain.member.Member" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
        //request, response 사용 가능
        MemberRepository memberRepository = MemberRepository.getInstance();
        System.out.println("save.jsp");
        String username = request.getParameter("username");
        int age = Integer.parseInt(request.getParameter("age"));
        Member member = new Member(username, age);
        System.out.println("member = " + member);
        memberRepository.save(member);
%>
<html>
<head>
    <title>Title</title>
</head>
<body>
성공
<ul>
    <li>id=<%=member.getId()%></li>
    <li>username=<%=member.getName()%></li>
    <li>age=<%=member.getAge()%></li>
</ul>
<a href="/index.html">메인</a>

</body>
</html>
