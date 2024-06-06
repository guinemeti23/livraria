package br.com.livraria.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/ProcessarPagamento")
public class ProcessarPagamentoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        String formaPagamento = request.getParameter("formaPagamento");
        System.out.println("Forma de Pagamento: " + formaPagamento);

        session.setAttribute("formaPagamento", formaPagamento);

        response.sendRedirect("resumoPedido.jsp");
    }
}
