<%@ page contentType="text/html; charset=EUC-KR" %>
<%@ page import = "java.net.URLDecoder" %>
<%@ page import = "java.net.URLEncoder" %>

<html>
<head>

<title>��� ��ǰ ����</title>

</head>
<body>
	����� ��� ��ǰ�� �˰� �ִ�
<br>
<br>
<%
	request.setCharacterEncoding("euc-kr");
	response.setCharacterEncoding("euc-kr");
	
	String history = null;
	
	// Client�� ���� ���۵� Cookie ó��
	Cookie[] cookies = request.getCookies();
	
	// Cookie�� ���� ���� �� name = value ó��
	if (cookies != null && cookies.length > 0) {
		// Array�� return : Array ������ŭ ó��
		for (int i = 0; i < cookies.length; i++) {
			
			Cookie cookie = cookies[i]; // ��Ű �ϳ��� ������
			
			System.out.println("client�� ���� ���۵� cookie : " + cookie.getName() + "=" + cookie.getValue());
			
			if (cookie.getName().equals("history")) { // ��Ű �̸��� history��
				history = URLDecoder.decode(cookie.getValue()); // name�� history�� ��Ű�� value ������
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