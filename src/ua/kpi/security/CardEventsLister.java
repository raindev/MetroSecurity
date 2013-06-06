package ua.kpi.security;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

/**
 * List card events by station hall name and card number.
 */
@WebServlet(name = "CardEventsListener", urlPatterns = "/lab1")
public class CardEventsLister extends HttpServlet {
    private static final String STATION_PARAM = "hall";
    private static final String CARD_PARAM = "card";
    private static Properties properties;
    private static Properties stationNames;
    private static Properties errorCodes;
    private static Properties eventCodes;

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        stationNames = new Properties();
        errorCodes = new Properties();
        eventCodes = new Properties();
        properties = new Properties();
        try {
            stationNames.load(new InputStreamReader(CardEventsLister.class.getClassLoader().getResourceAsStream("/stationNames.properties")));
            errorCodes.load(new InputStreamReader(CardEventsLister.class.getClassLoader().getResourceAsStream("/errors.properties")));
            eventCodes.load(new InputStreamReader(CardEventsLister.class.getClassLoader().getResourceAsStream("/events.properties")));
            properties.load(new InputStreamReader(CardEventsLister.class.getClassLoader().getResourceAsStream("/events.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String station = request.getParameter(STATION_PARAM);
        String card = request.getParameter(CARD_PARAM);
        query:
        {
            if (station != null && card != null) {
                try {
                    if (stationNames.get(station) == null) {
                        log(station);
                        log(stationNames.propertyNames().nextElement().toString());
                        log(stationNames.get(stationNames.propertyNames().nextElement()).toString());
                        request.setAttribute("error", true);
                        break query;
                    }
                    Connection connection = DriverManager.getConnection("jdbc:sqlite:" + properties.get("db_path") + "adbk." + stationNames.get(station) + ".11.db3");
                    PreparedStatement statement = connection.prepareStatement("SELECT DateTime date, ErrorCode error, EventCode event FROM events WHERE cardNo = ?");
                    statement.setString(1, card);
                    ResultSet result = statement.executeQuery();
                    Map<Date, String> events = new HashMap<>();
                    while (result.next()) {
                        String error = result.getString("error");
                        String message = error.equals("0") ?
                                eventCodes.getProperty(result.getString("event")) :
                                errorCodes.getProperty(error);
                        events.put(new SimpleDateFormat("YYYYMMDDHHMMSS").parse(result.getString("date")),
                                message != null ? message : "Невідома операція");
                    }
                    request.setAttribute("events", events);
                    request.setAttribute("fillList", true);
                } catch (SQLException e) {
                    throw new ServletException(e);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            } else {
                request.setAttribute("fillList", false);
            }
        }
        request.getRequestDispatcher("lab1.jsp").forward(request, response);
    }
}
