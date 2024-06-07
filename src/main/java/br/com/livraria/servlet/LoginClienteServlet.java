package br.com.livraria.servlet;

import br.com.livraria.dao.ClienteDAO;
import br.com.livraria.dao.LivroDAO;
import br.com.livraria.model.Cliente;
import br.com.livraria.model.Livro;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.util.List;


@WebServlet("/LoginClienteServlet")
public class LoginClienteServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("loginCliente.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String redirectUrl = req.getParameter("redirectUrl");
        System.out.println("url : /" +redirectUrl+"/");

        Cliente cliente = new Cliente(email, senha);

        boolean isValidUser = new ClienteDAO().verifyCredentials(cliente);

        if (isValidUser) {
            List<Cliente> listaCliente = new ClienteDAO().listarClientePorEmail(email);

            req.getSession().setAttribute("cliente", listaCliente.get(0));
            req.setAttribute("message", "Login successful!");
            if(redirectUrl == ""){
                resp.sendRedirect("ListarEnderecoServlet");
            }else{
                resp.sendRedirect(redirectUrl != null ? URLDecoder.decode(redirectUrl, "UTF-8") : "/LivrosCImagensServlet");
            }

        } else {
            req.setAttribute("message", "Invalid credentials!");
            resp.sendRedirect("loginCliente.jsp?error=invalid&redirectUrl=" + URLEncoder.encode(redirectUrl, "UTF-8"));
        }
    }
}

