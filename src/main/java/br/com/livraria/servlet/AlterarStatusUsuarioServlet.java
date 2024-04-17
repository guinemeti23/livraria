package br.com.livraria.servlet;

import br.com.livraria.dao.UserDAO;
import br.com.livraria.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/AlterarStatusUsuario")
public class AlterarStatusUsuarioServlet extends HttpServlet{
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("listarUsuarios.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int userId = Integer.parseInt(req.getParameter("userId"));
        boolean novoStatus = Boolean.parseBoolean(req.getParameter("status"));


        UserDAO userDAO = new UserDAO();
        boolean sucesso = userDAO.atualizarStatusUsuario(userId, novoStatus);

        if (sucesso) {
            resp.sendRedirect("ListarUsuariosServlet");
        } else {
            resp.getWriter().println("Falha ao atualizar o status do usuário.");
        }
    }
}
