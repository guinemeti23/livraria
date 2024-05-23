package br.com.livraria.servlet;

import br.com.livraria.dao.ClienteDAO;
import br.com.livraria.dao.ViaCepService;
import br.com.livraria.model.Cliente;
import br.com.livraria.model.Endereco;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet("/CadastroEndereco")
public class CadastroEnderecoServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("cadastroCliente.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        Cliente clienteLogado = (Cliente) req.getSession().getAttribute("cliente");
        int clienteId = clienteLogado.getId();

        String cep = req.getParameter("cep");
        String logradouro = req.getParameter("logradouro");
        String numero = req.getParameter("numero");
        String complemento = req.getParameter("complemento");
        String bairro = req.getParameter("bairro");
        String cidade = req.getParameter("cidade");
        String uf = req.getParameter("uf");


        Endereco endereco = new Endereco(clienteId, cep, logradouro, numero, complemento, bairro, cidade, uf);

        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.cadastrarEndereco(endereco);

        resp.sendRedirect("/LivrosCImagensServlet");
    }

}

