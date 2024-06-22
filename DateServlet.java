import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/submit-date")
public class DateServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String selectedDate = request.getParameter("selected-date");

        // Dados de conexão ao banco de dados
        String jdbcURL = "jdbc:mysql://localhost:3306/interProject";
        String jdbcUsername = "usuario";
        String jdbcPassword = "senha";

        try (Connection connection = DriverManager.getConnection(jdbcURL, jdbcUsername, jdbcPassword)) {
            String sql = "INSERT INTO datas_selecionadas (data) VALUES (?)";
            PreparedStatement statement = connection.prepareStatement(sql);
            statement.setString(1, selectedDate);
            statement.executeUpdate();
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException(e);
        }

        // Redirecionar ou enviar uma resposta após salvar a data
        response.sendRedirect("calendario.html");
    }
}
