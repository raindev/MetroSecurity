<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map" %>
<%--
  List of events by station hall and
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Lab1</title>
</head>
<body>
    <form action="lab1">
        <label>
            Hall name
            <input type="text" name="hall">
        </label>
        <label>
            Card number
            <input type="text" name="card">
        </label>
        <input type="submit">
    </form>
    <table style="margin-left: 25px; margin-top: 10px; border: 1px solid black;">
    <%
        if (request.getAttribute("error") != null) {
            %><h2>Cannot find station</h2><%
        }else{
        if ((Boolean) request.getAttribute("fillList")) {
            @SuppressWarnings("unchecked")
            Map<Date, String> events = (Map<Date, String>) request.getAttribute("events");
            for (Map.Entry<Date, String> event : events.entrySet()) { %>
                <tr><td style="border: 1px solid black;"><%= event.getKey()%></td><td style="border: 1px solid black;"><%= event.getValue()%></td></tr>
            <%}
        }
            }
    %>
    </table>
</body>
</html>