package br.com.livraria.servlet;

import br.com.livraria.dao.ClienteDAO;
import br.com.livraria.model.Cliente;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;


@WebServlet("/LoginClienteServlet")
public class LoginClienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("loginCliente.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");

        Cliente cliente = new Cliente(email, senha);

        boolean isValidUser = new ClienteDAO().verifyCredentials(cliente);

        if (isValidUser) {
            req.getSession().setAttribute("Cliente", email);
            req.setAttribute("message", "Login successful!");
            resp.sendRedirect("telaInicial.jsp");
        } else {
            req.setAttribute("message", "Invalid credentials!");
            resp.sendRedirect("loginCliente.html?error=invalid");
        }
    }
}
