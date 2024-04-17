package br.com.livraria.servlet;

import br.com.livraria.dao.UserDAO;
import br.com.livraria.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/PreencheUsuarioServlet")
public class preencheUsuarioServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int userId = Integer.parseInt(request.getParameter("userId"));


        List<Usuario> usuarios = new UserDAO().listarUsuariosPorID(userId);


            if (!usuarios.isEmpty()) {
            Usuario usuario = usuarios.get(0);


            request.setAttribute("usuario", usuario);
            request.getRequestDispatcher("alternaUsuario.jsp").forward(request, response);
        } else {

            response.getWriter().println("Usuário não encontrado");
        }
    }
}
