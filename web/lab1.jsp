<%@ page import="ua.kpi.security.Event" %>
<%@ page import="java.util.Date" %>
<%@ page import="java.util.List" %>
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
<form action="lab1" autocomplete="on">
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
    <label>
        Тип події
        <select name="event">
            <option value='206'>Запис ресурсу</option>
            <option value='212'>Операція з гаманцем</option>
            <option value='1'>Включення живлення</option>
            <option value='2'>Вхід до технологічного меню</option>
            <option value='3'>Завершення роботи</option>
            <option value='4'>Вхід до меню інкасування</option>
            <option value='5'>Перегляд історії</option>
            <option value='6'>Відкриття пристрою (спрацював датчик )</option>
            <option value='7'>Закриття пристрою (спрацював датчик )</option>
            <option value='8'>Вилучення бункеру</option>
            <option value='9'>Встановлення бункеру</option>
            <option value='15'>Додана купюра</option>
            <option value='17'>Запуск ПЗ</option>
            <option value='18'>Встановлена картка</option>
            <option value='19'>Завершення роботи з карткою</option>
            <option value='28'>Купюру повернуто</option>
            <option value='29'>Прийнято купюру</option>
            <option value='39'>Друк службового чеку</option>
            <option value='42'>Обслуговування пристрою</option>
            <option value='43'>Закінчення обслуговування</option>
            <option value='45'>Перевірка бази даних</option>
        </select>
    </label>
    <input type="submit" value="Шукати" style="height: 30px;background-color: lightblue;">
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
    List<Event> events = (List<Event>) request.getAttribute("events");
        for (Event event : events) { %>
    <tr>
        <td style="border: 1px solid black;"><%= event.getDate()%>
        </td>
        <td style="border: 1px solid black;"><%= event.getEvent()%>
        </td>
        <td style="border: 1px solid black;"><%= event.getAid()%>
        </td>
        <td style="border: 1px solid black;"><%= event.getPix()%>
        </td>
        <td style="border: 1px solid black;"><%= event.getContract()%>
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