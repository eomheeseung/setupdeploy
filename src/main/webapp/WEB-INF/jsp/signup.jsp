<%@ taglib uri="http://www.springframework.org/tags/form" prefix="form" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>회원가입</title>
</head>
<body>
<h2>회원가입</h2>
<form:form action="/signup" method="post" modelAttribute="signupRequest">
    <label>이메일: <form:input path="email" type="email" required="true" /></label><br>
    <label>이름: <form:input path="name" required="true" /></label><br>
    <label>비밀번호: <form:password path="password" required="true" /></label><br>
    <button type="submit">가입하기</button>
</form:form>

<c:if test="${not empty errorMessage}">
    <p style="color: red;">${errorMessage}</p>
</c:if>
</body>
</html>
