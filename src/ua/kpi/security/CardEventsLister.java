package ua.kpi.security;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.Date;

/**
 * List card events by station hall name and card number.
 */
@WebServlet(name = "CardEventsListener", urlPatterns = "/lab1")
public class CardEventsLister extends HttpServlet {
    private static final String STATION_PARAM = "hall";
    private static final String CARD_PARAM = "card";
    public static final String EVENT_PARAM = "event";
    public static Properties properties;
    static Properties stationNames;
    private static Properties errorCodes;
    static Properties eventCodes;

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
            properties.load(new InputStreamReader(CardEventsLister.class.getClassLoader().getResourceAsStream("/metro.properties")));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private Object dbPath;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("utf-8");
        String station = request.getParameter(STATION_PARAM);
        String card = request.getParameter(CARD_PARAM);
        String event = request.getParameter(EVENT_PARAM);
        query:
        {
            if (station != null && card != null) {
                if (stationNames.get(station) == null) {
                    log(station);
                    log(stationNames.propertyNames().nextElement().toString());
                    log(stationNames.get(stationNames.propertyNames().nextElement()).toString());
                    request.setAttribute("error", true);
                    break query;
                }
                dbPath = properties.get("db_path");
                String dbFile = dbPath + "adbk." + stationNames.get(station) + ".11.db3";
                request.setAttribute("fileUpdate", new Date(new File(dbFile).lastModified()));
                List<Event> events = null;
                Connection connection = null;
                try {
                    connection = DriverManager.getConnection("jdbc:sqlite:" + dbFile);
                    PreparedStatement statement = connection.prepareStatement(
                            "SELECT " +
                                    "DateTime date, ErrorCode error, contractID aid, " +
                                    " ContractCopyID pix, ContractValue contract " +
                                    "FROM events WHERE cardNo = ? AND eventCode = ?");
                    statement.setString(1, card);
                    statement.setString(2, event);
                    log(event + " <<<");
                    ResultSet result = statement.executeQuery();
                    events = new ArrayList<>();
                    while (result.next()) {
                        String error = result.getString("error");
                        String message = error.equals("0") ?
                                eventCodes.getProperty(event) :
                                errorCodes.getProperty(error);
                        events.add(new Event(
                                new SimpleDateFormat("yyyyMMDDHHmmss").parse(result.getString("date")),
                                message != null ? message : "Невідома операція",
                                result.getInt("aid"),
                                result.getInt("pix"),
                                result.getInt("contract")
                        ));
                    }
                } catch (SQLException e) {
                    throw new ServletException(e);
                } catch (ParseException e) {
                    log("Date parsing error", e);
                } finally {
                    try {
                        if (connection != null) {
                            connection.close();
                        }
                    } catch (SQLException e) {
                        log("Cannot close connection", e);
                    }
                }
                request.setAttribute("events", events);
                request.setAttribute("fillList", true);
            } else {
                request.setAttribute("fillList", false);
            }
        }
        request.getRequestDispatcher("lab1.jsp").forward(request, response);
    }
}
