package br.com.livraria.servlet;

import br.com.livraria.dao.UserDAO;
import br.com.livraria.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet("/Cadastro")
public class CadastroUsuarioServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("cadastro.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String nome = req.getParameter("nome");
        String cpf = req.getParameter("cpf");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String confirmacaoSenha = req.getParameter("confirmacaoSenha");
        String grupo = req.getParameter("grupo");

        Usuario user = new Usuario(nome, cpf, email, senha, grupo, true);

        UserDAO userDAO = new UserDAO();
        userDAO.cadastrarUsuario(user, confirmacaoSenha);

        resp.sendRedirect(req.getContextPath() + "/listarUsuarios.html");
    }
}
