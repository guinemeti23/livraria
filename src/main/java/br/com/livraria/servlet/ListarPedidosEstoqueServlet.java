package br.com.livraria.servlet;

import br.com.livraria.dao.PedidoDAO;
import br.com.livraria.model.Pedido;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/ListarPedidosEstoque")
public class ListarPedidosEstoqueServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        PedidoDAO pedidoDAO = new PedidoDAO();
        try {
            List<Pedido> pedidos = pedidoDAO.listarPedidos();
            request.setAttribute("pedidos", pedidos);
            RequestDispatcher dispatcher = request.getRequestDispatcher("listaPedidosEstoque.jsp");
            dispatcher.forward(request, response);
        } catch (SQLException e) {
            throw new ServletException("Erro ao listar pedidos", e);
        }
    }
}
