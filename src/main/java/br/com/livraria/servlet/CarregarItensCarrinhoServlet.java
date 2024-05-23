package br.com.livraria.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

import com.google.gson.Gson;
import br.com.livraria.model.ItemCarrinho;

@WebServlet("/carregarItensCarrinho")
public class CarregarItensCarrinhoServlet extends HttpServlet {
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        // Verifica se há um carrinho na sessão
        List<ItemCarrinho> carrinho = (List<ItemCarrinho>) session.getAttribute("carrinho");

        // Verifica se há itens no carrinho
        if (carrinho != null && !carrinho.isEmpty()) {
            // Converte a lista de itens do carrinho para JSON
            Gson gson = new Gson();
            String json = gson.toJson(carrinho);

            // Configura a resposta
            response.setContentType("application/json");
            response.setCharacterEncoding("UTF-8");

            // Escreve os itens do carrinho como JSON na resposta
            PrintWriter out = response.getWriter();
            out.print(json);
            out.flush();
        } else {
            // Se não houver itens no carrinho, retorna uma resposta vazia
            response.setStatus(HttpServletResponse.SC_NO_CONTENT);
        }
    }
}

