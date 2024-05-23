package br.com.livraria.servlet;



import br.com.livraria.dao.ClienteDAO;
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

@WebServlet("/CadastroCliente")
public class CadastroClienteServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("cadastroCliente.html").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        // Obtenha os parâmetros do formulário
        String nome = req.getParameter("nome");
        String cpf = req.getParameter("cpf");
        String email = req.getParameter("email");
        String senha = req.getParameter("senha");
        String confirmacaoSenha = req.getParameter("confirmacaoSenha");
        String dataNascimentoStr = req.getParameter("dataNascimento");
        String genero = req.getParameter("genero");
        String cep = req.getParameter("cep");
        String logradouro = req.getParameter("logradouro");
        String numero = req.getParameter("numero");
        String complemento = req.getParameter("complemento");
        String bairro = req.getParameter("bairro");
        String cidade = req.getParameter("cidade");
        String uf = req.getParameter("uf");

        // Parse da data de nascimento
        Date dataNascimento = null;
        try {
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
            dataNascimento = dateFormat.parse(dataNascimentoStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }

        // Criação do objeto Cliente
        Cliente cliente = new Cliente(nome,cpf,email,senha,dataNascimento,genero);

        // Criação do objeto Endereco
        Endereco endereco = new Endereco(cep, logradouro,  numero, complemento, bairro, cidade, uf);

        // Chama o método para cadastrar o cliente e o endereço
        ClienteDAO clienteDAO = new ClienteDAO();
        clienteDAO.cadastrarCliente(cliente, confirmacaoSenha, endereco);


        resp.sendRedirect(req.getContextPath() + "/loginCliente.jsp");
    }

}