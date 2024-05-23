package br.com.livraria.servlet;

import br.com.livraria.dao.PedidoDAO;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;

/*@WebServlet("/AlterarPedido")
public class AlterarPedidoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pedidoId = Integer.parseInt(request.getParameter("pedidoId"));
        String novoStatus = request.getParameter("status");

        PedidoDAO pedidoDAO = new PedidoDAO();
        try {
            pedidoDAO.atualizarStatusPedido(pedidoId, novoStatus);
            response.sendRedirect("ListarPedidosEstoque");
        } catch (SQLException e) {
            e.printStackTrace();
            response.sendRedirect("listaPedidosEstoque.jsp?error=Erro ao atualizar o pedido");
        }
    }
}*/
