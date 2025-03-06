<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>

<html>
<head>
    <title>홈 화면</title>
    <script>
        // 쿠키에서 JWT를 가져오는 함수
        function getJwtToken() {
            let cookies = document.cookie.split(';');
            for (let i = 0; i < cookies.length; i++) {
                let cookie = cookies[i].trim();
                if (cookie.startsWith("Authorization=")) {
                    return cookie.substring("Authorization=".length, cookie.length);
                }
            }
            return null;
        }

        // JWT를 서버로 보내어 사용자 정보를 요청하는 함수
        function fetchUserInfo() {
            let token = getJwtToken();
            if (!token) {
                alert("로그인이 필요합니다.");
                window.location.href = "/login"; // 로그인 페이지로 이동
                return;
            }

            // JWT를 Authorization 헤더에 포함하여 서버로 요청
            fetch("/api/user", {
                method: "GET",
                headers: {
                    "Authorization": "Bearer " + token
                }
            })
                .then(response => response.json())
                .then(data => {
                    // 사용자 이메일을 화면에 출력
                    document.getElementById("userEmail").innerText = data.email;
                })
                .catch(error => {
                    console.error("Error:", error);
                });
        }

        window.onload = fetchUserInfo; // 페이지 로드 시 사용자 정보 요청
    </script>
</head>
<body>
<h2>환영합니다!</h2>

<!-- 모델에 담긴 'user' 객체에서 이메일을 가져와 출력 -->
<p>사용자 이메일: <span id="userEmail">${user.email}</span></p> <!-- 서버에서 전달한 값 -->

<!-- 로그아웃 버튼 -->
<form action="${pageContext.request.contextPath}/logout" method="post">
    <button type="submit">로그아웃</button>
</form>
</body>
</html>
