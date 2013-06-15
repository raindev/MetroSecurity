package ua.kpi.security;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

import static ua.kpi.security.CardEventsLister.properties;
import static ua.kpi.security.CardEventsLister.stationNames;


@WebServlet(name = "Incasations", urlPatterns = "/lab2")
public class Incasations extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String station = request.getParameter("station");
        String hall = request.getParameter("hall");
        String fromDate = request.getParameter("from");
        String toDate = request.getParameter("to");
        if (station != null && fromDate != null && toDate != null) {
            fromDate = fromDate.replaceAll("\\.", "");
            toDate = toDate.replaceAll("\\.", "");
            String dbFile = properties.getProperty("db_path") + "adbk." + stationNames.get(station) + ".1" + hall + ".db3";
            Connection cashierConnection = null;
            Connection connection = null;
            try {
                connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
                PreparedStatement statement = connection.prepareStatement(
                        "SELECT datetime eventTime, cardNo cashier, transactionValue / 100 amount, eventCode FROM events " +
                                "WHERE eventCode = 231 AND eventTime > ? AND eventTime < ?");
                statement.setString(1, fromDate);
                statement.setString(2, toDate);
                ResultSet result = statement.executeQuery();
                List<IncasationEvent> events = new ArrayList<>();
                cashierConnection = DriverManager.getConnection("jdbc:sqlite:" + properties.getProperty("db_path") + "cashiers.sqlite");
                while (result.next()) {
                    String cashierName = result.getString("cashier");
                    log(String.valueOf(result.getInt("cashier")));
                    PreparedStatement queryCashier = cashierConnection.prepareStatement("SELECT sname cashier FROM Cashiers " +
                            "WHERE cardNo = ?");
                    queryCashier.setLong(1, Long.parseLong(cashierName.replace(" ", "").replaceAll("^0+", "")));
                    ResultSet cashiers = queryCashier.executeQuery();
                    if (cashiers.next()) {
                        cashierName = cashiers.getString("cashier") != null ? cashiers.getString("cashier") : result.getString("cashier");
                    }
                    events.add(new IncasationEvent(result.getString("eventTime"), cashierName, result.getInt("amount"))/*CardEventsLister.eventCodes.getProperty(String.valueOf(result.getInt("eventType")))*/);
                }
                request.setAttribute("events", events);
            } catch (SQLException e) {
                log("OOps", e);
            } finally {
                try {
                    if (cashierConnection != null) {
                        cashierConnection.close();
                    }
                } catch (SQLException e) {
                    log("Cannot close connection", e);
                }
                try {
                    if (connection != null) {
                        connection.close();
                    }
                } catch (SQLException e) {
                    log("Cannot close connection", e);
                }
            }
        }
        request.getRequestDispatcher("lab2.jsp").forward(request, response);
    }
}
