<%@ page import="java.util.Enumeration" %>
<%--
  Created by IntelliJ IDEA.
  User: lifubo
  Date: 2017/2/3
  Time: 16:43
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
111222
${username}


<%

  Enumeration em = request.getParameterNames();
  while (em.hasMoreElements()) {
    String name = (String) em.nextElement();
    String value = request.getParameter(name);
    System.out.println(name+":"+value);
  }

  System.out.println("===========================================================");

  java.util.Enumeration   e   =   request.getAttributeNames();
  while( e.hasMoreElements())   {
    String sessionName=(String)e.nextElement();
    System.out.println("request "+sessionName+"||||"+request.getAttribute(sessionName));
  }

  System.out.println("===========================================================");

  e   =   request.getSession().getAttributeNames();
  while( e.hasMoreElements())   {
    String sessionName=(String)e.nextElement();
    System.out.println("Session "+sessionName+"===="+request.getSession().getAttribute(sessionName));
  }

%>
</body>
</html>
