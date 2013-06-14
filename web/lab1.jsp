<%@ page import="java.util.Date" %>
<%@ page import="java.util.Map" %>
<%--
  List of events by station hall and card number.
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
        Виберіть станцію
        <select name="hall">
            <option>Дніпро</option>
            <option>Лісова (захід)</option>
            <option>Лісова (схід)</option>
            <option>Хрещатик І (вул. Хрещатик)</option>
        </select>
    </label>
    <label>
        Номер картки
        <input type="text" name="card">
    </label>
    <input type="submit">
</form>
<%
    //TODO фильтр по событиям
    if (request.getAttribute("error") != null) {
%><h2>Cannot find station</h2><%
} else {
    if ((Boolean) request.getAttribute("fillList")) {
        //TODO выводить данных побольше
%><h4>File updated: <%= request.getAttribute("fileUpdate") %></h4>
<table style="margin-left: 25px; margin-top: 10px; border: 1px solid black;">
    <% @SuppressWarnings("unchecked")
    Map<Date, String> events = (Map
            <Date, String>) request.getAttribute("events");
        for (Map.Entry<Date, String> event : events.entrySet()) { %>
    <tr>
        <td style="border: 1px solid black;"><%= event.getKey()%>
        </td>
        <td style="border: 1px solid black;"><%= event.getValue()%>
        </td>
    </tr>
    <% }%>
</table>
<%
        }
    }
%>
</body>
</html>