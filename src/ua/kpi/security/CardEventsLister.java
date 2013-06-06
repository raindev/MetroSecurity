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

/**
 * List card events by station hall name and card number.
 */
@WebServlet(name = "CardEventsListener", urlPatterns="/lab1")
public class CardEventsLister extends HttpServlet {
    private static final String DB_PATH = "/home/rain/Desktop/";
    private static final String HALL_PARAM = "hall";
    private static final String CARD_PARAM = "card";

    static {
        try {
            Class.forName("org.sqlite.JDBC");
        } catch (ClassNotFoundException ignored) {

        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String hall = request.getParameter(HALL_PARAM);
        String card = request.getParameter(CARD_PARAM);
//        if (hall != null && card != null) {
//            if (hall.equals("Чернігівська (схід)")) {
                try {
                    Connection connection = DriverManager.getConnection("jdbc:sqlite:" + DB_PATH + "adbk.n0265.11.db3");
                    PreparedStatement statement = connection.prepareStatement("SELECT  * FROM events WHERE cardNo = ? LIMIT 100");
                    statement.setString(1, card);
                    ResultSet result = statement.executeQuery();
                    List<String> cardNumbers = new ArrayList<>();
                    while (result.next()) {
                        log(result.getString("ErrorCode"));
                        cardNumbers.add(result.getString("ErrorCode"));
                    }
                    System.out.println("Connected");
                    request.getRequestDispatcher("lab1.jsp").forward(request, response);
                } catch (SQLException e) {
                    throw new ServletException(e);
                }
//            }
//        }
    }
}
