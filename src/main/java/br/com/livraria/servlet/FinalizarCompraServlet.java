package br.com.livraria.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

    @WebServlet("/FinalizarCompra")
    public class FinalizarCompraServlet extends HttpServlet {
        protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            HttpSession session = request.getSession();

            String freteSelecionado = request.getParameter("frete");
            System.out.println(freteSelecionado);
            String valorTotal = request.getParameter("valorTotal");
            System.out.println(valorTotal);

            session.setAttribute("freteSelecionado", freteSelecionado);
            session.setAttribute("valorTotal", valorTotal);

            response.sendRedirect("/ListarEnderecoServlet");
        }
    }

