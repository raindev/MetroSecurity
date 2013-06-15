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
    <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="css/metro.css" rel="stylesheet">
</head>
<body>
  <form class="form-horizontal">
      <fieldset>
          <legend>Пошук інкасацій</legend>
          <div class="control-group">
              <label for="station" class="control-label">
                  Станція метро
              </label>
              <div class="controls">
                  <select id="station" name="station">
                      <option>Дніпро</option>
                      <option>Лісова (захід)</option>
                      <option>Лісова (схід)</option>
                      <option>Хрещатик І (вул. Хрещатик)</option>
                  </select>
              </div>
          </div>
          <div class="control-group">
              <label for="hall" class="control-label">
                  Вестибюль
              </label>
              <div class="controls">
                  <input id="hall" type="number" min="1" name="hall" placeholder="Номер вестибюлю">
              </div>
          </div>
          <div class="control-group">
              <label for="from" class="control-label">
                  Від
              </label>
              <div class="controls">
                  <input id="from" type="text" name="from" placeholder="YYYY.MM.DD.HH.MM">
              </div>
          </div>
          <div class="control-group">
              <label for="to" class="control-label">
                  До
              </label>
              <div class="controls">
                  <input id="to" type="text" name="to" placeholder="YYYY.MM.DD.HH.MM">
              </div>
          </div>
          <div class="controls">
              <input type="submit" class="btn" value="Шукати">
          </div>
      </fieldset>
  </form>
  <%
      @SuppressWarnings("unchecked")
      List<IncasationEvent> events = (List<IncasationEvent>) request.getAttribute("events");
      if (events != null) {
          %>
  <table class="table">
      <thead>
      <tr>
          <th>Дата</th>
          <th>Касир</th>
          <th>Сума</th>
      </tr>
      </thead>

      <%
          int sum = 0;
          for (IncasationEvent event : events) {
              sum += event.getValue();
              %><tr><td><%= new SimpleDateFormat("yyyyMMDDHHmmss").parse(event.getDate()) %></td><td><%= event.getCashier() %></td><td><%=event.getValue()%></td></tr><%
          }
          %></table><%
          %><h4 id="balance">Сумарний баланс: <%= sum %> ГРН</h4><%
      } %>
</body>
</html>