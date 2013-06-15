<%@ page import="ua.kpi.security.Event" %>
<%@ page import="java.util.List" %>
<%--
  List of events by station hall and card number.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!doctype html>
<html>
<head>
    <meta charset="utf-8">
    <title>Lab1</title>
    <link href="css/bootstrap.min.css" rel="stylesheet" media="screen">
    <link href="css/metro.css" rel="stylesheet">
</head>
<body>
<form action="lab1" class="form-horizontal">
    <fieldset>
        <legend>Пошук подій</legend>
        <div class="control-group">
            <label class="control-label" for="hall">
                Станція
            </label>
            <div class="controls">
                <select id="hall" name="hall">
                    <option>Дніпро</option>
                    <option>Лісова (захід)</option>
                    <option>Лісова (схід)</option>
                    <option>Хрещатик І (вул. Хрещатик)</option>
                </select>
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="card">
                Номер картки
            </label>
            <div class="controls">
                <input id="card" type="text" name="card" placeholder="Номер картки...">
            </div>
        </div>
        <div class="control-group">
            <label class="control-label" for="event">
                Подія
            </label>
            <div class="controls">
                <select id="event" name="event">
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
            </div>
        </div>
        <div class="controls">
            <input type="submit" class="btn" value="Шукати">
        </div>
    </fieldset>
</form>
<%
    if (request.getAttribute("error") != null) {
%><p><small>Cannot find station</small></p><%
} else {
    if ((Boolean) request.getAttribute("fillList")) {
%><p id="fileUpdate" class="text-info">Оновлення бази даних: <%= request.getAttribute("fileUpdate") %>
</p>
<% @SuppressWarnings("unchecked")
List<Event> events = (List<Event>) request.getAttribute("events");
    if (!events.isEmpty()) { %>
<table class="table">
    <thead>
        <tr>
            <th>Час</th>
            <th>Подія</th>
            <th>PIX</th>
            <th>AID</th>
            <th>Контракт</th>
        </tr>
    </thead>
    <% for (Event event : events) { %>
    <tr>
        <td><%= event.getDate()%>
        </td>
        <td><%= event.getEvent()%>
        </td>
        <td><%= event.getAid()%>
        </td>
        <td><%= event.getPix()%>
        </td>
        <td><%= event.getContract()%>
        </td>
    </tr>
    <% }
    }%>
</table>
<%
        }
    }
%>
</body>
</html>