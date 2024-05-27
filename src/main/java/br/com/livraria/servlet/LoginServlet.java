package br.com.livraria.servlet;

import br.com.livraria.dao.UserDAO;
import br.com.livraria.model.Usuario;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@WebServlet("/LoginServlet")
public class LoginServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("login.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        Usuario user = new Usuario(email, senha);

        boolean isValidUser = new UserDAO().verifyCredentials(user);
        boolean isADM = new UserDAO().isAdmin(user);

        if (isValidUser) {
            System.out.println("Usuário " + email + " é admin: " + isADM);

            HttpSession session = req.getSession();
            session.setAttribute("loggedUser", email);
            if (isADM) {
                session.setAttribute("isAdmin", true);
            } else {
                session.removeAttribute("isAdmin");
            }
            resp.sendRedirect("inicio.jsp");

        }else{
            resp.sendRedirect("index.html?error=invalid");
        }
    }
}