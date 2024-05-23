package br.com.livraria.servlet;

import br.com.livraria.dao.PedidoDAO;
import br.com.livraria.model.Cliente;
import br.com.livraria.model.Pedido;
import br.com.livraria.model.ItemCarrinho;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/CadastroPedido")
public class CadastroPedidoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Recuperando o cliente da sessão
        Cliente cliente = (Cliente) request.getSession().getAttribute("cliente");
        int clienteId = cliente != null ? cliente.getId() : -1;

        int enderecoId = Integer.parseInt(request.getParameter("enderecoId"));

        String formaPagamento = request.getParameter("formaPagamento");
        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) request.getSession().getAttribute("carrinho");

        Pedido pedido = new Pedido(clienteId, enderecoId, formaPagamento, "Calcular o Frete Aqui", 0.0);
        pedido.setItens(carrinho);
        pedido.calcularValorTotal();

        try {
            PedidoDAO dao = new PedidoDAO();
            dao.adicionarPedido(pedido);
            response.sendRedirect("pedidoConfirmado.jsp");
        } catch (SQLException e) {
            throw new ServletException("Erro ao processar pedido", e);
        }
    }
}

