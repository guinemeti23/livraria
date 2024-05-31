package br.com.livraria.servlet;

import br.com.livraria.dao.LivroDAO;
import br.com.livraria.model.Livro;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/*@WebServlet("/ListarProdutoServlet")
public class ListarProdutoServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        List<Livro> livros = new LivroDAO().listarProdutos();

        if (livros.isEmpty()) {

            System.out.println("A lista de livros está vazia.");
        } else {

            req.setAttribute("livros", livros);
            RequestDispatcher view = req.getRequestDispatcher("ListaProduto.jsp");
            view.forward(req,resp);

        }
    }
}*/