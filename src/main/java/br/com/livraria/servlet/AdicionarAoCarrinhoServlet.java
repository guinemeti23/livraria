package br.com.livraria.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import br.com.livraria.model.ItemCarrinho;

@WebServlet("/adicionarAoCarrinho")
public class AdicionarAoCarrinhoServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Verifica se há um carrinho na sessão
        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) session.getAttribute("carrinho");
        if (carrinho == null) {
            carrinho = new ArrayList<>();
            session.setAttribute("carrinho", carrinho);
        }

        // Detalhes do livro a ser adicionado ao carrinho
        int livroId = Integer.parseInt(request.getParameter("livroId"));
        String livroNome = request.getParameter("livroNome");
        double livroPreco = Double.parseDouble(request.getParameter("livroPreco"));

        // Verifica se o livro já está no carrinho
        boolean encontrado = false;
        for (ItemCarrinho item : carrinho) {
            if (item.getLivroId() == livroId) {
                // Se o livro já estiver no carrinho, apenas atualize a quantidade
                item.setQuantidade(item.getQuantidade() + 1);
                encontrado = true;
                break;
            }
        }

        // Se o livro não estiver no carrinho, adicione-o
        if (!encontrado) {
            ItemCarrinho novoItem = new ItemCarrinho(livroId, livroNome, livroPreco, 1);
            carrinho.add(novoItem);
        }

        // Redireciona de volta para a página de onde veio
        response.sendRedirect(request.getHeader("referer"));
    }
}
