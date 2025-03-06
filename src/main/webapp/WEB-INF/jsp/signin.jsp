<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>로그인</title>
</head>
<body>
<h2>회원가입</h2>
<form:form action="/login" method="post" modelAttribute="signinRequest">
    <label>이메일: <form:input path="email" type="email" required="true" /></label><br>
    <label>비밀번호: <form:password path="password" required="true" /></label><br>
    <button type="submit">로그인하기</button>
</form:form>

<c:if test="${not empty errorMessage}">
    <p style="color: red;">${errorMessage}</p>
</c:if>
</body>
</html>



