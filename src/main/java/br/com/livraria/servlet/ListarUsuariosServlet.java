package br.com.livraria.servlet;

import br.com.livraria.dao.UserDAO;
import br.com.livraria.model.Usuario;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ListarUsuariosServlet")
public class ListarUsuariosServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Usuario> usuarios = new UserDAO().listarUsuarios();


        if (usuarios.isEmpty()) {

            System.out.println("A lista de usuários está vazia.");
        } else {

            req.setAttribute("usuarios", usuarios);
            RequestDispatcher view = req.getRequestDispatcher("listarUsuarios.jsp");
            view.forward(req,resp);

        }
    }
}