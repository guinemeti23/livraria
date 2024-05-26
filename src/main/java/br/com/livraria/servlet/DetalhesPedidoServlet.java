package br.com.livraria.servlet;

import br.com.livraria.dao.ClienteDAO;
import br.com.livraria.dao.PedidoDAO;
import br.com.livraria.model.Endereco;
import br.com.livraria.model.ItemCarrinho;
import br.com.livraria.model.Pedido;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@WebServlet("/DetalhesPedido")
public class DetalhesPedidoServlet extends HttpServlet {

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        int pedidoId = Integer.parseInt(request.getParameter("pedidoId"));
        PedidoDAO pedidoDAO = new PedidoDAO();

        try {
            // Buscando informações do pedido
            Pedido pedido = pedidoDAO.buscarPedidoPorId(pedidoId);
            if (pedido == null) {
                request.setAttribute("erro", "Pedido não encontrado.");
                request.getRequestDispatcher("/erro.jsp").forward(request, response);
                return;
            }

            // Buscando itens do pedido
            List<ItemCarrinho> itensPedido = pedidoDAO.buscarItensPedidoPorPedidoId(pedidoId);

            // Buscando endereço de entrega do pedido
            List<Endereco> enderecoEntrega = ClienteDAO.listarEnderecoEntrega(pedido.getEnderecoId());
            Endereco endereco = enderecoEntrega.isEmpty() ? null : enderecoEntrega.get(0);

            // Configurando atributos para serem acessados na JSP
            request.setAttribute("pedido", pedido);
            request.setAttribute("itensPedido", itensPedido);
            request.setAttribute("endereco", endereco);

            // Encaminhando para a página de detalhes do pedido
            request.getRequestDispatcher("/detalhesPedido.jsp").forward(request, response);

        } catch (SQLException e) {
            e.printStackTrace();
            response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR, "Erro ao acessar dados do pedido.");
        }
    }
}

