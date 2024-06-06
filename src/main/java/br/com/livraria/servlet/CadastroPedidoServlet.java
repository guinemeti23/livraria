package br.com.livraria.servlet;

import br.com.livraria.dao.PedidoDAO;
import br.com.livraria.model.Cliente;
import br.com.livraria.model.Endereco;
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

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Obter o objeto Cliente da sessão
        Cliente cliente = (Cliente) session.getAttribute("cliente");


        // Extração das informações necessárias do objeto Cliente
        int clienteId = cliente.getId();
        int enderecoId = (int) session.getAttribute("enderecoId");
        String formaPagamento = (String) session.getAttribute("formaPagamento");
        String freteSelecionado = (String) session.getAttribute("freteSelecionado");
        double valorTotal = Double.parseDouble((String) session.getAttribute("valorTotal"));


        Pedido pedido = new Pedido(clienteId, enderecoId, formaPagamento, freteSelecionado, valorTotal);
        List<ItemCarrinho> itensCarrinho = (List<ItemCarrinho>) session.getAttribute("carrinho");

        if (itensCarrinho != null) {
            for (ItemCarrinho item : itensCarrinho) {
                pedido.adicionarItem(item);
            }
        }


        try {
            PedidoDAO pedidoDAO = new PedidoDAO();
            pedidoDAO.adicionarPedido(pedido);
            response.sendRedirect("/LivrosCImagensServlet");
        } catch (Exception e) {
            e.printStackTrace(); // Log de erro
            request.setAttribute("error", "Erro ao processar o pedido.");
            request.getRequestDispatcher("errorPage.jsp").forward(request, response);
        }
    }

}



