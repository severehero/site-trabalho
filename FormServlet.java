import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/submit-form")
public class FormServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String nome = request.getParameter("nome");
        String dataNasc = request.getParameter("data-nasc");
        String estadoCivil = request.getParameter("estado-civil");
        String endereco = request.getParameter("endereco");
        String convenio = request.getParameter("convenio");
        int idade = Integer.parseInt(request.getParameter("idade"));
        String genero = request.getParameter("genero");
        String alergia = request.getParameter("alergia");
        boolean sensibilidadeMed = request.getParameter("sensibilidade-med") != null;
        boolean pressaoSanguinia = request.getParameter("pressao-sanguinia") != null;
        boolean tomandoMed = request.getParameter("tomando-med") != null;
        String problemasSaude = request.getParameter("problemas-saude");
        String observacoes = request.getParameter("observacoes");
        String historico = request.getParameter("historico");

        Connection connection = null;
        PreparedStatement preparedStatement = null;

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/interProject", "seu_usuario", "sua_senha");

            String sql = "INSERT INTO ficha_dentaria (nome, data_nasc, estado_civil, endereco, convenio, idade, genero, alergia, sensibilidade_med, pressao_sanguinia, tomando_med, problemas_saude, observacoes, historico) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            preparedStatement = connection.prepareStatement(sql);
            preparedStatement.setString(1, nome);
            preparedStatement.setString(2, dataNasc);
            preparedStatement.setString(3, estadoCivil);
            preparedStatement.setString(4, endereco);
            preparedStatement.setString(5, convenio);
            preparedStatement.setInt(6, idade);
            preparedStatement.setString(7, genero);
            preparedStatement.setString(8, alergia);
            preparedStatement.setBoolean(9, sensibilidadeMed);
            preparedStatement.setBoolean(10, pressaoSanguinia);
            preparedStatement.setBoolean(11, tomandoMed);
            preparedStatement.setString(12, problemasSaude);
            preparedStatement.setString(13, observacoes);
            preparedStatement.setString(14, historico);

            int result = preparedStatement.executeUpdate();

            if (result > 0) {
                response.getWriter().println("Dados salvos com sucesso!");
            } else {
                response.getWriter().println("Falha ao salvar os dados.");
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            if (preparedStatement != null) {
                try {
                    preparedStatement.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            if (connection != null) {
                try {
                    connection.close();
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
