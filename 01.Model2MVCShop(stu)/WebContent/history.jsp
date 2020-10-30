<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page import = "java.net.URLEncoder" %>

<html>
<head>

<title>열어본 상품 보기</title>

</head>
<body>
	당신이 열어본 상품을 알고 있다
<br>
<br>
<%
	request.setCharacterEncoding("euc-kr");
	response.setCharacterEncoding("euc-kr");
	
	String history = null;
	
	// Client로 부터 전송된 Cookie 처리
	Cookie[] cookies = request.getCookies();
	
	// Cookie의 존재 유무 및 name = value 처리
	if (cookies != null && cookies.length > 0) {
		// Array로 return : Array 갯수만큼 처리
		for (int i = 0; i < cookies.length; i++) {
			
			Cookie cookie = cookies[i]; // 쿠키 하나씩 가져옴
			
			System.out.println("client로 부터 전송된 cookie : " + cookie.getName() + "=" + cookie.getValue());
			
			if (cookie.getName().equals("history")) { // 쿠키 이름이 history면
				history = URLDecoder.decode(cookie.getValue()); // name이 history인 쿠키의 value 가져옴
				System.out.println(history);
			}
		}
		
		if (history != null) {
			String[] h = history.split(",");
			for (int i = 0; i < h.length; i++) {
				if (!h[i].equals("null")) {
%>
<a href="/getProduct.do?prodNo=<%=h[i]%>&menu=search" target="rightFrame"><%=h[i]%></a>
<br>
<%
				}
			}
		}
	}
%>

</body>
</html>