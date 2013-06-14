<%@ page import="ua.kpi.security.CardEventsLister" %>
<%@ page import="java.io.File" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<ol>
    <% for (String fileName : new File(CardEventsLister.properties.getProperty("db_path")).list()) { %>
    <% if (fileName.matches(".+\\.db3$") || fileName.matches(".+\\.sqlite")) {%>
    <li><%= fileName %>
    </li>
    <%
            }
        }
    %>
</ol>
</body>
</html>