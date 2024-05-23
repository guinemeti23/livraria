package br.com.livraria.servlet;
import br.com.livraria.dao.PedidoDAO;
import br.com.livraria.model.Cliente;
import br.com.livraria.model.Pedido;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet("/ListarPedidos")
public class ListarPedidosServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession session = req.getSession(false);


        Cliente cliente = (Cliente) session.getAttribute("cliente");
        int clienteId = cliente.getId();

        PedidoDAO pedidoDAO = new PedidoDAO();
        try {
            List<Pedido> pedidos = pedidoDAO.listarPedidosPorClienteId(clienteId);
            req.setAttribute("pedidos", pedidos);
            RequestDispatcher dispatcher = req.getRequestDispatcher("Pedidos.jsp");
            dispatcher.forward(req, resp);
        } catch (Exception e) {
            e.printStackTrace();
            throw new ServletException("Erro ao listar pedidos", e);
        }
    }
}