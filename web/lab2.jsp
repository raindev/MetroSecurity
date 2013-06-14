<%@ page import="ua.kpi.security.IncasationEvent" %>
<%@ page import="java.util.List" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%--
  List of incasation by station and period of time.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Lab2</title>
</head>
<body>
  <form>
      <label>
          Metro station:
          <select name="station">
              <option>Дніпро</option>
              <option>Лісова (захід)</option>
              <option>Лісова (схід)</option>
              <option>Хрещатик І (вул. Хрещатик)</option>
          </select>
      </label>
      <label>
          Station hall
          <input type="text" name="hall">
      </label>
      <br>
      <i>
          Date Format: YYYY.MM.DD.HH.MM
      </i>
      <br>
      <label>
          From:
          <input type="text" name="from">
      </label>
      <label>
          To:
          <input type="text" name="to">
      </label>
      <input type="submit">
  </form>
  <%
      @SuppressWarnings("unchecked")
      List<IncasationEvent> events = (List<IncasationEvent>) request.getAttribute("events");
      if (events != null) {
          %><table style="border: 1px solid black;"><%
          int sum = 0;
          for (IncasationEvent event : events) {
              sum += event.getValue();
              %><tr><td style="border: 1px solid black;"><%= new SimpleDateFormat("yyyyMMDDHHmmss").parse(event.getDate()) %></td><td style="border: 1px solid black;"><%= event.getCashier() %></td><td style="border: 1px solid black;"><%= event.getEventType() %></td><td style="border: 1px solid black;"><%=event.getValue()%></td></tr><%
          }
          %></table><%
          %><h4>Сумарний баланс: <%= sum %> ГРН</h4><%
      } else {
              %>EMPTY<%
          }
  %>
</body>
</html>