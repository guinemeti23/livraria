package br.com.livraria.servlet;

import br.com.livraria.dao.ClienteDAO;
import br.com.livraria.model.Cliente;
import br.com.livraria.model.Endereco;


import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

@WebServlet("/ListarEnderecoServlet")
public class ListarEnderecoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Cliente cliente = (Cliente) req.getSession().getAttribute("cliente");


        if (cliente == null) {
            // Se não está logado, redirecione diretamente para o carrinho
            resp.sendRedirect("selecionarEndereco.jsp");
        } else {
            // Se está logado, busque os endereços e encaminhe para o carrinho com a lista de endereços
            int clienteId = cliente.getId();
            System.out.println(clienteId);
            List<Endereco> enderecos = new ClienteDAO().listarEndereco(clienteId);
            req.setAttribute("enderecos", enderecos);
            RequestDispatcher view = req.getRequestDispatcher("selecionarEndereco.jsp");
            view.forward(req, resp);
        }
    }


}
