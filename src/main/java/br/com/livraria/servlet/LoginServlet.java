package br.com.livraria.servlet;

import br.com.livraria.dao.UserDAO;
import br.com.livraria.model.Usuario;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.UUID;

@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        Usuario user = new Usuario(email, senha);

        //boolean isValidUser = new UserDAO();

        if (isValidUser) {

            req.getSession().setAttribute("loggedUser", email);

            resp.sendRedirect("/");

        } else {

            req.setAttribute("message", "Invalid credentials!");

            req.getRequestDispatcher("index.html").forward(req, resp);

        }
    }
}
